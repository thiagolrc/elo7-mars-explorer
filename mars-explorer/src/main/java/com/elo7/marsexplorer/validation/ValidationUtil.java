package com.elo7.marsexplorer.validation;

import org.springframework.stereotype.Component;

import com.elo7.marsexplorer.exception.InvalidResourceException;

@Component
public class ValidationUtil {

	public void ensureExistence(Object resource) throws InvalidResourceException {
		if (resource == null){
			throw new InvalidResourceException();
		}
	}
	
	public void ensureTrue(boolean condition) throws InvalidResourceException {
		if (!condition){
			throw new InvalidResourceException();
		}
	}
}
