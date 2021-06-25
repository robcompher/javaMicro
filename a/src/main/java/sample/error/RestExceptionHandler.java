package sample.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(APIErrorException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ResponseEntity<APIError> handleAPIErrorException(APIErrorException ex) {
		APIError apiError = new APIError(HttpStatus.UNPROCESSABLE_ENTITY, "Issue Validating", ex);
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
