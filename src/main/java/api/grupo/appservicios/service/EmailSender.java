package api.grupo.appservicios.service;

public interface EmailSender {
	//Empezar a construir un email
	void beginBuilding();
	//Setear asunto
	void setSubject(String subject);
	//Setear email de destino
	void setDestination(String email);
	//Agregar un archivo adjunto
	void addAttachment(Object object);
	//Enviar el e-mail
	void send();
}
