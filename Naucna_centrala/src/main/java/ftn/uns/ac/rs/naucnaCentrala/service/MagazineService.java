package ftn.uns.ac.rs.naucnaCentrala.service;

import java.util.Collection;

import ftn.uns.ac.rs.naucnaCentrala.modelDTO.MagazineDTO;

public interface MagazineService {
	public Collection<MagazineDTO> getAll(String username);
	public Collection<MagazineDTO> getAllForSubscribe(String username);
	public Collection<MagazineDTO> getAllSubscribed(String username);


}
