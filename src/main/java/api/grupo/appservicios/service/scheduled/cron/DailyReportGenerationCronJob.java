package api.grupo.appservicios.service.scheduled.cron;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;

import api.grupo.appservicios.model.dto.ClientDTO;
import api.grupo.appservicios.model.dto.ProfessionalDTO;
import api.grupo.appservicios.service.ClientService;
import api.grupo.appservicios.service.ProfessionalService;

public class DailyReportGenerationCronJob extends QuartzJobBean {
	@Autowired
	ClientService clientService;

	@Autowired
	ProfessionalService professionalService;
	
	@Value("${dailyreport.path.pending}")
	String reportFolderPath;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// Obtener listado de clientes registrados en el dia
		List<ClientDTO> clients = clientService.listClientsSignedUpToday();
		int clientCount = clients.size();
		// Obtener listado de profesionales registrados en el dia
		List<ProfessionalDTO> professionals = professionalService.listProfessionalsSignedUpToday();
		int professionalCount = professionals.size();
		// TODO: Crear archivo XLS con los reportes, guardarlo en la carpeta reports/pending
		try {
			File reportFolder = new File(reportFolderPath);
			if (reportFolder.exists()) {
				//TODO xls con Apache Poi
				String fileName = reportFolderPath + "/DailySignupReport-" + LocalDate.now().toString() + ".txt";
				if (!new File(fileName).exists())
				{
					PrintWriter writer = new PrintWriter(fileName);
					writer.println("Reporte diario de altas de usuarios");
					writer.println("~~~~~~~");
					writer.println("~~~~~~~");
					writer.println("Se dieron de alta " + clientCount + " clientes: ");
					for (ClientDTO client: clients) {
						writer.println("-" + client.getName() + " " + client.getSurname() + " " + client.getEmail());
					}
					writer.println("~~~~~~~");
					writer.println("Se dieron de alta " + professionalCount + " profesionales: ");
					for (ProfessionalDTO professional: professionals) {
						writer.println("-" + professional.getName() + " " + professional.getSurname() + " " + professional.getEmail());
					}
					writer.close();
					System.out.println("Debug: Reporte diario generado.");
				}
			}
		} catch (Exception e) {
			// TODO: Loggear la excepcion
			e.printStackTrace();
		}
	}

}
