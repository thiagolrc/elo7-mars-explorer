package com.elo7.marsexplorer;

import static com.elo7.marsexplorer.CardinalDirection.N;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

/** Testes para {@link Plateau} */
public class PlateauTest {
	
	private Plateau plateau = new Plateau(3,4);

	@Test
	public void isPositionValidShouldBeFalseIfXIsLessThanZero() {
		Assert.assertFalse(plateau.isPositionValid(new Position(-1, 1, N)));
	}
	@Test
	public void isPositionValidShouldBeFalseIfYIsLessThanZero() {
		Assert.assertFalse(plateau.isPositionValid(new Position(1, -1, N)));
	}
	@Test
	public void isPositionValidShouldBeTrueIfXIsZero() {
		Assert.assertTrue(plateau.isPositionValid(new Position(0, 1, N)));
	}
	@Test
	public void isPositionValidShouldBeTrueIfYIsZero() {
		Assert.assertTrue(plateau.isPositionValid(new Position(1, 0, N)));
	}
	@Test
	public void isPositionValidShouldBeFalseIfXIsGreaterThanPlateauX() {
		Assert.assertFalse(plateau.isPositionValid(new Position(4, 1, N)));
	}
	@Test
	public void isPositionValidShouldBeFalseIfYIsGreaterThanPlateauY() {
		Assert.assertFalse(plateau.isPositionValid(new Position(1, 5, N)));
	}
	@Test
	public void isPositionValidShouldBeTrueIfXIsEqualsToPlateauX() {
		Assert.assertTrue(plateau.isPositionValid(new Position(3, 1, N)));
	}
	@Test
	public void isPositionValidShouldBeTrueIfYIsEqualsToPlateauY() {
		Assert.assertTrue(plateau.isPositionValid(new Position(1, 4, N)));
	}
}
