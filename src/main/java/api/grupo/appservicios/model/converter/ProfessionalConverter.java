package api.grupo.appservicios.model.converter;

import org.springframework.stereotype.Component;

import api.grupo.appservicios.model.dto.ProfessionalDTO;
import api.grupo.appservicios.model.entity.Professional;

@Component("professionalConverter")
public class ProfessionalConverter {

	public ProfessionalDTO entityToDTO(Professional professional) {
		return new ProfessionalDTO(professional.getId(), professional.getName(), professional.getSurname(),
				professional.getIdentityType(), professional.getIdentityNumber(), professional.getAddress(),
				professional.getPhoneNumber(), professional.getEmail());
	}

	public Professional DTOtoEntity(ProfessionalDTO professionalDTO) {
		return new Professional(professionalDTO.getName(), professionalDTO.getSurname(),
				professionalDTO.getIdentityType(), professionalDTO.getIdentityNumber(), professionalDTO.getAddress(),
				professionalDTO.getPhoneNumber(), professionalDTO.getEmail(), professionalDTO.getId());
	}
}
