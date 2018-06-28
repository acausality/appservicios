package api.grupo.appservicios.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import api.grupo.appservicios.data.repository.ClientDAO;
import api.grupo.appservicios.model.dto.ClientDTO;
import api.grupo.appservicios.model.entity.Client;
import api.grupo.appservicios.model.mapper.ClientMapper;
import api.grupo.appservicios.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Autowired
	ClientDAO clientDAO;

	@Override
	public List<ClientDTO> listClients() {
		List<Client> clients = clientDAO.findAll();
		List<ClientDTO> result = new ArrayList<ClientDTO>();
		for (Client client : clients)
			result.add(ClientMapper.INSTANCE.clientToDTO(client));

		return result;
	}

	@Override
	public void saveClient(@Valid ClientDTO clientDTO) throws DuplicateKeyException, EmptyResultDataAccessException {
		Client newClientData = ClientMapper.INSTANCE.DTOToClient(clientDTO);
		System.out.println(clientDTO);
		System.out.println(newClientData);
		Client currentClientData = clientDAO.findById(newClientData.getId());
		boolean isCreation = (newClientData.getId() == 0);
		// Verificar que es una creacion de cliente nuevo, o bien existe el cliente a
		// modificar
		if (!isCreation && currentClientData == null)
			throw new EmptyResultDataAccessException("Error. No se encontró la ID especificada.", 1);
		// Si es un cliente nuevo, o si se quiere modificar el mail de uno existente,
		// hay que verificar que no exista
		// el email nuevo
		if (isCreation || !currentClientData.getEmail().equalsIgnoreCase(newClientData.getEmail())) {
			if (clientDAO.existsByEmail(newClientData.getEmail()))
				throw new DuplicateKeyException("El E-mail se encuentra en uso.");
		}
		// Idem con el tipo + numero de documento
		if (isCreation || !currentClientData.getFullIdentity().equalsIgnoreCase(newClientData.getFullIdentity())) {
			if (clientDAO.existsByIdentityTypeAndIdentityNumber(newClientData.getIdentityType(),
					newClientData.getIdentityNumber())) {
				throw new DuplicateKeyException("La combinación de tipo y número de documento se encuentra en uso.");
			}
		}
		// Si no se dio ninguna de las excepciones anteriores, guardar el cliente
		clientDAO.save(newClientData);
		LOGGER.info("Saved client with id: {}", newClientData.getId());
	}

	@Override
	public ClientDTO findClient(long id) {
		Client client = clientDAO.findById(id);
		if (client == null)
			return null;

		return ClientMapper.INSTANCE.clientToDTO(client);
	}

	@Override
	public void removeClient(long id) {
		if (clientDAO.existsById(id)) {
			clientDAO.deleteById(id);
			LOGGER.info("Client with id {} removed", id);
		}
	}

}
