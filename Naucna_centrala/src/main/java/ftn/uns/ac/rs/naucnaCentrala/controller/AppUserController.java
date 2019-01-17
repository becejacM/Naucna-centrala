package ftn.uns.ac.rs.naucnaCentrala.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.uns.ac.rs.naucnaCentrala.NaucnaCentralaApplication;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.AuthenticationRequestDto;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.AuthenticationResponseDto;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.ChangePasswordDto;
import ftn.uns.ac.rs.naucnaCentrala.security.SecurityUser;
import ftn.uns.ac.rs.naucnaCentrala.security.TokenUtils;
import ftn.uns.ac.rs.naucnaCentrala.service.UserExtendedService;


@RestController
@RequestMapping("/users")
//@CrossOrigin
public class AppUserController {
    private static final Logger logger = LoggerFactory.getLogger(NaucnaCentralaApplication.class);

    @Value("${nc.token.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserExtendedService userDetailsService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequestDto authenticationRequest) {
        // Perform the authentication
        Authentication authentication = null;
        UsernamePasswordAuthenticationToken t = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword());
        try {
            authentication = this.authenticationManager.authenticate(t);
        } catch (AuthenticationException e) {
            logger.warn(String.format("Invalid login with username: %s",
                    authenticationRequest.getUsername()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-authentication so we can generate token
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//        for (GrantedAuthority g : userDetails.getAuthorities()) {
//            if (g.toString().equals("ADMIN")) {
//                MongoClientURI mongoClientURI = new MongoClientURI("mongodb://admin:MDJ421054-bsep@localhost:27017/bezbednost");
//                MongoClient mongo = new MongoClient(mongoClientURI);
//            } else {
//                MongoClientURI mongoClientURI = new MongoClientURI("mongodb://operator:Operator-bsep@localhost:27017/bezbednost");
//                MongoClient mongo = new MongoClient(mongoClientURI);
//            }
//        }

        SecurityUser su = (SecurityUser) userDetails;
        String token = this.tokenUtils.generateToken(userDetails);
        // Return the token
        AuthenticationResponseDto authResponse = new AuthenticationResponseDto(token, su.getId(), su.getEmail(),su.getRole(),
                su.getAuthorities().toString(), su.isEnabled(), su.getUsername(), su.getFirstname(), su.getLastname());
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }


    @PutMapping(value = "/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) throws IOException {
        boolean changedPassword = this.userDetailsService.changePassword
                (changePasswordDto.getCurrentPassword()
                        , changePasswordDto.getNewPassword(),
                        changePasswordDto.getUsername());
        if(changedPassword) {
            return new ResponseEntity<>("Password has been successfully changed",HttpStatus.OK);
        }
        return new ResponseEntity<>("Current password is not valid",HttpStatus.BAD_REQUEST);
    }

//    public static String hashPassword(String password_plaintext) {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String s = bCryptPasswordEncoder.encode(password_plaintext);
//        return (s);
//    }
}