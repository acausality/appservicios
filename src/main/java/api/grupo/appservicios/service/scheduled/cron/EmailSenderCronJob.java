package api.grupo.appservicios.service.scheduled.cron;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;

import api.grupo.appservicios.service.EmailSenderService;
import api.grupo.appservicios.view.controller.ClientController;

public class EmailSenderCronJob extends QuartzJobBean {
	private static final Logger LOGGER = LogManager.getLogger(ClientController.class);
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
		CONSOLE.info("Executing task: e-mail pending reports.");
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
				CONSOLE.info("0 pending reports found.");
				return;
			}
			LOGGER.info(files.size() + " pending reports found. Attempting to send e-mail...");
			CONSOLE.info(files.size() + " pending reports found. Attempting to send e-mail...");
			for (File file : files) {
				emailSenderService.sendEmail("Report: " + file.getName(), "Report attached", Arrays.asList(file),
						destination);

				// Mover el archivo a carpeta enviados
				Path targetPath = Paths.get(sentReportFolderPath + File.separator + file.getName());
				if (!targetPath.toFile().exists()) {
					targetPath.toFile().mkdirs();
				}
				LOGGER.info("Report sent successfully: " + file.getName() + ". Moving file...");
				CONSOLE.info("Report sent successfully: " + file.getName() + ". Moving file...");
				Files.move(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
				LOGGER.info("File moved successfully.");
				CONSOLE.info("File moved successfully.");
			}
		} catch (Exception e) {
			LOGGER.error("An error occurred while trying to send the daily report email:" + e);
			CONSOLE.error(
					"An error occurred while trying to send the daily report email. Please check the log file for detailed information.");
		}
	}

}
