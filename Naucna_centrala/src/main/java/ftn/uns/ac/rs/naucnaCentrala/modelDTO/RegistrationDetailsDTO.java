package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

import ftn.uns.ac.rs.naucnaCentrala.model.UserRole;

public class RegistrationDetailsDTO {

	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String email;
	private String city;
	private String state;
	private String lat;
	private String lng;
	private UserRole role;
	private String processInstanceId;
	//#{registrationTaskService.checkData(firstname, lastname, username, password, email, city, state)}
	//#{registrationTaskService.persistData(firstname, lastname, username, password, email, city, state)}
    //"start": "ng serve --proxy-config proxy.conf.json --ssl 1 --ssl-key '/src/assets/angular.key' --ssl-cert '/src/assets/angular.crt'",
	public RegistrationDetailsDTO() {
		
	}

	public RegistrationDetailsDTO(String firstname, String lastname, String username, String password, String email,
			String city, String state, String lat, String lng, UserRole role, String processInstanceId) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.city = city;
		this.state = state;
		this.lat = lat;
		this.lng = lng;
		this.role = role;
		this.processInstanceId = processInstanceId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
}
