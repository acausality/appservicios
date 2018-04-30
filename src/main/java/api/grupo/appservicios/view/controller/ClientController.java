package api.grupo.appservicios.view.controller;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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

	/*
	 * esto se va a usar para parte de la validacion. Cuando se recibe un string,
	 * quita los espacios extra al final y al principio, y si queda un string vacio
	 * lo hace null
	 */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		// true en el constructor para que haga que los strings vacios se seteen a null
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	// muestra un listado con todos los clientes
	@GetMapping("/list")
	public String listClients(Model model) {
		logger.info("Inside listClients");
		model.addAttribute("clients", clientService.listClients());

		return LIST_CLIENTS;
	}

	@PostMapping("/save")
	public String saveClient(@Valid @ModelAttribute("client") ClientDTO client, BindingResult bindingResult) {
		logger.info("Inside saveClient. Received: " + client);
		if (!bindingResult.hasErrors()) {
			clientService.saveClient(client);

			return "redirect:/clients/list";
		} else {
			logger.error("Failed to validate client: " + client);
			logger.error(bindingResult.toString());

			return SAVE_CLIENT_FORM;
		}
	}

	@GetMapping("/form")
	public String showClientForm(@RequestParam(value = "id", defaultValue = "0", required = false) long id,
			Model model) {
		ClientDTO client = clientService.findClient(id);
		if (client == null)
			client = new ClientDTO();
		model.addAttribute("client", client);

		return SAVE_CLIENT_FORM;
	}

	@GetMapping("/remove")
	public String removeClient(@RequestParam(value = "id", required = true) long id) {
		if (id > 0)
			clientService.removeClient(id);

		return "redirect:/clients/list";
	}

}
