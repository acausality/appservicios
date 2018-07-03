package api.grupo.appservicios.service;

import java.io.File;
import java.util.List;

import org.apache.commons.mail.EmailException;

public interface EmailSenderService {

	void sendEmail(String subject, String body, List<File> attachments, String destination) throws EmailException;
	
}
