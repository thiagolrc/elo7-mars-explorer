package com.elo7.marsexplorer.validation;

import org.junit.Test;

import com.elo7.marsexplorer.exception.ResourceNotFoundException;

public class ValidationUtilTest {

	private ValidationUtil validationUtil = new ValidationUtil();

	@Test(expected = ResourceNotFoundException.class)
	public void ensureExistenceShouldFailIfObjectIsNull() {
		validationUtil.ensureExistence(null);
	}

	@Test
	public void ensureExistenceShouldNotFailIfObjectIsNotNull() {
		validationUtil.ensureExistence("");
	}

}
