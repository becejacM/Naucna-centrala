package ftn.uns.ac.rs.naucnaCentrala.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.ac.rs.naucnaCentrala.model.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{

	//Collection<Subscription> 

}
