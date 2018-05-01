package api.grupo.appservicios.data.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.grupo.appservicios.model.entity.Cliente;

//momentaneamente lo hacemos asi, automaticamente se va a generar el codigo necesario para hacer los queries con esto 
@Repository
public interface ClienteDAO extends JpaRepository<Cliente, Serializable> {

	public abstract Cliente findById(long id);

	public abstract Cliente findByIdentityTypeAndIdentityNumber(String identityType, String identityNumber);

	public abstract Cliente findByEmail(String email);
}

/*
 * public interface ClienteDAO { // TODO List<ClienteDTO> listarClientes(); }
 */
