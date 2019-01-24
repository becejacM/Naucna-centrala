package ftn.uns.ac.rs.naucnaCentrala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.ac.rs.naucnaCentrala.model.EditorialBoard;

@Repository
public interface EditorialBoardRepository extends JpaRepository<EditorialBoard, Long>{

}
