package ftn.uns.ac.rs.naucnaCentrala.camunda.tasks;

import java.nio.file.Paths;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.repository.IUPaperRepository;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.service.ESPaperService;
import ftn.uns.ac.rs.naucnaCentrala.model.Paper;
import ftn.uns.ac.rs.naucnaCentrala.model.ScientificFieldName;
import ftn.uns.ac.rs.naucnaCentrala.repository.PaperRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.ScientificFieldRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PublishSavePaper implements JavaDelegate{

	@Autowired
	ESPaperService esPaperService;
	
	@Autowired
	PaperRepository peperRepository;
	
	@Autowired
	ScientificFieldRepository sfRepository;
	
	Path dirLocation;
	Path dirTemporaryLocation;
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
        this.dirLocation = Paths.get("src/main/resources/files/pdf_files");
        this.dirTemporaryLocation = Paths.get("src/main/resources/files/temporary_pdf_files");
        
		String filename = (String) execution.getVariable("filename");
		String naslov = (String) execution.getVariable("naslov");
		String apstrakt = (String) execution.getVariable("apstrakt");
		String kljucneReci = (String) execution.getVariable("kljucneReci");
		String nsucnaOblast = (String) execution.getVariable("nsucnaOblast");
		String autori = (String) execution.getVariable("autori");

        File file = this.dirLocation.resolve(filename).toFile();
        System.out.println("************************ "+autori);
        Paper paper = new Paper();
        paper.setFilename(filename);
        paper.setNaslovRada(naslov);
        paper.setApstract(apstrakt);
        paper.setKeywords(kljucneReci);
        paper.setScientificField(sfRepository.findByScientificFieldName(ScientificFieldName.valueOf(nsucnaOblast)));
        paper.setNaslovRada(naslov);

        peperRepository.save(paper);
        //esPaperService.storeFile(multipartFile);
        
        
	}

}
