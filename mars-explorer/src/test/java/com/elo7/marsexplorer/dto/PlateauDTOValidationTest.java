package com.elo7.marsexplorer.dto;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.elo7.marsexplorer.domain.CardinalDirection;
import com.elo7.marsexplorer.domain.Plateau;

/**
 * Teste das validações configuradas em {@link Plateau}
 *
 */
public class PlateauDTOValidationTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void idShoudNotBeDiffZero() {
		Plateau plateau = new Plateau(1, 3);
		ReflectionTestUtils.setField(plateau, "id", 1);

		Set<ConstraintViolation<Plateau>> constraintViolations = validator.validate(plateau);

		assertEquals(1, constraintViolations.size());
		assertEquals("must be less than or equal to 0", constraintViolations.iterator().next().getMessage());
	}

	@Test
	public void xShoudBeGreaterThanOrEqualToOne() {
		Plateau plateau = new Plateau(0, 3);

		Set<ConstraintViolation<Plateau>> constraintViolations = validator.validate(plateau);

		assertEquals(1, constraintViolations.size());
		assertEquals("must be greater than or equal to 1", constraintViolations.iterator().next().getMessage());
	}

	@Test
	public void yShoudBeGreaterThanOrEqualToOne() {
		Plateau plateau = new Plateau(1, 0);

		Set<ConstraintViolation<Plateau>> constraintViolations = validator.validate(plateau);

		assertEquals(1, constraintViolations.size());
		assertEquals("must be greater than or equal to 1", constraintViolations.iterator().next().getMessage());
	}

}
