package api.grupo.appservicios.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import api.grupo.appservicios.model.dto.ClientDTO;
import api.grupo.appservicios.model.entity.Client;

@Mapper
public interface ClientMapper {
	ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

	ClientDTO clientToDTO(Client client);

	Client DTOToClient(ClientDTO clientDTO);
}
