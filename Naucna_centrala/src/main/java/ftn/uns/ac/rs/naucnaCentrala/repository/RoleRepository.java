package ftn.uns.ac.rs.naucnaCentrala.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.uns.ac.rs.naucnaCentrala.model.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}