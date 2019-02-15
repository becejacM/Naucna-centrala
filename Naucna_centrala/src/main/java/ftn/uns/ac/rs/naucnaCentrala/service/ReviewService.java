package ftn.uns.ac.rs.naucnaCentrala.service;

import java.util.Collection;

import ftn.uns.ac.rs.naucnaCentrala.model.Review;

public interface ReviewService {

	Review save(Review r);
	Collection<Review> getAll();
	
	Collection<Review> getByPaper(String paperID);
}
