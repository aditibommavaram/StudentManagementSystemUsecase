package com.training.studentmanagement.dto;

public class ContactDetails {

	private Long mobile;
	private String email;
	private String address;
	private String country;
	private String state;
	private String city;

	public ContactDetails(Long mobile, String email, String address, String country, String state, String city) {
		this.mobile = mobile;
		this.email = email;
		this.address = address;
		this.country = country;
		this.state = state;
		this.city = city;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "ContactDetails [mobile=" + mobile + ", email=" + email + ", address=" + address + ", country=" + country
				+ ", state=" + state + ", city=" + city + "]";
	}

	public ContactDetails() {

	}

}