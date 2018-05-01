package api.grupo.appservicios.data.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.grupo.appservicios.model.Client;

//
@Repository
public interface ClientDAO extends JpaRepository<Client, Serializable>{

	public abstract Client findById(long id);
}
