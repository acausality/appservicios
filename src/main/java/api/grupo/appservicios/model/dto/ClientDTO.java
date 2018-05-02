package api.grupo.appservicios.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClientDTO {

	@NotNull
	@Min(0)
	private long id;
	@NotBlank
	@Size(min = 3, max = 50)
	private String name;
	@NotBlank
	@Size(min = 3, max = 50)
	private String surname;
	@NotBlank
	@Size(max = 30)
	private String identityType;
	@NotBlank
	@Size(min = 6, max = 70)
	private String identityNumber;
	@NotBlank
	@Size(min = 3, max = 50)
	private String address;
	@NotBlank
	@Size(min = 6, max = 50)
	private String phoneNumber;
	@NotBlank
	@Size(min = 7, max = 50)
	private String email;

	public ClientDTO(@NotNull @Min(0) long id, @NotBlank @Size(min = 3, max = 50) String name,
			@NotBlank @Size(min = 3, max = 50) String surname, @NotBlank @Size(max = 30) String identityType,
			@NotBlank @Size(min = 6, max = 70) String identityNumber, @NotBlank @Size(min = 3, max = 50) String address,
			@NotBlank @Size(min = 6, max = 50) String phoneNumber, @NotBlank @Size(min = 7, max = 50) String email) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.identityType = identityType;
		this.identityNumber = identityNumber;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public ClientDTO() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "ClientDTO [id=" + id + ", name=" + name + ", surname=" + surname + ", identityType=" + identityType
				+ ", identityNumber=" + identityNumber + ", address=" + address + ", phoneNumber=" + phoneNumber
				+ ", email=" + email + "]";
	}
}