package api.grupo.appservicios.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "professionals", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "identity_type", "identity_number" }),
		@UniqueConstraint(columnNames = "email") })
public class Professional extends Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "professional_id")
	private long id;

	public Professional(String name, String surname, String identityType, String identityNumber, String address,
			String phoneNumber, String email, long id) {
		super(name, surname, identityType, identityNumber, address, phoneNumber, email);
		this.id = id;
	}

	public Professional() {
	}

	public long getId() {
		return id;
	}

}
