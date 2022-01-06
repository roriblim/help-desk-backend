package com.rosana.helpdesk.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
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
		
	//para lidar com a exceção de objeto não encontrado (quando procuramos um id específico, por exemplo):
		@ExceptionHandler(ObjectNotFoundException.class)
		public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex,
											HttpServletRequest request){
			
			StandardError error = new StandardError(System.currentTimeMillis(),HttpStatus.NOT_FOUND.value(),
					           "Object Not Found", ex.getMessage(),request.getRequestURI());
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
			
		}
		
		//para lidar com a exceção de violação da integridade dos dados (quando tentamos adicionar um dado de um cpf ou email que
		//já existe, por exemplo):
		@ExceptionHandler(DataIntegrityViolationException.class)
		public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex,
											HttpServletRequest request){
			
			StandardError error = new StandardError(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(),
					           "Violação de integridade de dados", ex.getMessage(),request.getRequestURI());
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
			
		}

}
