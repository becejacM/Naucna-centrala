package ftn.uns.ac.rs.naucnaCentrala.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.naucnaCentrala.model.AppUser;

@Service
public interface UserExtendedService extends UserDetailsService {

    boolean changePassword(String oldPass, String newPass, String username) throws IOException;
    AppUser save(AppUser user);
    Optional<AppUser> findByUsernameAndPassword(String username, String password);
}
