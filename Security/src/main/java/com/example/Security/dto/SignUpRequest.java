package com.example.Security.dto;

public class SignUpRequest {

	private String email;
    private String password;
    private String username;
    private String phone;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String userName) {
		this.username = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
    
}
