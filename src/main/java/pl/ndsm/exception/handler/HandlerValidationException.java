package pl.ndsm.exception.handler;



import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import pl.ndsm.exception.ValidationException;

@RestControllerAdvice
public class HandlerValidationException extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler({ValidationException.class})
	@ResponseBody
	public ResponseEntity<List<String>> handleValidationException(ValidationException ex) {
		ResponseEntity<List<String>> response = new ResponseEntity<List<String>>(ex.getMessages(), HttpStatus.UNPROCESSABLE_ENTITY);
		return response;
	}
	
}
