package com.elo7.marsexplorer.exception;

/**
 * Indica que uma determinada requisição é inválido, seja por estar incompleta ou por gerar um estado não permitido na aplicação
 * 
 */
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message) {
		super(message);
	}

}
