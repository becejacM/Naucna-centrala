package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.indexing;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.handler.PdfDocumentHandler;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.NaucnaOblast;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.PaperIndexUnit;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.ReviewerIndexUnit;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.StorageProperties;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.repository.IUPaperRepository;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.repository.IUReviewerRepository;
import ftn.uns.ac.rs.naucnaCentrala.model.Author;
import ftn.uns.ac.rs.naucnaCentrala.model.Coauthor;
import ftn.uns.ac.rs.naucnaCentrala.model.Paper;
import ftn.uns.ac.rs.naucnaCentrala.model.Reviewer;
import ftn.uns.ac.rs.naucnaCentrala.model.ScientificField;
import ftn.uns.ac.rs.naucnaCentrala.model.ScientificFieldName;
import ftn.uns.ac.rs.naucnaCentrala.model.UserRole;
import ftn.uns.ac.rs.naucnaCentrala.repository.AppUserRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.ArticleRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.CoauthorRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.MagazineRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.ScientificFieldRepository;

@Service
public class Indexer {
	private IUPaperRepository iupaperRepository;
	private IUReviewerRepository iureviewerRepository;
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
    public Indexer(StorageProperties properties,
                        IUPaperRepository iupaperRepository,
                        ArticleRepository paperRepository,
                        PdfDocumentHandler pdfDocumentHandler,
                        ElasticsearchTemplate elasticsearchTemplate,
                        MagazineRepository magazineRepository,
                        ScientificFieldRepository sfRepository,
                        AppUserRepository userRepository,
                        CoauthorRepository coautorRepository, IUReviewerRepository iureviewerRepository
                        ) {
        this.iupaperRepository = iupaperRepository;
        this.paperRepository = paperRepository;
        this.pdfDocumentHandler = pdfDocumentHandler;
        this.dirLocation = Paths.get(properties.getLocation());
        this.dirTemporaryLocation = Paths.get(properties.getTemporarylocation());
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.magazineRepository = magazineRepository;
        this.sfRepository = sfRepository;
        this.userRepository = userRepository;
        this.coautorRepository = coautorRepository;
        this.iureviewerRepository = iureviewerRepository;
        }

    public PaperIndexUnit add(PaperIndexUnit iupaper) {
        return iupaperRepository.index(iupaper);
    }

    public PaperIndexUnit update(PaperIndexUnit iupaper) {
        return iupaperRepository.save(iupaper);
    }

    public void delete(PaperIndexUnit iupaper) {
    	iupaperRepository.delete(iupaper);
    }
    
    public PaperIndexUnit findByFilename(String filename) {
    	//return iupaperRepository.findByFilename(filename);
    	return null;
    }
    
    public void indexPaper(String filename, int i) {
		System.out.println(dirLocation.toString()+"\\"+filename);
        Paper paper = new Paper();
        paper.setFilename(filename);
        paper.setApstract("neki abstrakt");
        paper.setKeywords("kljucna rec");
        if(i%2==0) {
            paper.setDostupnost("WITH_SUBSCRIPTION");	
            paper.setScientificField(sfRepository.findByScientificFieldName(ScientificFieldName.BIOLOGY));
            paper.setMagazine(magazineRepository.findByName("casopis 1 biologija"));
            Collection<Coauthor> autori = new ArrayList<Coauthor>();
            Author a = (Author) userRepository.findByUsername("autor1");
            Coauthor c = new Coauthor();
            c.setFirstname(a.getFirstname());
            c.setLastname(a.getLastname());
            Coauthor cc = coautorRepository.findByFirstnameAndLastname("autor1", "autor1");
            if(cc==null) {
                coautorRepository.save(c);
                autori.add(c);
            }
            else {
                autori.add(cc);
            }
            paper.setCoauthors(autori);
        }
        else {
            paper.setDostupnost("OPEN_ACCESS");
            paper.setScientificField(sfRepository.findByScientificFieldName(ScientificFieldName.LOGIC));
            paper.setMagazine(magazineRepository.findByName("casopis 2 logika"));
            Collection<Coauthor> autori = new ArrayList<Coauthor>();
            Author a = (Author) userRepository.findByUsername("autor2");
            Coauthor c = new Coauthor();
            c.setFirstname(a.getFirstname());
            c.setLastname(a.getLastname());
            Coauthor cc = coautorRepository.findByFirstnameAndLastname("autor2", "autor2");
            if(cc==null) {
                coautorRepository.save(c);
                autori.add(c);
            }
            else {
                autori.add(cc);
            }
            paper.setCoauthors(autori);
        }
        paper.setNaslovRada(filename.substring(0, filename.length()-4));
        //paper.setScientificField(sfRepository.findByScientificFieldName(ScientificFieldName.BIOLOGY));
        
        paper.setCena(10L);
        PaperIndexUnit paperIU = pdfDocumentHandler
                .getIUPaper(paper, dirLocation.resolve(paper.getFilename()));
        Paper saved = paperRepository.save(paper);
        System.out.println("evo meeeeeeeeeeeeeee" +paperIU.getOblast());
        paperIU.setOblast(saved.getScientificField().getScientificFieldName().name());
        PaperIndexUnit s = iupaperRepository.save(paperIU);
        System.out.println("sacuvaooooooooooo " +s.getOblast());

	}
    
    public void indexReviewer() {
		ScientificField sf1 = sfRepository.findByScientificFieldName(ScientificFieldName.BIOLOGY);
		ScientificField sf2 = sfRepository.findByScientificFieldName(ScientificFieldName.LOGIC);
		ArrayList<ScientificField> oblasti1 = new ArrayList<ScientificField>();
		oblasti1.add(sf1);
		oblasti1.add(sf2);
		ArrayList<ScientificField> oblasti2 = new ArrayList<ScientificField>();
		oblasti2.add(sf1);
		ArrayList<ScientificField> oblasti3 = new ArrayList<ScientificField>();
		oblasti3.add(sf2);
        Reviewer r = createReviewer("myreviewer1", "pass", "myreviewer1", "myreviewer1", "myreviewer1@mailinator.com", "NS", "Serbia",  oblasti1);
        Reviewer r2 = createReviewer("myreviewer2", "pass", "myreviewer2", "myreviewer2", "myreviewer2@mailinator.com", "NS", "Serbia",  oblasti2);
        Reviewer r3 = createReviewer("myreviewer3", "pass", "myreviewer3", "myreviewer3", "myreviewer3@mailinator.com", "NS", "Serbia",  oblasti3);

        Reviewer rsaved = userRepository.save(r);
        Reviewer rsaved2 = userRepository.save(r2);
        Reviewer rsaved3= userRepository.save(r3);

        ReviewerIndexUnit riu = createIUReviewer(rsaved);
        ReviewerIndexUnit riu2 = createIUReviewer(rsaved2);
        ReviewerIndexUnit riu3 = createIUReviewer(rsaved3);

        ReviewerIndexUnit s = iureviewerRepository.save(riu);
        ReviewerIndexUnit s2 = iureviewerRepository.save(riu2);
        ReviewerIndexUnit s3 = iureviewerRepository.save(riu3);
        System.out.println("sacuvaooooooooooo " +s.getUsername());
        System.out.println("sacuvaooooooooooo " +s2.getUsername());
        System.out.println("sacuvaooooooooooo " +s3.getUsername());

	}
    
    private Reviewer createReviewer(String username, String pass, String firstname, String lastname, String email, String city, String state, ArrayList<ScientificField> naucnaOblast) {
    	Reviewer reviewer = new Reviewer();
		reviewer.setUsername(username);
		reviewer.setPassword(pass);
		reviewer.setFirstname(firstname);
		reviewer.setLastname(lastname);
		reviewer.setEmail(email);
		reviewer.setCity(city);
		reviewer.setState(state);
		reviewer.setReviewerFields(naucnaOblast);
		reviewer.setRole(UserRole.REVIEWER);
		return reviewer;
    	
    }
    
    private ReviewerIndexUnit createIUReviewer(Reviewer r) {
    	ReviewerIndexUnit riu = new ReviewerIndexUnit();
    	riu.setId(r.getId().toString());
    	riu.setUsername(r.getUsername());
    	riu.setPassword(r.getPassword());
    	riu.setFirstname(r.getFirstname());
    	riu.setLastname(r.getLastname());
    	riu.setEmail(r.getEmail());
    	riu.setCity(r.getCity());
    	riu.setState(r.getState());
    	ArrayList<NaucnaOblast> no = new ArrayList<NaucnaOblast>();
    	for (ScientificField s : r.getReviewerFields()) {
    		NaucnaOblast n = new NaucnaOblast();
        	n.setIme(s.getScientificFieldName().name());
        	no.add(n);
		}
    	
    	riu.setNaucnaOblast(no);
    	return riu;
    }
}
