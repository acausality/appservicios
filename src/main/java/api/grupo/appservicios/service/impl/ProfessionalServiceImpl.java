package api.grupo.appservicios.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.grupo.appservicios.data.repository.ProfessionalDAO;
import api.grupo.appservicios.model.converter.ProfessionalConverter;
import api.grupo.appservicios.model.dto.ProfessionalDTO;
import api.grupo.appservicios.model.entity.Professional;
import api.grupo.appservicios.service.ProfessionalService;

@Service("professionalService")
public class ProfessionalServiceImpl implements ProfessionalService {

	@Autowired
	private ProfessionalDAO professionalDAO;

	@Autowired
	private ProfessionalConverter professionalConverter;

	@Override
	public List<ProfessionalDTO> listAllProfessionals() {
		List<Professional> professionals = professionalDAO.findAll();
		List<ProfessionalDTO> professionalDTOs = new ArrayList<>();
		for (Professional professional : professionals)
			professionalDTOs.add(professionalConverter.entityToDTO(professional));
		return professionalDTOs;
	}

	@Override
	public Professional addProfessional(ProfessionalDTO professionalDTO) {
		return professionalDAO.save(professionalConverter.DTOtoEntity(professionalDTO));
	}

	@Override
	public long removeProfessional(long id) {
		if(professionalDAO.existsById(id))
			professionalDAO.deleteById(id);
		return 0;
	}

	@Override
	public Professional updateProfessional(ProfessionalDTO professionalDTO) {
		return professionalDAO.save(professionalConverter.DTOtoEntity(professionalDTO));
	}

	@Override
	public ProfessionalDTO findProfessional(long id) {
		Professional professional = professionalDAO.findById(id);
		if (professional == null)
			return null;
		return professionalConverter.entityToDTO(professional);
	}

}
