package ftn.uns.ac.rs.naucnaCentrala.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import ftn.uns.ac.rs.naucnaCentrala.model.Magazine;
import ftn.uns.ac.rs.naucnaCentrala.model.Paper;
import ftn.uns.ac.rs.naucnaCentrala.model.PaymentMethod;
import ftn.uns.ac.rs.naucnaCentrala.model.Subscription;
import ftn.uns.ac.rs.naucnaCentrala.model.Transaction;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.NCTransactionRequestDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.NCTransactionResponseDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.SubscriptionRequestDto;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.TransactionRequestDto;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.TransactionResponseDto;
import ftn.uns.ac.rs.naucnaCentrala.repository.ArticleRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.MagazineRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.SubscriptionRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.TransactionRepository;
import ftn.uns.ac.rs.naucnaCentrala.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	SubscriptionRepository subRepository;
	
	@Autowired
	ArticleRepository paperRepository;
	
	@Autowired
	MagazineRepository magazineRepository;
	
	@Override
	public TransactionResponseDto initTransaction(NCTransactionRequestDTO nctransactionDTO) {
		// TODO Auto-generated method stub
		Transaction t = new Transaction();
		t.setKupac(nctransactionDTO.getKupac());
		t.setNaslovRada(nctransactionDTO.getNaslovRada());
		t.setStatus("created");
		Transaction tt = transactionRepository.save(t);
		
		Paper paper = paperRepository.findByNaslovRada(nctransactionDTO.getNaslovRada());
		Magazine magazine = magazineRepository.findByName(paper.getMagazine().getName());
		
		TransactionRequestDto transactionDTO = new TransactionRequestDto();
		transactionDTO.setFailUrl("http://localhost:4200/fail/"+t.getId());
		transactionDTO.setSuccessUrl("http://localhost:4200/success/"+t.getId());
		transactionDTO.setAmount(paper.getCena());
		transactionDTO.setDescription(t.getNaslovRada());
		transactionDTO.setSellerUuid(magazine.getMid());
		
		System.out.println(transactionDTO.toString());
		String url = "https://192.168.0.16:8083/api/kp-service/transactions/init";
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
	
	private final String successUrl = "http://localhost:8400/success";
	private final String failUrl = "http://localhost:8400/fail";
    public NCTransactionResponseDTO success(String id) {
    	Transaction t = transactionRepository.getOne(Long.valueOf(id));
    	t.setStatus("success");
    	transactionRepository.save(t);
    	System.out.println(id);
    	//String successUrl = restTemplate.getForEntity(url, String.class).getBody();
        return new NCTransactionResponseDTO("success");
    }
    
    public NCTransactionResponseDTO fail(String id) {
    	Transaction t = transactionRepository.getOne(Long.valueOf(id));
    	t.setStatus("fail");
    	transactionRepository.save(t);
    	System.out.println(id);
    	//String failUrl = restTemplate.getForEntity(url, String.class).getBody();
        return new NCTransactionResponseDTO("fail");
    }

	@Override
	public NCTransactionResponseDTO proveriKupovinu(NCTransactionRequestDTO transactionDTO) {
		// TODO Auto-generated method stub
		Collection<Transaction> transactions = transactionRepository.findByNaslovRadaAndKupacAndStatus(transactionDTO.getNaslovRada(), transactionDTO.getKupac(), "success");
		if(transactions.isEmpty()) {
			return new NCTransactionResponseDTO(false);
		}
		return new NCTransactionResponseDTO(true);
	}

	@Override
	public TransactionResponseDto subscribe(NCTransactionRequestDTO nctransactionDTO) {
		Subscription s = new Subscription();
		s.setKupac(nctransactionDTO.getKupac());
		s.setNazivCasopisa(nctransactionDTO.getProdavac());
		s.setStatus("created");
		Subscription ss = subRepository.save(s);
		
		//Paper paper = paperRepository.findByNaslovRada(nctransactionDTO.getNaslovRada());
		Magazine magazine = magazineRepository.findByName(nctransactionDTO.getProdavac());
		
		SubscriptionRequestDto subDTO = new SubscriptionRequestDto();
		subDTO.setFailUrl("http://localhost:4200/subscribe/fail/"+s.getId());
		subDTO.setSuccessUrl("http://localhost:4200/subscribe/success/"+s.getId());
		subDTO.setSellerUuid(magazine.getMid());
		
		System.out.println(subDTO.toString());
		String url = "https://192.168.0.16:8083/api/kp-service/subscription";
		ResponseEntity<TransactionResponseDto> response = restTemplate.postForEntity(url, subDTO,
				TransactionResponseDto.class);
		System.out.println("ovde*****************sssssssssssss "+response.getBody().toString());
		TransactionResponseDto responseDTO = new TransactionResponseDto();
		//responseDTO.setStatus(response.getBody().getStatus());
		String token = response.getHeaders().getFirst("Transaction-Authorisation");
		responseDTO.setRedirectUrl(response.getBody().getRedirectUrl()+"/"+token);
		System.out.println("ovde*****************sssssssssssss2222222222 "+responseDTO.toString());

		return responseDTO;
	}

	@Override
	public NCTransactionResponseDTO successSubscription(String id) {
		Subscription t = subRepository.getOne(Long.valueOf(id));
    	t.setStatus("success");
    	subRepository.save(t);
    	System.out.println(id);
    	//String failUrl = restTemplate.getForEntity(url, String.class).getBody();
        return new NCTransactionResponseDTO("success");
	}

	@Override
	public NCTransactionResponseDTO failSubscription(String id) {
		Subscription t = subRepository.getOne(Long.valueOf(id));
    	t.setStatus("fail");
    	subRepository.save(t);
    	System.out.println(id);
    	//String failUrl = restTemplate.getForEntity(url, String.class).getBody();
        return new NCTransactionResponseDTO("fail");
	}

	@Override
	public Collection<Paper> getAllForBy(String username) {
		Collection<Paper> papers = new ArrayList<Paper>();
		papers = paperRepository.findAll();
		Collection<Paper> papersForBuy = new ArrayList<Paper>();
		for (Paper paper : papers) {
			if(paper.getMagazine().getPaymentMethod().equals(PaymentMethod.WITH_SUBSCRIPTION)) {
				Boolean kupljen = false;
				for (Transaction t : transactionRepository.findAll()) {
					if(t.getNaslovRada().equals(paper.getNaslovRada()) && t.getStatus().equals("success")  && t.getKupac().equals(username)) {
						kupljen=true;
					}
				}
				if(!kupljen) {
					papersForBuy.add(paper);

				}
			}
		}
		return papersForBuy;
	}

	@Override
	public Collection<Paper> getAllForDownload(String username) {
		Collection<Paper> papers = new ArrayList<Paper>();
		papers = paperRepository.findAll();
		Collection<Paper> papersForDownload = new ArrayList<Paper>();
		for (Paper paper : papers) {
			if(paper.getMagazine().getPaymentMethod().equals(PaymentMethod.WITH_SUBSCRIPTION)) {
				for (Transaction t : transactionRepository.findAll()) {
					if(t.getNaslovRada().equals(paper.getNaslovRada()) && t.getStatus().equals("success")  && t.getKupac().equals(username)) {
						papersForDownload.add(paper);
					}
				}
			}
			else {
				papersForDownload.add(paper);
			}
		}
		return papersForDownload;
	}

}
