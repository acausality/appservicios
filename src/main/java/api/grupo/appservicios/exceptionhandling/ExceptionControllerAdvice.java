/*package api.grupo.appservicios.exceptionhandling;

import org.hibernate.exception.ConstraintViolationException;
// import org.springframework.dao.DataIntegrityViolationException; También se puede capturar
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity handleConstraintViolationException() {
        // loguear la excepción
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Error Message");
	}

}
*/