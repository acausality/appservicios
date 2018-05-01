package api.grupo.appservicios.service;

import java.util.List;

import api.grupo.appservicios.model.dto.ProfessionalDTO;
import api.grupo.appservicios.model.entity.Professional;

public interface ProfessionalService {

	public abstract List<ProfessionalDTO> listAllProfessionals();

	public abstract Professional saveAndUpdateProfessional(ProfessionalDTO professionalDTO);

	public abstract void removeProfessional(long id);

	public abstract ProfessionalDTO findProfessional(long id);
}
