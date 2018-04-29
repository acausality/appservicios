package api.grupo.appservicios.service;

import java.util.List;

import api.grupo.appservicios.model.dto.ProfessionalDTO;
import api.grupo.appservicios.model.entity.Professional;

public interface ProfessionalService {

	public abstract List<ProfessionalDTO> listAllProfessionals();

	public abstract Professional addProfessional(ProfessionalDTO professionalDTO);

	public abstract long removeProfessional(long id);

	public abstract Professional updateProfessional(ProfessionalDTO professionalDTO);

	public abstract ProfessionalDTO findProfessional(long id);
}
