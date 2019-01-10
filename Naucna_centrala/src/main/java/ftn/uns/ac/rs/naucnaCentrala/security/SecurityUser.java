package ftn.uns.ac.rs.naucnaCentrala.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ftn.uns.ac.rs.naucnaCentrala.model.AppUser;
import ftn.uns.ac.rs.naucnaCentrala.model.Privilege;
import ftn.uns.ac.rs.naucnaCentrala.model.Role;
import ftn.uns.ac.rs.naucnaCentrala.repository.RoleRepository;


public class SecurityUser implements UserDetails {
	private static final long serialVersionUID = -949811899438278427L;

	@Autowired
	RoleRepository repository;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long id;
	private String password;
	private String email;
	private Collection<? extends GrantedAuthority> privileges;
	private Boolean enabled;
	private String username;
	private String firstname;
	private String lastname;
	private Collection<Role> roles;
	private Collection<? extends GrantedAuthority> role;

	public SecurityUser() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getRole() {
		return role.toString();
	}

	public void setRole(Collection<? extends GrantedAuthority> role) {
		this.role = role;
	}

	public SecurityUser(AppUser user) {
		this.username = user.getUsername();
		this.id = user.getId();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.privileges = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole().toString());
		this.enabled = user.getVerified();
		this.firstname = user.getFirstname();
		this.lastname = user.getLastname();
		this.roles = user.getRoles();
		this.role = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole().toString());
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	/*@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authoritiess = new ArrayList<>();
		if (this.role.toString().equals("[ADMIN]"))
			authoritiess.add(new SimpleGrantedAuthority("ADMIN"));
		else if (this.role.toString().equals("[OPERATOR]"))
			authoritiess.add(new SimpleGrantedAuthority("OPERATOR"));
		return authoritiess;
	}*/

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<Role> roles = new ArrayList<Role>();
        /*if (this.role.toString().equals("[ADMIN]")) {
        	System.out.println("evo meeeeeeee");
        	roles.add(repository.findByName("ADMIN"));
        }
		else if (this.role.toString().equals("[OPERATOR]")) {
			roles.add(repository.findByName("OPERATOR"));
		}*/
		roles=this.roles;
        
        System.out.println(this.role.toString());
        final List<String> privileges = new ArrayList<String>();
        final List<Privilege> collection = new ArrayList<Privilege>();
        for (final Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (final Privilege item : collection) {
            privileges.add(item.getName());
        }
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        System.out.println(authorities.size());
        return authorities;
        

	}

    


}
