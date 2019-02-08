package ftn.uns.ac.rs.naucnaCentrala.service;

import org.springframework.web.servlet.ModelAndView;

import ftn.uns.ac.rs.naucnaCentrala.modelDTO.NCTransactionRequestDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.NCTransactionResponseDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.TransactionRequestDto;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.TransactionResponseDto;

public interface TransactionService {

	public TransactionResponseDto initTransaction(NCTransactionRequestDTO transactionDTO);
	public NCTransactionResponseDTO success(String id);
	public NCTransactionResponseDTO fail(String id);
	public NCTransactionResponseDTO proveriKupovinu(NCTransactionRequestDTO transactionDTO);

}
