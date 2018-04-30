package api.grupo.appservicios.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.grupo.appservicios.data.dao.ClientDAO;
import api.grupo.appservicios.model.Client;
import api.grupo.appservicios.model.converter.ClientConverter;
import api.grupo.appservicios.model.dto.ClientDTO;
import api.grupo.appservicios.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientDAO clientDAO;
	
	@Override
	public List<ClientDTO> listClients() {
		List<Client> clients = clientDAO.findAll();
		List<ClientDTO> result = new ArrayList<ClientDTO>();
		for (Client client: clients)
			result.add(ClientConverter.modelToDTO(client));
		
		return result;
	}

	@Override
	public void saveClient(@Valid ClientDTO client) {
		clientDAO.save(ClientConverter.DTOToModel(client));		
	}

	@Override
	public ClientDTO findClient(long id) {
		Client client = clientDAO.findById(id);
		if (client == null)
			return null;
		
		return ClientConverter.modelToDTO(client);
	}

	@Override
	public void removeClient(long id) {
		if (clientDAO.existsById(id))
			clientDAO.deleteById(id);		
	}

}
