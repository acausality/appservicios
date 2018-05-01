package api.grupo.appservicios.data.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.grupo.appservicios.model.entity.Client;

//
@Repository
public interface ClientDAO extends JpaRepository<Client, Serializable> {

	public abstract Client findById(long id);
	
	public abstract boolean existsByIdentityTypeAndIdentityNumber(String identityType, String identityNumber);
	
	public abstract boolean existsByEmail(String email);
}
