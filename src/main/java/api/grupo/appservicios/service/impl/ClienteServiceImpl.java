package api.grupo.appservicios.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.grupo.appservicios.data.dao.ClienteDAO;
import api.grupo.appservicios.model.Cliente;
import api.grupo.appservicios.model.converter.ClienteConverter;
import api.grupo.appservicios.model.dto.ClienteDTO;
import api.grupo.appservicios.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	ClienteDAO clienteDAO;
	
	@Override
	public List<ClienteDTO> listarClientes() {
		List<Cliente> clientes = clienteDAO.findAll();
		List<ClienteDTO> resultado = new ArrayList<ClienteDTO>();
		for (Cliente cliente: clientes)
			resultado.add(ClienteConverter.modelToDTO(cliente));
		
		return resultado;
	}

	@Override
	public void guardarCliente(@Valid ClienteDTO cliente) {
		clienteDAO.save(ClienteConverter.DTOToModel(cliente));		
	}

	@Override
	public ClienteDTO buscarCliente(long id) {
		Cliente cliente = clienteDAO.findById(id);
		if (cliente == null)
			return null;
		
		return ClienteConverter.modelToDTO(cliente);
	}

	@Override
	public void baja(long id) {
		clienteDAO.deleteById(id);		
	}

}
