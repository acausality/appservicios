package api.grupo.appservicios.service;

import java.util.List;

import javax.validation.Valid;

import api.grupo.appservicios.model.dto.ClientDTO;

public interface ClientService {

	List<ClientDTO> listClients();

	void saveClient(@Valid ClientDTO client);

	ClientDTO findClient(long id);

	void removeClient(long id);
}
