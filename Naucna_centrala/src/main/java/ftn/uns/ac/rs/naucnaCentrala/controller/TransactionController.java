package ftn.uns.ac.rs.naucnaCentrala.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import ftn.uns.ac.rs.naucnaCentrala.model.Magazine;
import ftn.uns.ac.rs.naucnaCentrala.model.Paper;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.MagazineDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.NCTransactionRequestDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.NCTransactionResponseDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.SearchHitDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.TransactionRequestDto;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.TransactionResponseDto;
import ftn.uns.ac.rs.naucnaCentrala.security.TokenUtils;
import ftn.uns.ac.rs.naucnaCentrala.service.MagazineService;
import ftn.uns.ac.rs.naucnaCentrala.service.TransactionService;

@RestController
@RequestMapping(value = "/trans")
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@Autowired
	MagazineService magazineService;
	
	@Autowired
	TokenUtils tokenUtils;
    
	@Autowired
	HttpServletRequest httpServletRequest;
	
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
	
	@GetMapping("/magazineForSubscribe")
    public ResponseEntity getAllMagazineForSubscribe() {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
        Collection<MagazineDTO> magazineList  = magazineService.getAllForSubscribe(username);        

        return new ResponseEntity<>(magazineList, HttpStatus.OK);
    }
	
	@GetMapping("/magazineSubscribed")
    public ResponseEntity getAllMagazineSubscribed() {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
        Collection<MagazineDTO> magazineList  = magazineService.getAllSubscribed(username);        

        return new ResponseEntity<>(magazineList, HttpStatus.OK);
    }
	@PostMapping(value = "/subscribe")
	public TransactionResponseDto subscribe(@RequestBody NCTransactionRequestDTO nctransactionRequest) throws Exception {
		System.out.println("ovde***************** "+nctransactionRequest.toString());
		TransactionResponseDto transactionResponseDto = transactionService.subscribe(nctransactionRequest);
		System.out.println("ovde***************** "+transactionResponseDto.getRedirectUrl());
		return transactionResponseDto;
	}
	
	@GetMapping(value = "/subscribe/success/{id}")
	public NCTransactionResponseDTO successSubscription(@PathVariable String id) {
		System.out.println("Platio sam! Uspesna subscripcija!");
		System.out.println(id);
		return transactionService.successSubscription(id);

	}

	@GetMapping(value = "/subscribe/fail/{id}")
	public NCTransactionResponseDTO failSubscription(@PathVariable String id) {
		System.out.println("Nisam platio! Neuspesna subscripcija");
		System.out.println(id);
		return transactionService.failSubscription(id);
	}
	
	
	@GetMapping("/getForBuy")
    public ResponseEntity getAllForBuy() {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		Collection<Paper> articleList  = transactionService.getAllForBy(username);
        List<SearchHitDTO> articleDTOList = new ArrayList<SearchHitDTO>();
        for (Paper a : articleList) {
        	System.out.println(a.getFilename());
			SearchHitDTO resultData = new SearchHitDTO(a);
			articleDTOList.add(resultData);

		}

        return new ResponseEntity<>(articleDTOList, HttpStatus.OK);
    }
	
	@GetMapping("/getForDownload")
    public ResponseEntity getAllForDownload() {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		Collection<Paper> articleList  = transactionService.getAllForDownload(username);
        List<SearchHitDTO> articleDTOList = new ArrayList<SearchHitDTO>();
        for (Paper a : articleList) {
        	System.out.println(a.getFilename());
			SearchHitDTO resultData = new SearchHitDTO(a);
			articleDTOList.add(resultData);

		}

        return new ResponseEntity<>(articleDTOList, HttpStatus.OK);
    }
}
