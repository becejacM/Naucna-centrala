package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.ReviewerIndexUnit;

public interface IUReviewerRepository  extends ElasticsearchRepository<ReviewerIndexUnit, String>{

    //@Query("{\"bool\": {\"must\": [{\"match\": {\"naucnaOblast.ime\": \"?0\"}}]}}")
    //Collection<ReviewerIndexUnit> findByNaucnaOblastQuery(String naucnaOblast);
}
