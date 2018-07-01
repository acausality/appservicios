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
import api.grupo.appservicios.view.controller.ClientController;

public class DailyReportGenerationCronJob extends QuartzJobBean {
	private static final Logger LOGGER = LogManager.getLogger(ClientController.class);
	private static final Logger CONSOLE = LogManager.getLogger("api.grupo.appservicios.DailyReportGenerationCronJob");

	@Autowired
	ClientService clientService;

	@Autowired
	ProfessionalService professionalService;

	@Value("${dailyreport.path.pending}")
	String reportFolderPath;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
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
					"An error occurred while trying to create a report file. Please check the log for detailed information.");
		}
	}

}
