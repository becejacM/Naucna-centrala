package ftn.uns.ac.rs.naucnaCentrala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.ac.rs.naucnaCentrala.model.ScientificField;
import ftn.uns.ac.rs.naucnaCentrala.model.ScientificFieldName;

@Repository
public interface ScientificFieldRepository extends JpaRepository<ScientificField, Long> {

	ScientificField findByScientificFieldName(ScientificFieldName name);
}
