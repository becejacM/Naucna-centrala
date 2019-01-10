package ftn.uns.ac.rs.naucnaCentrala.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserExtendedService extends UserDetailsService {

    boolean changePassword(String oldPass, String newPass, String username);
}
