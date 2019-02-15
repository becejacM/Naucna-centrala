package ftn.uns.ac.rs.naucnaCentrala.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.naucnaCentrala.model.Review;
import ftn.uns.ac.rs.naucnaCentrala.repository.ReviewRepository;
import ftn.uns.ac.rs.naucnaCentrala.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewRepository rep;
	
	@Override
	public Review save(Review r) {
		// TODO Auto-generated method stub
		return rep.save(r);
	}

	@Override
	public Collection<Review> getAll() {
		// TODO Auto-generated method stub
		return rep.findAll();
	}

	@Override
	public Collection<Review> getByPaper(String paperID) {
		// TODO Auto-generated method stub
		return rep.getByPaperId(paperID);
	}

}
