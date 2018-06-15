package api.grupo.appservicios.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;

@MappedSuperclass
public abstract class Person {

	@Column(name = "name", length = 80, nullable = false)
	protected String name;
	@Column(name = "surname", length = 80, nullable = false)
	protected String surname;
	@Column(name = "identity_type", length = 30, nullable = false)
	protected String identityType;
	@Column(name = "identity_number", length = 70, nullable = false)
	protected String identityNumber;
	@Column(name = "address", nullable = false)
	protected String address;
	@Column(name = "phone_number", length = 70, nullable = false)
	protected String phoneNumber;
	@Column(name = "email", length = 100, nullable = false)
	protected String email;
	@Column(name = "signup_date", nullable = false, updatable = false)
	@CreationTimestamp
	protected LocalDate signupDate;

	public Person(String name, String surname, String identityType, String identityNumber, String address,
			String phoneNumber, String email) {
		super();
		this.name = name;
		this.surname = surname;
		this.identityType = identityType;
		this.identityNumber = identityNumber;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public Person() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
	
	public LocalDate getSignupDate() {
		return signupDate;
	}

	public void setSignupDate(LocalDate signupDate) {
		this.signupDate = signupDate;
	}

	// Usado para comparaciones principalmente
	public String getFullIdentity() {
		return (identityType + " " + identityNumber);
	}
}
