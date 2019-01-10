package ftn.uns.ac.rs.naucnaCentrala.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.uns.ac.rs.naucnaCentrala.model.Privilege;


public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}