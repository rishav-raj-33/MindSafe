package mindSafe.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.mail.SendFailedException;
import mindSafe.helpers.ApiResponse;



@ControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(ResourceNotFound.class)
public ResponseEntity<ApiResponse> resouceNotFoundExceptionHandler(ResourceNotFound exception){
		String message=exception.getMessage();
		ApiResponse response=new ApiResponse(message,false);
		
		return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> NotValidException(MethodArgumentNotValidException e){
		Map<String, String> errorResponse=new HashMap<>();
		e.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName=((FieldError)error).getField();
			String errorMessge=error.getDefaultMessage();
			
			errorResponse.put(fieldName, errorMessge);
			
		});	
				return new ResponseEntity<Map<String,String>>(errorResponse,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(java.sql.SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ApiResponse> duplicateError(java.sql.SQLIntegrityConstraintViolationException e){
		String message=e.getMessage();
		ApiResponse response=new ApiResponse(message,false);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(UserNotFoundException.class)
public ResponseEntity<ApiResponse> UserNotFoundExceptionHandler(UserNotFoundException exception){
		String message=exception.getMessage();
		ApiResponse response=new ApiResponse(message,false);
		
		return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
}
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ApiResponse> confirmationTokenHandler(IllegalStateException e){
		String message=e.getMessage();
		ApiResponse response=new ApiResponse(message,false);
		
		return new ResponseEntity<>(response,HttpStatus.NOT_ACCEPTABLE);
	
		
	}
	
	@ExceptionHandler(SendFailedException.class)
	public ResponseEntity<ApiResponse> sendMailExceptionHandler(SendFailedException exception){
		String message=exception.getMessage();
		ApiResponse response=new ApiResponse(message,false);
		
		return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
}
}
