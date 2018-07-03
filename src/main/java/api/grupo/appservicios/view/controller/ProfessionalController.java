package api.grupo.appservicios.view.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
@RequestMapping("/professional")
public class ProfessionalController {
	private static final String PROFESSIONALS_LIST = "professionals-list";
	private static final String PROFESSIONAL_FORM = "professional-form";

	/**
	 * Está referenciado a dos apéndices(uno de tipo RollingFile y otro de tipo
	 * Console): se encarga de loguear a un archivo a partir del threshold TRACE,
	 * mientras que el apéndice Console imprime los mensajes de threshold INFO y
	 * sólo INFO de los subpackages api.grupo.appservicios.
	 */
	private static final Logger LOGGER = LogManager.getLogger(ProfessionalController.class);
	/**
	 * Únicamente imprime los mensajes por consola. Razón: En caso de tener que
	 * loguear una excepción, no es necesario mostrar el stacktrace por consola. Es
	 * decir, hace un "aviso" utilizando el mismo level y en caso de necesitar más
	 * detalles se recurre al log.
	 */
	private static final Logger CONSOLE = LogManager.getLogger("api.grupo.appservicios.ProfessionalController");

	@Autowired
	private ProfessionalService professionalService;

	@GetMapping("/list")
	public String listAllProfessionals(Model model) {
		LOGGER.debug("Processing 'listAllProfessionals' request");
		model.addAttribute("professionals", professionalService.listAllProfessionals());
		return PROFESSIONALS_LIST;
	}
	
	@GetMapping("/form")
	public String showProfessionalForm(@RequestParam(value = "id", defaultValue = "0", required = false) long id,
			Model model) {
		ProfessionalDTO professionalDTO = professionalService.findProfessional(id);
		if (professionalDTO == null)
			professionalDTO = new ProfessionalDTO();
		model.addAttribute("professional", professionalDTO);
		return PROFESSIONAL_FORM;
	}

	@PostMapping("/add")
	public String addProfessional(@Valid @ModelAttribute("professional") ProfessionalDTO professionalDTO,
			BindingResult bindingResult, Model model) {
		LOGGER.debug("Processing 'addProfessional' request");
		if (bindingResult.hasErrors()) {
			LOGGER.info("Failed to validate professional: {}", bindingResult);
			return PROFESSIONAL_FORM;
		} else {
			try {
				professionalService.saveAndUpdateProfessional(professionalDTO);
			} catch (DuplicateKeyException e) {
				CONSOLE.warn("Professional could not be saved: {}", e.getMessage());
				LOGGER.warn("An exception occurred while trying to save a professional:", e);
				model.addAttribute("errorMessage", e.getMessage());
				return PROFESSIONAL_FORM;
			}
			model.addAttribute("successMessage",
					"Profesional: " + professionalDTO.getSurname() + ", " + professionalDTO.getName() + " guardado!");
			return listAllProfessionals(model);
		}
	}

	@GetMapping("/delete")
	public String deleteProfessional(@RequestParam(value = "id", required = true) long id, Model model) {
		LOGGER.debug("Processing 'deleteProfessional' request");
		ProfessionalDTO professionalDTO = professionalService.findProfessional(id);
		if (professionalDTO == null) {
			LOGGER.info("Professional with id {} was not found", id);
			model.addAttribute("errorMessage", "Error! No se encontró el profesional a eliminar.");
			return listAllProfessionals(model);
		}
		professionalService.removeProfessional(id);
		model.addAttribute("successMessage",
				"Profesional: " + professionalDTO.getSurname() + ", " + professionalDTO.getName() + " eliminado!");
		return listAllProfessionals(model);
	}
}
