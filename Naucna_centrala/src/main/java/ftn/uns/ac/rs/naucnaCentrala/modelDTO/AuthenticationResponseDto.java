package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

public class AuthenticationResponseDto {

	private static final long serialVersionUID = 1L;
	private String token;
	private Long id;
	private String email;
	private String role;
	private String roles;
	private Boolean enabled;
	private String username;
	private String firstname;
	private String lastname;

	public AuthenticationResponseDto(String token, Long id, String email,String role, String roles, Boolean enabled, String username,
			String firstname, String lastname) {
		super();
		this.token = token;
		this.id = id;
		this.email = email;
		this.role = role;
		this.roles = roles;
		this.enabled = enabled;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public AuthenticationResponseDto() {
		super();
	}

}
