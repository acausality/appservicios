package api.grupo.appservicios.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import api.grupo.appservicios.model.Person;

@Entity
@Table(name = "cliente", uniqueConstraints = {@UniqueConstraint(columnNames = {"identity_type", "identity_number"}), @UniqueConstraint(columnNames = "email")})
public class Cliente extends Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente")
	private long id;

	public Cliente(String name, String surname, String identityType, String identityNumber, String address,
			String phoneNumber, String email, long id) {
		super(name, surname, identityType, identityNumber, address, phoneNumber, email);
		this.id = id;
	}

	public Cliente() {
	}

	public long getId() {
		return id;
	}

}
