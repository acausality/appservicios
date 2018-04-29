package api.grupo.appservicios.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person {

	@Column(name = "name")
	protected String name;
	@Column(name = "surname")
	protected String surname;
	@Column(name = "identity_type")
	protected String identityType;
	@Column(name = "identity_number")
	protected String identityNumber;
	@Column(name = "address")
	protected String address;
	@Column(name = "phone_number")
	protected String phoneNumber;
	@Column(name = "email")
	protected String email;

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

}
