package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

public class AuthenticationRequestDto {
	private static final long serialVersionUID = 6624726180748515507L;
	private String username;
	private String password;

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

	public AuthenticationRequestDto(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public AuthenticationRequestDto() {
		super();
	}

}
