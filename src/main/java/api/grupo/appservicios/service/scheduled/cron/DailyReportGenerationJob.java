package api.grupo.appservicios.service.scheduled.cron;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import api.grupo.appservicios.model.dto.ClientDTO;
import api.grupo.appservicios.model.dto.ProfessionalDTO;
import api.grupo.appservicios.service.ClientService;
import api.grupo.appservicios.service.ProfessionalService;

public class DailyReportGenerationJob extends QuartzJobBean {
	@Autowired
	ClientService clientService;

	@Autowired
	ProfessionalService professionalService;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		// Obtener listado de clientes registrados en el dia
		List<ClientDTO> clients = clientService.listClientsSignedUpToday();

		// Crear xls en la carpeta de pendientes de envio
		// TODO
		// Obtener listado de profesionales registrados en el dia
		List<ProfessionalDTO> professionals = professionalService.listProfessionalsSignedUpToday();
		// Crear xls en la carpeta de pendientes de envio
		// TODO
	}

}
