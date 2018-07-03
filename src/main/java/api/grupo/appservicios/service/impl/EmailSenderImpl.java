package api.grupo.appservicios.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import api.grupo.appservicios.service.EmailSenderService;

@Service
public class EmailSenderImpl implements EmailSenderService {

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
	public void sendEmail(String subject, String body, List<File> attachments, String destination) {

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

		} catch (Exception e) {
			// TODO: handle exception
		}

		// TODO Auto-generated method stub
		System.out.println("enviando mail!");
	}

}
