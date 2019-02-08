package ftn.uns.ac.rs.naucnaCentrala.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.naucnaCentrala.model.AppUser;
import ftn.uns.ac.rs.naucnaCentrala.model.Magazine;
import ftn.uns.ac.rs.naucnaCentrala.model.Paper;
import ftn.uns.ac.rs.naucnaCentrala.model.PaymentMethod;
import ftn.uns.ac.rs.naucnaCentrala.model.Subscription;
import ftn.uns.ac.rs.naucnaCentrala.model.Transaction;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.MagazineDTO;
import ftn.uns.ac.rs.naucnaCentrala.repository.AppUserRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.MagazineRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.SubscriptionRepository;
import ftn.uns.ac.rs.naucnaCentrala.service.MagazineService;
@Service
public class MagazineServiceImpl implements MagazineService{

	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	MagazineRepository magazineRepository;
	
	@Autowired
	SubscriptionRepository subscriptionRepository;
	
	@Override
	public Collection<MagazineDTO> getAll(String username) {
		AppUser user = this.appUserRepository.findByUsername(username);
		Collection<Magazine> magazines = this.magazineRepository.findAll();
		Collection<MagazineDTO> magazinesDTO = new ArrayList<>();
		for(Magazine m : magazines) {
			magazinesDTO.add(new MagazineDTO(m));
		}
		return magazinesDTO;
	}
	
	@Override
	public Collection<MagazineDTO> getAllForSubscribe(String username) {
		Collection<Magazine> magazines = this.magazineRepository.findAll();
		Collection<Magazine> magazinesForSubscribe = new ArrayList<Magazine>();
		for (Magazine m : magazines) {
			if(m.getPaymentMethod().equals(PaymentMethod.OPEN_ACCESS)) {
				Boolean pretplacen = false;
				for (Subscription t : subscriptionRepository.findAll()) {
					if(t.getNazivCasopisa().equals(m.getName()) && t.getStatus().equals("success")  && t.getKupac().equals(username)) {
						pretplacen=true;
					}
				}
				if(!pretplacen) {
					magazinesForSubscribe.add(m);

				}
			}
		}
		Collection<MagazineDTO> magazinesDTO = new ArrayList<>();
		for(Magazine m : magazinesForSubscribe) {
			magazinesDTO.add(new MagazineDTO(m));
		}
		return magazinesDTO;
	}

	@Override
	public Collection<MagazineDTO> getAllSubscribed(String username) {
		Collection<Magazine> magazines = this.magazineRepository.findAll();
		Collection<Magazine> magazinesForSubscribe = new ArrayList<Magazine>();
		for (Magazine m : magazines) {
			if(m.getPaymentMethod().equals(PaymentMethod.OPEN_ACCESS)) {
				for (Subscription t : subscriptionRepository.findAll()) {
					if(t.getNazivCasopisa().equals(m.getName()) && t.getStatus().equals("success")  && t.getKupac().equals(username)) {
						magazinesForSubscribe.add(m);
					}
				}
			}
			else {
				magazinesForSubscribe.add(m);
			}
		}
		Collection<MagazineDTO> magazinesDTO = new ArrayList<>();
		for(Magazine m : magazinesForSubscribe) {
			magazinesDTO.add(new MagazineDTO(m));
		}
		return magazinesDTO;
	}
}
