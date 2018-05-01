package api.grupo.appservicios.model.converter;

import api.grupo.appservicios.model.dto.ClientDTO;
import api.grupo.appservicios.model.entity.Client;

public class ClientConverter {
	public static ClientDTO modelToDTO(Client client) {
		return new ClientDTO(client.getId(), client.getName(), client.getSurname(), client.getIdentityType(),
				client.getIdentityNumber(), client.getAddress(), client.getPhoneNumber(), client.getEmail());
	}

	public static Client DTOToModel(ClientDTO clientDTO) {
		return new Client(clientDTO.getName(), clientDTO.getSurname(), clientDTO.getIdentityType(),
				clientDTO.getIdentityNumber(), clientDTO.getAddress(), clientDTO.getPhoneNumber(), clientDTO.getEmail(),
				clientDTO.getId());
	}
}