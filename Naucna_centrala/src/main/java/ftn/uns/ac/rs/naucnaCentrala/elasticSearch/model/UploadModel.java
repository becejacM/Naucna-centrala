package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class UploadModel {

    private String naslov;
    
    private String keywords;

    private MultipartFile[] files;

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

    
    
}
