package api.grupo.appservicios.model.converter;

import api.grupo.appservicios.model.Cliente;
import api.grupo.appservicios.model.dto.ClienteDTO;

public class ClienteConverter {
	public static ClienteDTO modelToDTO(Cliente cliente) {
		return new ClienteDTO(cliente.getId(), cliente.getNombre(), cliente.getApellido(),
				cliente.getTipoDocumento(),	cliente.getNumeroDocumento());
	}
	
	public static Cliente DTOToModel(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNombre(), clienteDTO.getApellido(), 
				clienteDTO.getTipoDocumento(), clienteDTO.getNumeroDocumento());
	}
}
