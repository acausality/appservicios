package api.grupo.appservicios.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClientDTO {

	@NotNull
	@Min(0)
	private long id;

	@NotNull
	@Size(min = 3, max = 50)
	private String firstName;

	@NotNull
	@Size(min = 3, max = 50)
	private String lastName;

	@NotNull
	@Size(max = 50)
	private String identityType;

	@NotNull
	@Size(max = 50)
	private String identityNumber;

	public ClientDTO() {
	}

	public ClientDTO(@NotNull @Min(0) long id, @NotNull @Size(min = 3, max = 50) String firstName,
			@NotNull @Size(min = 3, max = 50) String lastName, @NotNull @Size(max = 50) String identityType,
			@NotNull @Size(max = 50) String identityNumber) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.identityType = identityType;
		this.identityNumber = identityNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	@Override
	public String toString() {
		return "ClientDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", identityType="
				+ identityType + ", identityNumber=" + identityNumber + "]";
	}

}
