package api.grupo.appservicios.view.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@RequestMapping("/client")
public class ClientController {
	// constantes para los nombres de los templates
	private static final String LIST_CLIENTS = "list-clients";
	private static final String SAVE_CLIENT_FORM = "client-form";

	private static final Logger LOGGER = LogManager.getLogger(ClientController.class);
	private static final Logger CONSOLE = LogManager.getLogger("api.grupo.appservicios.ClientController");
	
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
		LOGGER.debug("Processing 'listClients' request");
		model.addAttribute("clients", clientService.listClients());

		return LIST_CLIENTS;
	}

	// Crear cliente nuevo o modificar existente
	@PostMapping("/save")
	public String saveClient(@Valid @ModelAttribute("client") ClientDTO client, BindingResult bindingResult,
			Model model) {
		LOGGER.debug("Processing 'saveClient' request");
		if (bindingResult.hasErrors()) {
			LOGGER.info("Failed to validate client: {}", bindingResult);
			return SAVE_CLIENT_FORM;
		} else {
			try {
				clientService.saveClient(client);
			} catch (DuplicateKeyException | EmptyResultDataAccessException e) {
				CONSOLE.warn("Client could not be saved: {}", e.getMessage());
				LOGGER.warn("An exception occurred while trying to save a client:", e);
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
		LOGGER.debug("Processing 'showClientForm' request with id: {}", id);
		ClientDTO client = clientService.findClient(id);
		if (client == null)
			client = new ClientDTO();
		model.addAttribute("client", client);

		return SAVE_CLIENT_FORM;
	}

	// Eliminación de cliente
	@GetMapping("/remove")
	public String removeClient(@RequestParam(value = "id", required = true) long id, Model model) {
		LOGGER.debug("Processing 'removeClient' request with id: {}", id);
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
