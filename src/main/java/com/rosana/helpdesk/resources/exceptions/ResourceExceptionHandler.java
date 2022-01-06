package com.rosana.helpdesk.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rosana.helpdesk.services.exceptions.ObjectNotFoundException;

/*
 * @ControllerAdvice é um tipo de annotation @Component que permite lidar com exceções 
 * em toda a aplicação em um único componente global.
 * É um interceptor de EXCEÇÕES LANÇADAS POR MÉTODOS @RequestMapping E SIMILARES
 * */

@ControllerAdvice
public class ResourceExceptionHandler {
		
		@ExceptionHandler(ObjectNotFoundException.class)
		public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex,
											HttpServletRequest request){
			
			StandardError error = new StandardError(System.currentTimeMillis(),HttpStatus.NOT_FOUND.value(),
					           "Object Not Found", ex.getMessage(),request.getRequestURI());
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
			
		}

}
