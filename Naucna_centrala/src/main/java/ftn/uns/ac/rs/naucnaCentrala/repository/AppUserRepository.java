package ftn.uns.ac.rs.naucnaCentrala.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ftn.uns.ac.rs.naucnaCentrala.model.AppUser;

import java.util.Optional;

import javax.transaction.Transactional;


@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Page<AppUser> findAll(Pageable pageable);

    AppUser findByUsername(String username);

    AppUser findByEmail(String email);
    
    Page<AppUser> findByVerified(Pageable pageable, boolean verified);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE AppUser user set user.password = ?1 where user.username = ?2")
    void changePassword(String password, String username);
    
    Optional<AppUser> findByEmailAndPassword(String username, String password);

}
