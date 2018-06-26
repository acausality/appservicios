package api.grupo.appservicios.service;

public interface EmailSender {
	//Setear asunto
	void setSubject(String subject);
	//Setear email de destino
	void setDestination(String email);
	//Agregar un archivo adjunto
	void addAttachment(Object object);
	//Enviar el e-mail
	void send();
}
