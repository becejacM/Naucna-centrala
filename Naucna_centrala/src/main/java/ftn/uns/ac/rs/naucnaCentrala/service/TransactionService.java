package ftn.uns.ac.rs.naucnaCentrala.service;

import ftn.uns.ac.rs.naucnaCentrala.modelDTO.TransactionRequestDto;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.TransactionResponseDto;

public interface TransactionService {

	public TransactionResponseDto initTransaction(TransactionRequestDto transactionDTO);
}
