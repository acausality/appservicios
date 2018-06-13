package api.grupo.appservicios.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.grupo.appservicios.data.repository.ProfessionalDAO;
import api.grupo.appservicios.model.dto.ProfessionalDTO;
import api.grupo.appservicios.model.entity.Professional;
import api.grupo.appservicios.model.mapper.ProfessionalMapper;
import api.grupo.appservicios.service.ProfessionalService;

@Service("professionalService")
public class ProfessionalServiceImpl implements ProfessionalService {
	
	private static final Logger LOGGER = LogManager.getLogger();

	@Autowired
	private ProfessionalDAO professionalDAO;

	@Override
	public List<ProfessionalDTO> listAllProfessionals() {
		List<Professional> professionals = professionalDAO.findAll();
		List<ProfessionalDTO> professionalDTOs = new ArrayList<>();
		for (Professional professional : professionals)
			professionalDTOs.add(ProfessionalMapper.INSTANCE.professionalToDTO(professional));
		return professionalDTOs;
	}

	@Override
	public Professional saveAndUpdateProfessional(ProfessionalDTO professionalDTO) {
		Professional professionalToSave = ProfessionalMapper.INSTANCE.DTOToProfessional(professionalDTO);
		professionalDAO.save(professionalToSave);
		LOGGER.info("Saved professional with id: {}", professionalToSave.getId());
		return professionalToSave;
	}

	@Override
	public void removeProfessional(long id) {
		if (professionalDAO.existsById(id)) {
			professionalDAO.deleteById(id);
			LOGGER.info("Professional with id {} removed", id);
		}
	}

	@Override
	public ProfessionalDTO findProfessional(long id) {
		Professional professional = professionalDAO.findById(id);
		if (professional == null)
			return null;
		return ProfessionalMapper.INSTANCE.professionalToDTO(professional);
	}

}
