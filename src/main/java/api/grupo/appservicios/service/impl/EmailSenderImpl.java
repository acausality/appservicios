package api.grupo.appservicios.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import api.grupo.appservicios.service.EmailSenderService;

@Service
public class EmailSenderImpl implements EmailSenderService {

	/**
	 * Está referenciado a tres apéndices(uno de tipo RollingFile, otro de tipo
	 * Console, y uno de tipo Smtp): se encarga de loguear a un archivo a partir
	 * del threshold TRACE, mientras que el apéndice Console imprime los mensajes
	 * de threshold INFO y sólo INFO de los subpackages api.grupo.appservicios.
	 * Si se da un log de nivel FATAL, se envía un email según la configuración
	 * del archivo properties. 
	 */
	private static final Logger LOGGER = LogManager.getLogger(EmailSenderImpl.class);
	
	/**
	 * Únicamente imprime los mensajes por consola. Razón: En caso de tener que
	 * loguear una excepción, no es necesario mostrar el stacktrace por consola. Es
	 * decir, hace un "aviso" utilizando el mismo level y en caso de necesitar más
	 * detalles se recurre al log. Para logs de nivel INFO, basta con el otro logger.
	 */
	private static final Logger CONSOLE = LogManager.getLogger("api.grupo.appservicios.emailSenderImpl");

	@Value("${emailsender.account.username}")
	String username;

	@Value("${emailsender.account.password}")
	String password;

	@Value("${emailsender.hostname}")
	String hostName;

	@Value("${emailsender.smtpport}")
	String stmpPort;

	@Value("${emailsender.ssl}")
	String ssl;

	@Value("${emailsender.starttls}")
	String startTLS;

	@Override
	public void sendEmail(String subject, String body, List<File> attachments, String destination)
			throws EmailException {
		LOGGER.info("Attempting to send an email with subject: [" + subject + "] to address: [" + destination + "]");
		try {
			MultiPartEmail email = new MultiPartEmail();

			// Configuración necesaria para Gmail
			email.setHostName(hostName);
			email.setSmtpPort(Integer.valueOf(stmpPort));
			email.setSSLOnConnect(Boolean.valueOf(ssl));
			email.setStartTLSEnabled(Boolean.valueOf(startTLS));

			// Usar cuenta de Gmail y contraseña
			email.setAuthenticator(new DefaultAuthenticator(username, password));

			email.setFrom(username, "App Servicios");

			// Asunto del Email
			email.setSubject(subject);

			// Contenido del Email
			email.setMsg(body);

			for (File file : attachments) {
				// Agregar adjunto
				email.attach(file);
			}

			// Cuenta de Email Destino
			email.addTo(destination);

			// Enviar email
			email.send();
			LOGGER.info("Email sent.");
		} catch (EmailException ee) {
			CONSOLE.error("There was a problem sending the e-mail. Check the log file for details.");
			LOGGER.error("There was a problem sending the e-mail: " + ee);
			throw ee;
		} catch (Exception e) {
			CONSOLE.error("There was a problem sending the e-mail. Check the log file for details.");
			LOGGER.error("There was a problem sending the e-mail: " + e);
		}
	}

}
