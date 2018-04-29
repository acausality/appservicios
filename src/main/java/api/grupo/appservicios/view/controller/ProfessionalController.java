package api.grupo.appservicios.view.controller;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import api.grupo.appservicios.model.dto.ProfessionalDTO;
import api.grupo.appservicios.service.ProfessionalService;

@Controller
@RequestMapping("/professionals")
public class ProfessionalController {

	private static final Log LOG = LogFactory.getLog(ProfessionalController.class);
	private static final String PROFESSIONALS_LIST = "professionals-list";
	private static final String PROFESSIONAL_FORM = "professional-form";
	// private static final String SUCCESS_MESSAGE = "success-message";

	@Autowired
	private ProfessionalService professionalService;

	@GetMapping("/list")
	public String listAllProfessionals(Model model) {
		model.addAttribute("professionals", professionalService.listAllProfessionals());
		return PROFESSIONALS_LIST;
	}

	@GetMapping("/form")
	public String showProfessionalForm(@RequestParam(value = "id", defaultValue = "0", required = false) long id,
			Model model) {
		ProfessionalDTO professionalDTO = professionalService.findProfessional(id);
		if (professionalDTO == null) {
			LOG.info("DENTRO DE FORM. NO ENCONTRE EL ID: " + id);
			professionalDTO = new ProfessionalDTO();
		}
		LOG.info("DENTRO DE FORM. ENCONTRADO: " + professionalDTO);
		model.addAttribute("professional", professionalDTO);
		return PROFESSIONAL_FORM;
	}

	@PostMapping("/add")
	public String addProfessional(@Valid @ModelAttribute("professional") ProfessionalDTO professionalDTO,
			BindingResult bindingResult) {
		LOG.info("DENTRO DE ADD. RECIBIDO: " + professionalDTO);
		if (bindingResult.hasErrors()) {
			return PROFESSIONAL_FORM;
		} else {
			professionalService.addProfessional(professionalDTO);
			return "professional-form-result"; // en realidad tendría que ser un mensaje en la misma página
		}
	}
	
	@GetMapping("/delete")
	public String deleteProfessional(@RequestParam(value = "id", required = true) long id) {
		professionalService.removeProfessional(id);
		return "redirect:/professionals/list";
	}
}
