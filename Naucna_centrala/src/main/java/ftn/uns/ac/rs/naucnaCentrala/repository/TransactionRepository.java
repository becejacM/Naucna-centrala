package ftn.uns.ac.rs.naucnaCentrala.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.ac.rs.naucnaCentrala.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	Collection<Transaction> findByNaslovRadaAndKupacAndStatus(String naslovRada, String kupac, String status);
}
