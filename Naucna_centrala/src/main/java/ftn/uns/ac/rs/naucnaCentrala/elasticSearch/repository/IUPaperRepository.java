package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.repository;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.PaperIndexUnit;

import java.util.Collection;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUPaperRepository extends ElasticsearchRepository<PaperIndexUnit, String> {
	PaperIndexUnit findByFilename(String filename);
}
