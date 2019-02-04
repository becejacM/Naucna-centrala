package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.indexing;

import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.PaperIndexUnit;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.repository.IUPaperRepository;

@Service
public class Indexer {

    private IUPaperRepository iupaperRepository;

    public Indexer(IUPaperRepository iupaperRepository) {
        this.iupaperRepository = iupaperRepository;
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
    	return iupaperRepository.findByFilename(filename);
    }
}
