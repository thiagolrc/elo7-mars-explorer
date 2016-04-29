package com.thiagolrc.marsexplorer.validation;

import org.junit.Test;

import com.thiagolrc.marsexplorer.exception.ResourceNotFoundException;
import com.thiagolrc.marsexplorer.validation.ResourceValidationUtil;

public class ResourceValidationUtilTest {

	private ResourceValidationUtil validationUtil = new ResourceValidationUtil();

	@Test(expected = ResourceNotFoundException.class)
	public void ensureExistenceShouldFailIfObjectIsNull() {
		validationUtil.ensureExistence(null);
	}

	@Test
	public void ensureExistenceShouldNotFailIfObjectIsNotNull() {
		validationUtil.ensureExistence("");
	}

}
