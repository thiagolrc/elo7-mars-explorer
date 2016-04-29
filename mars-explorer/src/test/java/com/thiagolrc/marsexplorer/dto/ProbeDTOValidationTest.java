package com.thiagolrc.marsexplorer.dto;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import com.thiagolrc.marsexplorer.domain.CardinalDirection;
import com.thiagolrc.marsexplorer.dto.ProbeDTO;

/**
 * Teste das validações configuradas em {@link ProbeDTO}
 *
 */
public class ProbeDTOValidationTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void idShoudNotBeDiffZero() {
		ProbeDTO probe = validProbeDto();
		probe.setId(1);

		Set<ConstraintViolation<ProbeDTO>> constraintViolations = validator.validate(probe);

		assertEquals(1, constraintViolations.size());
		assertEquals("must be less than or equal to 0", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void xShoudBeZeroOrMore() {
		ProbeDTO probe = validProbeDto();
		probe.setX(-1);

		Set<ConstraintViolation<ProbeDTO>> constraintViolations = validator.validate(probe);

		assertEquals(1, constraintViolations.size());
		assertEquals("must be greater than or equal to 0", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void yShoudBeZeroOrMore() {
		ProbeDTO probe = validProbeDto();
		probe.setY(-1);

		Set<ConstraintViolation<ProbeDTO>> constraintViolations = validator.validate(probe);

		assertEquals(1, constraintViolations.size());
		assertEquals("must be greater than or equal to 0", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void directionShoudNotBeNull() {
		ProbeDTO probe = validProbeDto();
		probe.setDirection(null);

		Set<ConstraintViolation<ProbeDTO>> constraintViolations = validator.validate(probe);

		assertEquals(1, constraintViolations.size());
		assertEquals("may not be null", constraintViolations.iterator().next().getMessage());
	}
	
	private ProbeDTO validProbeDto(){
		ProbeDTO probe = new ProbeDTO();
		probe.setX(1);
		probe.setY(2);
		probe.setDirection(CardinalDirection.N);
		return probe;
	}

}
