package api.grupo.appservicios.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.grupo.appservicios.data.repository.ProfessionalDAO;
import api.grupo.appservicios.model.dto.ProfessionalDTO;
import api.grupo.appservicios.model.entity.Professional;
import api.grupo.appservicios.model.mapper.ProfessionalMapper;
import api.grupo.appservicios.service.ProfessionalService;

@Service("professionalService")
public class ProfessionalServiceImpl implements ProfessionalService {

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
	public List<ProfessionalDTO> listProfessionalsSignedUpToday() {
		List<Professional> professionals = professionalDAO.findBySignupDate(LocalDate.now());
		List<ProfessionalDTO> professionalDTOs = new ArrayList<>();
		for (Professional professional : professionals)
			professionalDTOs.add(ProfessionalMapper.INSTANCE.professionalToDTO(professional));
		return professionalDTOs;
	}

	@Override
	public Professional saveAndUpdateProfessional(ProfessionalDTO professionalDTO) {
		return professionalDAO.save(ProfessionalMapper.INSTANCE.DTOToProfessional(professionalDTO));
	}

	@Override
	public void removeProfessional(long id) {
		if (professionalDAO.existsById(id))
			professionalDAO.deleteById(id);
	}

	@Override
	public ProfessionalDTO findProfessional(long id) {
		Professional professional = professionalDAO.findById(id);
		if (professional == null)
			return null;
		return ProfessionalMapper.INSTANCE.professionalToDTO(professional);
	}

}
