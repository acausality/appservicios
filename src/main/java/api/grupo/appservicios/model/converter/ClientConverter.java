package api.grupo.appservicios.model.converter;

import api.grupo.appservicios.model.Client;
import api.grupo.appservicios.model.dto.ClientDTO;

public class ClientConverter {
	public static ClientDTO modelToDTO(Client cliente) {
		return new ClientDTO(cliente.getId(), cliente.getFirstName(), cliente.getLastName(),
				cliente.getIdentityType(),	cliente.getIdentityNumber());
	}
	
	public static Client DTOToModel(ClientDTO clienteDTO) {
		return new Client(clienteDTO.getId(), clienteDTO.getFirstName(), clienteDTO.getLastName(), 
				clienteDTO.getIdentityType(), clienteDTO.getIdentityNumber());
	}
}
