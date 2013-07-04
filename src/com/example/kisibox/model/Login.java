package com.example.kisibox.model;

public class Login {

	private String userName;
	private String pasword;
	
	public Login(String userName,String pasword){
		this.userName = userName;
		this.pasword = pasword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPasword(String pasword) {
		this.pasword = pasword;
	}

	public String getPasword() {
		return pasword;
	}
}
