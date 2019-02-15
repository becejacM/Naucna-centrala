package ftn.uns.ac.rs.naucnaCentrala.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.ac.rs.naucnaCentrala.model.Review;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

	Collection<Review> getByPaperId(String paperID);
}
