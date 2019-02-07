package ftn.uns.ac.rs.naucnaCentrala.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ftn.uns.ac.rs.naucnaCentrala.modelDTO.TransactionRequestDto;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.TransactionResponseDto;
import ftn.uns.ac.rs.naucnaCentrala.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public TransactionResponseDto initTransaction(TransactionRequestDto transactionDTO) {
		// TODO Auto-generated method stub
		String url = "http://localhost:8302/api/kp-service/transactions/init";
		ResponseEntity<TransactionResponseDto> response = restTemplate.postForEntity(url, transactionDTO,
				TransactionResponseDto.class);
		System.out.println("ovde*****************sssssssssssss "+response.getBody().toString());
		TransactionResponseDto responseDTO = new TransactionResponseDto();
		//responseDTO.setStatus(response.getBody().getStatus());
		String token = response.getHeaders().getFirst("Transaction-Authorisation");
		responseDTO.setRedirectUrl(response.getBody().getRedirectUrl()+"/"+token);
		System.out.println("ovde*****************sssssssssssss2222222222 "+responseDTO.toString());

		return responseDTO;
	}

}
