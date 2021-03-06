package api.grupo.appservicios.service.scheduled.cron;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;

import api.grupo.appservicios.service.EmailSenderService;

public class EmailSenderCronJob extends QuartzJobBean {
	
	/**
	 * Está referenciado a tres apéndices(uno de tipo RollingFile, otro de tipo
	 * Console, y uno de tipo Smtp): se encarga de loguear a un archivo a partir
	 * del threshold TRACE, mientras que el apéndice Console imprime los mensajes
	 * de threshold INFO y sólo INFO de los subpackages api.grupo.appservicios.
	 * Si se da un log de nivel FATAL, se envía un email según la configuración
	 * del archivo properties. 
	 */
	private static final Logger LOGGER = LogManager.getLogger(EmailSenderCronJob.class);
	
	/**
	 * Únicamente imprime los mensajes por consola. Razón: En caso de tener que
	 * loguear una excepción, no es necesario mostrar el stacktrace por consola. Es
	 * decir, hace un "aviso" utilizando el mismo level y en caso de necesitar más
	 * detalles se recurre al log. Para logs de nivel INFO, basta con el otro logger.
	 */
	private static final Logger CONSOLE = LogManager.getLogger("api.grupo.appservicios.EmailSenderCronJob");

	@Autowired
	EmailSenderService emailSenderService;

	@Value("${dailyreport.path.pending}")
	String pendingReportFolderPath;

	@Value("${dailyreport.path.sent}")
	String sentReportFolderPath;

	@Value("${dailyreport.destination}")
	String destination;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info("Executing task: e-mail pending reports.");
		sendPendingReports();
	}

	// Detecta si hay reportes pendientes de enviar, los envia, y los mueve a la
	// carpeta de enviados
	private void sendPendingReports() {
		File pendingFolder = new File(pendingReportFolderPath);
		try {
			//
			List<File> files = Arrays.asList(pendingFolder.listFiles());
			if (files.size() == 0) {
				LOGGER.info("0 pending reports found.");
				return;
			}
			LOGGER.info(files.size() + " pending reports found. Attempting to send e-mail...");
			for (File file : files) {
				emailSenderService.sendEmail("Report: " + file.getName(), "Report attached", Arrays.asList(file),
						destination);

				// Mover el archivo a carpeta enviados
				Path targetPath = Paths.get(sentReportFolderPath + File.separator + file.getName());
				if (!targetPath.toFile().exists()) {
					targetPath.toFile().mkdirs();
				}
				LOGGER.info("Report sent successfully: " + file.getName() + ". Moving file...");
				Files.move(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
				LOGGER.info("File moved successfully.");
			}
			LOGGER.info("Finished sending reports.");
		} catch (EmailException ee) {
			LOGGER.error("An error occurred while trying to send the daily report email:" + ee);
			CONSOLE.error(
					"An error occurred while trying to send the daily report email. Please check the log file for detailed information.");
		} catch (Exception e) {
			LOGGER.error("A general error occurred while trying to send the daily report email:" + e);
			CONSOLE.error(
					"A general error occurred while trying to send the daily report email. Please check the log file for detailed information.");
		}
	}

}
