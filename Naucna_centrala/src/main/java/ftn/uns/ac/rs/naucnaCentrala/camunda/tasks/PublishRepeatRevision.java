package ftn.uns.ac.rs.naucnaCentrala.camunda.tasks;

import java.util.ArrayList;
import java.util.Collection;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.naucnaCentrala.model.Review;
import ftn.uns.ac.rs.naucnaCentrala.service.ReviewService;

@Service
public class PublishRepeatRevision implements JavaDelegate{

	@Autowired
	ReviewService reviewService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		String paper = (String) execution.getVariable("filename");
		Collection<Review> reviews = reviewService.getByPaper(paper);
		Collection<String> reviewers = new ArrayList<String>();
		for(Review r : reviews) {
			reviewers.add(r.getReviewer());
		}
		
    	execution.setVariable("reviewers", reviewers);

	}

}
