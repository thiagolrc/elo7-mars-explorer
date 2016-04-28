package com.elo7.marsexplorer.validation;

import org.junit.Test;

import com.elo7.marsexplorer.exception.InvalidResourceException;

public class ResourceValidationUtilTest {

	private ResourceValidationUtil validationUtil = new ResourceValidationUtil();

	@Test(expected = InvalidResourceException.class)
	public void ensureExistenceShouldFailIfObjectIsNull() {
		validationUtil.ensureExistence(null);
	}

	@Test
	public void ensureExistenceShouldNotFailIfObjectIsNotNull() {
		validationUtil.ensureExistence("");
	}

}
