package ftn.uns.ac.rs.naucnaCentrala.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ftn.uns.ac.rs.naucnaCentrala.NaucnaCentralaApplication;
import ftn.uns.ac.rs.naucnaCentrala.model.AppUser;
import ftn.uns.ac.rs.naucnaCentrala.repository.AppUserRepository;
import ftn.uns.ac.rs.naucnaCentrala.security.SecurityUser;
import ftn.uns.ac.rs.naucnaCentrala.service.UserExtendedService;
import ftn.uns.ac.rs.naucnaCentrala.utils.AES;
import ftn.uns.ac.rs.naucnaCentrala.utils.PasswordChecker;

@Service
public class UserServiceDetailsImpl implements UserExtendedService {
	
    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    private AES aesService;
    
    private PasswordChecker passwordChecker;

    private static final Logger logger = LoggerFactory.getLogger(NaucnaCentralaApplication.class);

    @Override
    public UserDetails loadUserByUsername(String username) {
    	System.out.println(username);
    	String dekriptovanEmail = AES.encrypt(username);
    	System.out.println(dekriptovanEmail);
        //AppUser user = this.userRepository.findByUsername(username);
    	AppUser user = this.userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return new SecurityUser(user);
        }
    }

    @Transactional
    @Override
    public boolean changePassword(String oldPass,String newPass, String username) throws IOException {
        UsernamePasswordAuthenticationToken t = new UsernamePasswordAuthenticationToken(
                username, oldPass);
        
        if(!passwordChecker.checkNewPass(newPass)) {
        	logger.warn(String.format("Someone try to change password but unsuccessfully," +
                    " wrong current password for username %s",
            username));
        	return false;
        }
        try {
            this.authenticationManager.authenticate(t);
            newPass = new BCryptPasswordEncoder().encode(newPass);
            this.userRepository.changePassword(newPass,username);
            return true;
        } catch (AuthenticationException e) {
            logger.warn(String.format("Someone try to change password but unsuccessfully," +
                            " wrong current password for username %s",
                    username));
            return false;
        }
    }

	@Override
	public AppUser save(AppUser user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public Optional<AppUser> findByUsernameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		return userRepository.findByEmailAndPassword(username, password);
	}
    
    


    
}
