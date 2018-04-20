package api.grupo.appservicios.service;

import java.util.List;

import javax.validation.Valid;

import api.grupo.appservicios.model.dto.ClienteDTO;

public interface ClienteService {

	List<ClienteDTO> listarClientes();

	void guardarCliente(@Valid ClienteDTO cliente);

	ClienteDTO buscarCliente(long id);

	void baja(long id);
}
