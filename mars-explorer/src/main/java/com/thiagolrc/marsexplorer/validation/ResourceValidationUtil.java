package com.thiagolrc.marsexplorer.validation;

import org.springframework.stereotype.Component;

import com.thiagolrc.marsexplorer.exception.ResourceNotFoundException;

/**
 * Utilitário para validação de recursos
 *
 */
@Component
public class ResourceValidationUtil {

	/**
	 * Verifica a existência de um recurso
	 * 
	 * @param resource
	 * @throws ResourceNotFoundException
	 *             caso o recurso seja <i>null</i>
	 */
	public void ensureExistence(Object resource) throws ResourceNotFoundException {
		if (resource == null) {
			throw new ResourceNotFoundException();
		}
	}

}
