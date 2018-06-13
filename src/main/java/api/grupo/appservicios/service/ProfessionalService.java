package api.grupo.appservicios.service;

import java.util.List;

import api.grupo.appservicios.model.dto.ProfessionalDTO;

public interface ProfessionalService {

	public abstract List<ProfessionalDTO> listAllProfessionals();

	public abstract void saveAndUpdateProfessional(ProfessionalDTO professionalDTO);

	public abstract void removeProfessional(long id);

	public abstract ProfessionalDTO findProfessional(long id);
}
