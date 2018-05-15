package api.grupo.appservicios.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import api.grupo.appservicios.model.dto.ProfessionalDTO;
import api.grupo.appservicios.model.entity.Professional;

@Mapper
public interface ProfessionalMapper {
	ProfessionalMapper INSTANCE = Mappers.getMapper(ProfessionalMapper.class);
	
	ProfessionalDTO professionalToDTO(Professional professional);
	
	Professional DTOToProfessional(ProfessionalDTO professionalDTO);
}
