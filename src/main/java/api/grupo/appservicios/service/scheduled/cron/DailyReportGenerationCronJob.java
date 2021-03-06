package api.grupo.appservicios.service.scheduled.cron;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;

import api.grupo.appservicios.model.excel.ExcelBuilder;
import api.grupo.appservicios.service.ClientService;
import api.grupo.appservicios.service.ProfessionalService;

public class DailyReportGenerationCronJob extends QuartzJobBean {
	
	/**
	 * Está referenciado a tres apéndices(uno de tipo RollingFile, otro de tipo
	 * Console, y uno de tipo Smtp): se encarga de loguear a un archivo a partir
	 * del threshold TRACE, mientras que el apéndice Console imprime los mensajes
	 * de threshold INFO y sólo INFO de los subpackages api.grupo.appservicios.
	 * Si se da un log de nivel FATAL, se envía un email según la configuración
	 * del archivo properties. 
	 */
	private static final Logger LOGGER = LogManager.getLogger(DailyReportGenerationCronJob.class);
	
	/**
	 * Únicamente imprime los mensajes por consola. Razón: En caso de tener que
	 * loguear una excepción, no es necesario mostrar el stacktrace por consola. Es
	 * decir, hace un "aviso" utilizando el mismo level y en caso de necesitar más
	 * detalles se recurre al log. Para logs de nivel INFO, basta con el otro logger.
	 */
	private static final Logger CONSOLE = LogManager.getLogger("api.grupo.appservicios.DailyReportGenerationCronJob");

	@Autowired
	ClientService clientService;

	@Autowired
	ProfessionalService professionalService;

	@Value("${dailyreport.path.pending}")
	String reportFolderPath;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		CONSOLE.error("test");
		LOGGER.debug("Generating daily report...");
		try {
			File file = new File(reportFolderPath + "/DailySignupReport-" + LocalDate.now().toString() + ".xlsx");

			if (!file.exists()) {
				if (!new File(reportFolderPath).exists())
					file.getParentFile().mkdirs();
				file.createNewFile();
			}

			if (!file.isDirectory()) {
				ExcelBuilder.build(clientService.listClientsSignedUpToday(),
						professionalService.listProfessionalsSignedUpToday(), file);
				LOGGER.info("Daily report generated");
			}
		} catch (IOException e) {
			LOGGER.error("An error occurred while trying to create a report file:" + e);
			CONSOLE.error(
					"An error occurred while trying to create a report file. Please check the log file for detailed information.");
		}
	}

}
