package api.grupo.appservicios.service.impl;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.stereotype.Service;

import api.grupo.appservicios.service.EmailSenderService;

@Service
public class EmailSenderImpl implements EmailSenderService {

	public static void main(String[] args) throws Exception {

		// Usar cuenta de Gmail y contraseña
		String userName = "";
		String password = "";

		// Cuenta de Email Origen, la misma con la que nos autenticamos
		String fromAddress = "";
		// Cuenta de Email Destino
		String toAdress = "";
		// Asunto del Email
		String subject = "Reporte de Clientes";
		// Contenido del Email
		String message = "Hola";

		// Crear adjunto
		EmailAttachment attachment = new EmailAttachment();
		//attachment.setPath("");
		attachment.setURL(new URL("https://i.pinimg.com/736x/4e/84/d0/4e84d07e109efea6114aa8003a9a118f.jpg"));
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("Foto de Apu");
		attachment.setName("Apu");

		try {
			MultiPartEmail email = new MultiPartEmail();
			// Configuración necesaria para Gmail
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465);
			email.setSSLOnConnect(true);
			email.setStartTLSEnabled(true);
			// Usar cuenta de Gmail y contraseña
			email.setAuthenticator(new DefaultAuthenticator(userName, password));
			// Cuenta de Email Origen, la misma con la que nos autenticamos
			email.setFrom(fromAddress, "App Servicios");
			// Asunto del Email
			email.setSubject(subject);
			// Contenido del Email
			email.setMsg(message);
			// Cuenta de Email Destino
			email.addTo(toAdress);

			// Agregar adjunto
			email.attach(attachment);
			email.send();
		} catch (Exception e) {
			System.out.println("No se pudo enviar el email.");
			System.out.println(e);
		}

	}

	@Override
	public void sendEmail(String subject, String body, List<File> attachments, String destination) {
		// TODO Auto-generated method stub
		System.out.println("enviando mail!");
	}
	
	

}
