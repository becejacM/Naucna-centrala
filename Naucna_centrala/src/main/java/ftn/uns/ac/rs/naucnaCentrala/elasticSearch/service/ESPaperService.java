package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
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
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.StorageProperties;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.repository.IUPaperRepository;
import ftn.uns.ac.rs.naucnaCentrala.model.Author;
import ftn.uns.ac.rs.naucnaCentrala.model.Coauthor;
import ftn.uns.ac.rs.naucnaCentrala.model.Paper;
import ftn.uns.ac.rs.naucnaCentrala.model.ScientificField;
import ftn.uns.ac.rs.naucnaCentrala.model.ScientificFieldName;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.SearchDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.SearchHitDTO;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.handler.PdfDocumentHandler;
import ftn.uns.ac.rs.naucnaCentrala.repository.AppUserRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.ArticleRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.CoauthorRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.MagazineRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.ScientificFieldRepository;

@Service
public class ESPaperService {

	private IUPaperRepository irEbookRepository;

    private ArticleRepository paperRepository;

    private PdfDocumentHandler pdfDocumentHandler;

    private Path dirLocation;
    private Path dirTemporaryLocation;
    private ElasticsearchTemplate elasticsearchTemplate;

    private MagazineRepository magazineRepository;
    
    private ScientificFieldRepository sfRepository;

    private AppUserRepository userRepository;
    private CoauthorRepository coautorRepository;
    @Autowired
    public ESPaperService(StorageProperties properties,
                        IUPaperRepository irEbookRepository,
                        ArticleRepository paperRepository,
                        PdfDocumentHandler pdfDocumentHandler,
                        ElasticsearchTemplate elasticsearchTemplate,
                        MagazineRepository magazineRepository,
                        ScientificFieldRepository sfRepository,
                        AppUserRepository userRepository,
                        CoauthorRepository coautorRepository
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
	}
	
	public void indexPaper(String filename, int i) {
		System.out.println(dirLocation.toString()+"\\"+filename);
        Paper paper = new Paper();
        paper.setFilename(filename);
        paper.setApstract("neki abstrakt");
        paper.setKeywords("kljucna rec");
        if(i%2==0) {
            paper.setDostupnost("WITH_SUBSCRIPTION");	
        }
        else {
            paper.setDostupnost("OPEN_ACCESS");
        }
        paper.setNaslovRada(filename.substring(0, filename.length()-4));
        paper.setMagazine(magazineRepository.findByName("casopis 1 biologija"));
        paper.setScientificField(sfRepository.findByScientificFieldName(ScientificFieldName.BIOLOGY));
        Collection<Coauthor> autori = new ArrayList<Coauthor>();
        Author a = (Author) userRepository.findByUsername("autor1");
        Coauthor c = new Coauthor();
        c.setFirstname(a.getFirstname());
        c.setLastname(a.getLastname());
        coautorRepository.save(c);
        autori.add(c);
        paper.setCoauthors(autori);
        paper.setCena(10L);
        PaperIndexUnit paperIU = pdfDocumentHandler
                .getIUPaper(paper, dirLocation.resolve(paper.getFilename()));
        Paper saved = paperRepository.save(paper);
        System.out.println("evo meeeeeeeeeeeeeee" +paperIU.getNaslovRada());
        PaperIndexUnit s = irEbookRepository.save(paperIU);
        System.out.println("sacuvaooooooooooo " +paperIU.getNaslovRada());

	}
	
	
	
	public Collection<PaperIndexUnit> findAll() {
		Collection<PaperIndexUnit> papers = new ArrayList<PaperIndexUnit>();
		Iterable<PaperIndexUnit> booksEntities = irEbookRepository.findAll();
		for (PaperIndexUnit paperIndexUnit : booksEntities) {
			papers.add(paperIndexUnit);
		}
        return papers;
    }
	
	public Collection<Paper> findAllSEP(){
		Collection<Paper> papers = new ArrayList<Paper>();
		papers = paperRepository.findAll();
		return papers;
	}
	
	
	public void save(Paper paper) {
        PaperIndexUnit iupaper = pdfDocumentHandler
                .getIUPaper(paper, dirLocation.resolve(paper.getFilename()));
        Paper saved = paperRepository.save(paper);

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
    
    public List<SearchHitDTO> search(org.elasticsearch.index.query.QueryBuilder query) {
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
    }

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

    public void update(Paper paper) {
        this.save(paper);
    }

    public void deleteFileFromStorage(String filename) throws IOException {
        Files.deleteIfExists(dirLocation.resolve(filename));
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
    
    public List<SearchHitDTO> search2(SearchDTO s) {
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
    }
}
