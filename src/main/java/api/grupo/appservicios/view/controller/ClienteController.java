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

import api.grupo.appservicios.model.dto.ClienteDTO;
import api.grupo.appservicios.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	// constantes para los nombres de los templates
	private static final String LISTA_CLIENTES = "listado-clientes";
	private static final String FORMULARIO_GUARDAR_CLIENTE = "formulario-cliente";

	private Log logger = LogFactory.getLog(ClienteController.class);

	@Autowired
	private ClienteService clienteService;

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
	@GetMapping("/listar")
	public String listarClientes(Model modelo) {
		logger.info("Dentro de listarClientes");
		modelo.addAttribute("clientes", clienteService.listarClientes());

		return LISTA_CLIENTES;
	}

	@PostMapping("/guardar")
	public String guardarCliente(@Valid @ModelAttribute("cliente") ClienteDTO cliente, BindingResult bindingResult) {
		logger.info("Dentro de guardarClientes. Recibido: " + cliente);
		if (!bindingResult.hasErrors()) {
			clienteService.guardarCliente(cliente);

			return "redirect:/clientes/listar";
		} else {
			logger.error("Error al validar cliente: " + cliente);
			logger.error(bindingResult.toString());

			return FORMULARIO_GUARDAR_CLIENTE;
		}
	}
	
	@GetMapping("/formulario")
	public String mostrarFormularioCliente(@RequestParam(value="id", defaultValue="0", required=false) long id, Model modelo) {
		ClienteDTO cliente;
		if (id == 0)
			cliente = new ClienteDTO();
		else {
			cliente = clienteService.buscarCliente(id);
		}	
		modelo.addAttribute("cliente", cliente);
		
		return FORMULARIO_GUARDAR_CLIENTE;
	}

}
