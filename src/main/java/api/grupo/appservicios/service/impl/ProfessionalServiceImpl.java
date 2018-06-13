package api.grupo.appservicios.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import api.grupo.appservicios.data.repository.ProfessionalDAO;
import api.grupo.appservicios.model.dto.ProfessionalDTO;
import api.grupo.appservicios.model.entity.Professional;
import api.grupo.appservicios.model.mapper.ProfessionalMapper;
import api.grupo.appservicios.service.ProfessionalService;

@Service("professionalService")
public class ProfessionalServiceImpl implements ProfessionalService {

	private static final Logger LOGGER = LogManager.getLogger(ProfessionalServiceImpl.class);

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
	public void saveAndUpdateProfessional(ProfessionalDTO professionalDTO) {
		Professional professionalToSave = ProfessionalMapper.INSTANCE.DTOToProfessional(professionalDTO);
		Professional currentProfessional = professionalDAO.findById(professionalToSave.getId());
		boolean isCreation = (professionalToSave.getId() == 0);

		if (isCreation || !professionalToSave.getEmail().equalsIgnoreCase(currentProfessional.getEmail()))
			if (professionalDAO.existsByEmail(professionalToSave.getEmail()))
				throw new DuplicateKeyException("El E-mail se encuentra en uso.");
		if (isCreation || !professionalToSave.getFullIdentity().equalsIgnoreCase(currentProfessional.getFullIdentity()))
			if (professionalDAO.existsByIdentityTypeAndIdentityNumber(professionalToSave.getIdentityType(),
					professionalToSave.getIdentityNumber()))
				throw new DuplicateKeyException("La combinación de tipo y número de documento se encuentra en uso.");
		professionalDAO.save(professionalToSave);
		LOGGER.info("Saved professional with id: {}", professionalToSave.getId());
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
