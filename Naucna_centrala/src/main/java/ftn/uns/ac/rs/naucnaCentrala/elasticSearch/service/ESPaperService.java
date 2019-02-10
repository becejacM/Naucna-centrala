package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.PaperIndexUnit;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.ReviewerIndexUnit;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.SearchType;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.StorageProperties;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.repository.IUPaperRepository;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.repository.IUReviewerRepository;
import ftn.uns.ac.rs.naucnaCentrala.model.Author;
import ftn.uns.ac.rs.naucnaCentrala.model.Coauthor;
import ftn.uns.ac.rs.naucnaCentrala.model.Magazine;
import ftn.uns.ac.rs.naucnaCentrala.model.Paper;
import ftn.uns.ac.rs.naucnaCentrala.model.ScientificField;
import ftn.uns.ac.rs.naucnaCentrala.model.ScientificFieldName;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.AutorDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.PaperDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.SearchDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.SearchHitDTO;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.handler.PdfDocumentHandler;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.indexing.Indexer;
import ftn.uns.ac.rs.naucnaCentrala.repository.AppUserRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.ArticleRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.CoauthorRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.MagazineRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.ScientificFieldRepository;

@Service
public class ESPaperService {

	private Path dirLocation;
    private Path dirTemporaryLocation;
    
	private IUPaperRepository irEbookRepository;

	private IUReviewerRepository iuReviewerRepository;

    private ArticleRepository paperRepository;

    private PdfDocumentHandler pdfDocumentHandler;

    private ElasticsearchTemplate elasticsearchTemplate;

    private MagazineRepository magazineRepository;
    
    private ScientificFieldRepository sfRepository;

    private AppUserRepository userRepository;
    
    private CoauthorRepository coautorRepository;
    
	private Indexer indexer;
	
    @Autowired
    public ESPaperService(StorageProperties properties,
                        IUPaperRepository irEbookRepository,
                        ArticleRepository paperRepository,
                        PdfDocumentHandler pdfDocumentHandler,
                        ElasticsearchTemplate elasticsearchTemplate,
                        MagazineRepository magazineRepository,
                        ScientificFieldRepository sfRepository,
                        AppUserRepository userRepository,
                        CoauthorRepository coautorRepository,
                        Indexer indexer, 
                        IUReviewerRepository iuReviewerRepository
                        ) {
        this.irEbookRepository = irEbookRepository;
        this.paperRepository = paperRepository;
        this.pdfDocumentHandler = pdfDocumentHandler;
        this.dirLocation = Paths.get(properties.getLocation());
        this.dirTemporaryLocation = Paths.get(properties.getTemporarylocation());
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.magazineRepository = magazineRepository;
        this.sfRepository = sfRepository;
        this.userRepository = userRepository;
        this.coautorRepository = coautorRepository;
        this.indexer = indexer;
        this.iuReviewerRepository = iuReviewerRepository;
        }
    
	public void init() {
        try {
            Files.createDirectories(dirLocation);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void initializeIndexFiles() throws IOException {
		File folder = new File(dirLocation.toString());
		listFilesForFolder(folder);
	}
	
	public void listFilesForFolder(final File folder) {
        int i = 2;
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            indexPaper(fileEntry.getName(), i);
	            i++;
	        }
	    }
	    this.indexer.indexReviewer();
	}
	
	public void indexPaper(String filename, int i) {
		this.indexer.indexPaper(filename, i);
	}
	
	
	
	public Collection<PaperIndexUnit> findAll() {
		Collection<PaperIndexUnit> papers = new ArrayList<PaperIndexUnit>();
		Iterable<PaperIndexUnit> booksEntities = irEbookRepository.findAll();
		for (PaperIndexUnit paperIndexUnit : booksEntities) {
			papers.add(paperIndexUnit);
		}
        return papers;
    }
	
	public Collection<ReviewerIndexUnit> findAllReviewersByScientificField(String naucnaOblast) throws IllegalArgumentException, ParseException {
		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.regular, "naucnaOblast.ime", naucnaOblast);
		Collection<ReviewerIndexUnit> iureviewers = new ArrayList<ReviewerIndexUnit>();
		SearchQuery sq = new NativeSearchQueryBuilder()
				.withQuery(query)
				.build();
		Iterable<ReviewerIndexUnit> reviewers = iuReviewerRepository.search(query);
		for (ReviewerIndexUnit index : reviewers) {
			iureviewers.add(index);
		}
        return iureviewers;
    }
	public Collection<ReviewerIndexUnit> findAllReviewers() {
		Collection<ReviewerIndexUnit> iureviewers = new ArrayList<ReviewerIndexUnit>();
		Iterable<ReviewerIndexUnit> reviewers = iuReviewerRepository.findAll();
		for (ReviewerIndexUnit index : reviewers) {
			iureviewers.add(index);
		}
        return iureviewers;
    }
	/*public Collection<Paper> findAllSEP(){
		Collection<Paper> papers = new ArrayList<Paper>();
		papers = paperRepository.findAll();
		return papers;
	}*/
	
	
	public void save(PaperDTO paperDTO) {
        Paper paper = new Paper(paperDTO);
        ArrayList<Coauthor> autori = new ArrayList<Coauthor>();
		for (AutorDTO autorDTO : paperDTO.getAutori()) {
			Coauthor c = coautorRepository.findByFirstnameAndLastname(autorDTO.getImeAutora(), autorDTO.getPrezimeAutora());
			if(c==null) {
				Coauthor aaa = new Coauthor(autorDTO.getImeAutora(), autorDTO.getPrezimeAutora());
				coautorRepository.save(aaa);
				autori.add(aaa);
			}
			else {
				autori.add(c);

			}
		}
		paper.setCoauthors(autori);
		ScientificField sf = sfRepository.findByScientificFieldName(ScientificFieldName.valueOf(paperDTO.getNaucnaOblast()));
		paper.setScientificField(sf);
		Magazine m = magazineRepository.findByName(paperDTO.getNazivCasopisa());
		paper.setMagazine(m);
		paper.setDostupnost(m.getPaymentMethod().name());
		PaperIndexUnit iupaper = pdfDocumentHandler
                .getIUPaper(paper, dirLocation.resolve(paper.getFilename()));
        Paper saved = paperRepository.save(paper);
        iupaper.setOblast(saved.getScientificField().getScientificFieldName().name());
        iupaper.setFilename(String.valueOf(saved.getFilename()));
        irEbookRepository.save(iupaper);
    }

    public File storeFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            Files.copy(inputStream, this.dirLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.dirLocation.resolve(filename).toFile();
    }
    

    public File storeFileTemporary(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        System.out.println("jebi se "+file.getOriginalFilename());
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            Files.copy(inputStream, this.dirTemporaryLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.dirTemporaryLocation.resolve(filename).toFile();
    }
    
    /*public List<SearchHitDTO> search(org.elasticsearch.index.query.QueryBuilder query) {
        List<SearchHitDTO> result = new ArrayList<>();
        SearchResponse response = elasticsearchTemplate.getClient().prepareSearch("naucnacentrala6")
                .setTypes("paperindexunit")
                .setQuery(query)
                .highlighter(new HighlightBuilder()
                        .field("text")
                        .numOfFragments(1)
                        .fragmentSize(200)
                        .preTags("<b><em>")
                        .postTags("</em></b>")
                )
                .get();

        Iterator<SearchHit> iterator = response.getHits().iterator();
        Gson gson = new GsonBuilder().create();
        while (iterator.hasNext()) {
            SearchHit searchHit = iterator.next();
            String searchSource = searchHit.getSourceAsString();
            if (searchSource != null) {
                SearchHitDTO searchHitDTO = gson.fromJson(searchSource, SearchHitDTO.class);
                searchHit.getHighlightFields().values().forEach(field -> {
                    searchHitDTO.setText(extractHighlightedText(field));
                });
                result.add(searchHitDTO);
            }
        }

        return result;
    }

    private QueryStringQueryBuilder formQuery(SearchDTO searchDTO) {
    	System.out.println(QueryBuilders.queryStringQuery(searchDTO.getValue().trim()));
        return QueryBuilders.queryStringQuery(searchDTO.getValue().trim());
    }

    private String extractHighlightedText(HighlightField highlightField) {
        return Arrays.stream(highlightField.getFragments())
                .map(t -> t.string() + "...")
                .collect(Collectors.joining());
    }*/

    public Optional<Resource> loadBookAsResource(long id) {
        try {
            Paper paper = paperRepository.findById(id).get();
            Path filePath = this.dirLocation.resolve(paper.getFilename()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return Optional.of(resource);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void update(PaperDTO paperdto) {

        this.save(paperdto);
    }

    public void deleteFileFromStorage(String filename) throws IOException {
        Files.deleteIfExists(dirLocation.resolve(filename));
        Files.deleteIfExists(dirTemporaryLocation.resolve(filename));
    }
    
    public Paper getMetadata(File file) {
        Paper paper = null;
        try {
            paper = new Paper();
            PDDocument document = PDDocument.load(file);
            PDDocumentInformation info = document.getDocumentInformation();
            //paper.setAuthor(info.getAuthor());
            paper.setKeywords(info.getKeywords());
            paper.setNaslovRada(info.getTitle());
            paper.setFilename(file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return paper;
    }
    
    /*public List<SearchHitDTO> search2(SearchDTO s) {
        List<SearchHitDTO> result = new ArrayList<>();
        SearchResponse response = elasticsearchTemplate.getClient().prepareSearch("naucnacentrala7")
                .setTypes("paperindexunit")
                .setQuery(formQuery(s))
                .highlighter(new HighlightBuilder()
                        .field("text")
                        .numOfFragments(1)
                        .fragmentSize(200)
                        .preTags("<b><em>")
                        .postTags("</em></b>")
                )
                .get();

        Iterator<SearchHit> iterator = response.getHits().iterator();
        Gson gson = new GsonBuilder().create();
        while (iterator.hasNext()) {
            SearchHit searchHit = iterator.next();
            String searchSource = searchHit.getSourceAsString();
            if (searchSource != null) {
                SearchHitDTO searchHitDTO = gson.fromJson(searchSource, SearchHitDTO.class);
                searchHit.getHighlightFields().values().forEach(field -> {
                    searchHitDTO.setText(extractHighlightedText(field));
                });
                result.add(searchHitDTO);
            }
        }

        return result;
    }*/
}
