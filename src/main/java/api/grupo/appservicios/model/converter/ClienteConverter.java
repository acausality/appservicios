package api.grupo.appservicios.model.converter;

import api.grupo.appservicios.model.dto.ClienteDTO;
import api.grupo.appservicios.model.entity.Cliente;

public class ClienteConverter {
	public static ClienteDTO modelToDTO(Cliente cliente) {
		return new ClienteDTO(cliente.getId(), cliente.getName(), cliente.getSurname(), cliente.getIdentityType(),
				cliente.getIdentityNumber(), cliente.getAddress(), cliente.getPhoneNumber(), cliente.getEmail());
	}

	public static Cliente DTOToModel(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getName(), clienteDTO.getSurname(), clienteDTO.getIdentityType(),
				clienteDTO.getIdentityNumber(), clienteDTO.getAddress(), clienteDTO.getPhoneNumber(),
				clienteDTO.getEmail(), clienteDTO.getId());
	}
}
