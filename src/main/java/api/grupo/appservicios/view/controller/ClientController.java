package api.grupo.appservicios.view.controller;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import api.grupo.appservicios.model.dto.ClientDTO;
import api.grupo.appservicios.service.ClientService;

@Controller
@RequestMapping("/clients")
public class ClientController {
	// constantes para los nombres de los templates
	private static final String LIST_CLIENTS = "list-clients";
	private static final String SAVE_CLIENT_FORM = "client-form";

	private Log logger = LogFactory.getLog(ClientController.class);

	@Autowired
	private ClientService clientService;

	// Parte de la validacion: verifica que los strings que llegan no son vacios o
	// espacios, eliminando los espacios al principio y al final
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		// True en el constructor hace que los strings vacios se conviertan en null
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	// Listado de clientes
	@GetMapping("/list")
	public String listClients(Model model) {
		logger.info("Inside listClients");
		model.addAttribute("clients", clientService.listClients());

		return LIST_CLIENTS;
	}

	// Crear cliente nuevo o modificar existente
	@PostMapping("/save")
	public String saveClient(@Valid @ModelAttribute("client") ClientDTO client, BindingResult bindingResult,
			Model model) {
		logger.info("Inside saveClient. Received: " + client);
		if (bindingResult.hasErrors()) {
			logger.error("Failed to validate client: " + client);
			logger.error(bindingResult.toString());

			return SAVE_CLIENT_FORM;
		} else {
			try {
				clientService.saveClient(client);
			} catch (DuplicateKeyException | EmptyResultDataAccessException e) {
				model.addAttribute("errorMessage", e.getMessage());
				return SAVE_CLIENT_FORM;
			}

			model.addAttribute("successMessage",
					"Cliente: " + client.getSurname() + ", " + client.getName() + " guardado!");
			return listClients(model);
		}
	}

	// Formulario de alta/modificación de cliente
	@GetMapping("/form")
	public String showClientForm(@RequestParam(value = "id", defaultValue = "0", required = false) long id,
			Model model) {
		ClientDTO client = clientService.findClient(id);
		if (client == null)
			client = new ClientDTO();
		model.addAttribute("client", client);

		return SAVE_CLIENT_FORM;
	}

	// Eliminación de cliente
	@GetMapping("/remove")
	public String removeClient(@RequestParam(value = "id", required = true) long id, Model model) {
		ClientDTO client = clientService.findClient(id);
		if (client == null) {
			model.addAttribute("errorMessage", "Error! No se encontró el cliente a eliminar.");
			return listClients(model);
		} else {
			clientService.removeClient(id);
			model.addAttribute("successMessage",
					"Cliente: " + client.getSurname() + ", " + client.getName() + " eliminado!");
			return listClients(model);
		}
	}

}
