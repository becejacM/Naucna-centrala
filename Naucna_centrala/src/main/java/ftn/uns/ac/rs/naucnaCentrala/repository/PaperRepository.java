package ftn.uns.ac.rs.naucnaCentrala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.ac.rs.naucnaCentrala.model.Paper;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long>{

}
