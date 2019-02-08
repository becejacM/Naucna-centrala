package ftn.uns.ac.rs.naucnaCentrala.service;

import java.util.Collection;

import org.springframework.web.servlet.ModelAndView;

import ftn.uns.ac.rs.naucnaCentrala.model.Paper;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.NCTransactionRequestDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.NCTransactionResponseDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.TransactionRequestDto;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.TransactionResponseDto;

public interface TransactionService {

	public TransactionResponseDto initTransaction(NCTransactionRequestDTO transactionDTO);
	public NCTransactionResponseDTO success(String id);
	public NCTransactionResponseDTO fail(String id);
	public NCTransactionResponseDTO proveriKupovinu(NCTransactionRequestDTO transactionDTO);
	
	public TransactionResponseDto subscribe(NCTransactionRequestDTO transactionDTO);
	public NCTransactionResponseDTO successSubscription(String id);
	public NCTransactionResponseDTO failSubscription(String id);

	Collection<Paper> getAllForBy(String username);
	Collection<Paper> getAllForDownload(String username);

}
