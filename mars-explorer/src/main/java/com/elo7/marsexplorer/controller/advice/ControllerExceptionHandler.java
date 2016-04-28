package com.elo7.marsexplorer.controller.advice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.elo7.marsexplorer.exception.BadRequestException;
import com.elo7.marsexplorer.exception.ResourceNotFoundException;

/**
 * Aspecto que intercepta e trata exceções geradas nos controllers de forma genérica e padronizada
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler {

	private final Log logger = LogFactory.getLog(this.getClass());

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public void handleResourceNotFoundException(ResourceNotFoundException e) {
		logger.info("Tentativa de acesso a um recurso inexistente", e);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	@ResponseBody
	public String handleBadRequestException(BadRequestException e) {
		logger.info("Tentativa de acesso a um recurso inexistente", e);
		return e.getMessage();
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public void handleException(Exception e) {
		logger.error("Erro inesperado tratado pelo advice", e);
	}

}
