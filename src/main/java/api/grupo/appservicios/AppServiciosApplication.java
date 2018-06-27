package api.grupo.appservicios;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import api.grupo.appservicios.service.scheduled.cron.DailyReportGenerationCronJob;
import api.grupo.appservicios.service.scheduled.cron.EmailSenderCronJob;

@SpringBootApplication
public class AppServiciosApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppServiciosApplication.class, args);
	}

	@Bean
	public JobDetail dailyReportJobDetail() {
		return JobBuilder.newJob(DailyReportGenerationCronJob.class).withIdentity("dailyReportJob").storeDurably().build();
	}
	
	@Bean
	public JobDetail hourlyEmailJobDetail() {
		return JobBuilder.newJob(EmailSenderCronJob.class).withIdentity("hourlyEmailJob").storeDurably().build();
	}

	// Disparador de tarea de generacion de reportes diarios. La fecha y hora se
	// configuran en el archivo application.properties
	@Bean
	public Trigger dailyReportTrigger(@Value("${dailyreport.hour}") int hour,
			@Value("${dailyreport.minutes}") int minutes) {
		// Para experimentar. Ejecuta la tarea cada 5 segundos
		// CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.dailyAtHourAndMinute(hour, minutes);
		return TriggerBuilder.newTrigger().forJob(dailyReportJobDetail()).withSchedule(scheduleBuilder).build();
	}
	
	@Bean
	public Trigger hourlyEmailTrigger() {
		// Ejecutar una vez por hora
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 * * * ?");
		return TriggerBuilder.newTrigger().forJob(hourlyEmailJobDetail()).withSchedule(scheduleBuilder).build();
	}
}
