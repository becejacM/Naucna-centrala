package ftn.uns.ac.rs.naucnaCentrala.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ftn.uns.ac.rs.naucnaCentrala.modelDTO.NCTransactionRequestDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.NCTransactionResponseDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.TransactionRequestDto;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.TransactionResponseDto;
import ftn.uns.ac.rs.naucnaCentrala.service.TransactionService;

@RestController
@RequestMapping(value = "/trans")
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@PostMapping(value = "/initi")
	public TransactionResponseDto initTransaction(@RequestBody NCTransactionRequestDTO nctransactionRequest) throws Exception {
		System.out.println("ovde***************** "+nctransactionRequest.toString());
		TransactionResponseDto transactionResponseDto = transactionService.initTransaction(nctransactionRequest);
		System.out.println("ovde***************** "+transactionResponseDto.getRedirectUrl());
		//if (transaction != null) {
			//return new ResponseEntity<TransactionResponseDto>(new TransactionResponseDto(transaction), HttpStatus.OK);
		//}
		//return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		return transactionResponseDto;
	}

	@GetMapping(value = "/success/{id}")
	public NCTransactionResponseDTO successPayment(@PathVariable String id) {
		System.out.println("Platio sam! Uspesna transakcija!");
		System.out.println(id);
		return transactionService.success(id);

	}

	@GetMapping(value = "/fail/{id}")
	public NCTransactionResponseDTO canclePayment(@PathVariable String id) {
		System.out.println("Nisam platio! Neuspesna transakcija");
		System.out.println(id);
		return transactionService.fail(id);
	}

	@PostMapping(value = "/proveraKupovine")
	public NCTransactionResponseDTO proveraKupovine(@RequestBody NCTransactionRequestDTO nctransactionRequest) {
		System.out.println("Provera kupovine");
		return transactionService.proveriKupovinu(nctransactionRequest);
	}
}
