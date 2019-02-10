package ftn.uns.ac.rs.naucnaCentrala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.ac.rs.naucnaCentrala.model.Coauthor;

@Repository
public interface CoauthorRepository extends JpaRepository<Coauthor, Long>{
	Coauthor findByFirstnameAndLastname(String firstname, String lastname);
}
