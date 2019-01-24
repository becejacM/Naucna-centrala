package ftn.uns.ac.rs.naucnaCentrala.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.naucnaCentrala.model.AppUser;
import ftn.uns.ac.rs.naucnaCentrala.model.Magazine;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.MagazineDTO;
import ftn.uns.ac.rs.naucnaCentrala.repository.AppUserRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.MagazineRepository;
import ftn.uns.ac.rs.naucnaCentrala.service.MagazineService;
@Service
public class MagazineServiceImpl implements MagazineService{

	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	MagazineRepository magazineRepository;
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

}
