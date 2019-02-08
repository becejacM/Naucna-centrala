package ftn.uns.ac.rs.naucnaCentrala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.ac.rs.naucnaCentrala.model.Paper;

@Repository
public interface ArticleRepository extends JpaRepository<Paper, Long>{

	Paper findByNaslovRada(String naslovRada);
}
