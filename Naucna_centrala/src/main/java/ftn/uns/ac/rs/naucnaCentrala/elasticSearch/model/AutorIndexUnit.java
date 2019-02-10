package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model;

import org.springframework.data.elasticsearch.annotations.Document;

//@Document(indexName = "naucnacentrala8", type = "autorindexunit", shards = 1, replicas = 0)
public class AutorIndexUnit {

	private String imeAutora;
	private String prezimeAutora;
	
	

	public String getImeAutora() {
		return imeAutora;
	}

	public void setImeAutora(String imeAutora) {
		this.imeAutora = imeAutora;
	}

	public String getPrezimeAutora() {
		return prezimeAutora;
	}

	public void setPrezimeAutora(String prezimeAutora) {
		this.prezimeAutora = prezimeAutora;
	}
	
	
}
