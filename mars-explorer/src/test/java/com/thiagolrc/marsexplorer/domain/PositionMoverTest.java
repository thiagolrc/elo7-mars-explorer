package com.thiagolrc.marsexplorer.domain;

import org.junit.Assert;
import org.junit.Test;

import com.thiagolrc.marsexplorer.domain.CardinalDirection;
import com.thiagolrc.marsexplorer.domain.Position;
import com.thiagolrc.marsexplorer.domain.PositionMover;

/** Testes para {@link PositionMover} */
public class PositionMoverTest {

	/*
	 * Para garantir que toda direção cardinal terá uma implementação de PositionMover
	 */
	@Test
	public void ofShouldBeImplementedForAllCardinalDirections() {
		for (CardinalDirection d : CardinalDirection.values()) {
			Assert.assertNotNull(PositionMover.of(new Position(1, 2, d)));
		}
	}

	@Test
	public void ofPositionPointingNorthShouldCreateNewPositionIncreasingYCoordinate() {
		Position p = new Position(3, 3, CardinalDirection.N);

		Position newPostion = p.move();

		Assert.assertNotEquals(p, newPostion);
		Assert.assertEquals(3, newPostion.getX());
		Assert.assertEquals(4, newPostion.getY());
		Assert.assertEquals(CardinalDirection.N, newPostion.getDirection());
	}

	@Test
	public void ofPositionPointingSouthShouldCreateNewPositionDecreasingYCoordinate() {
		Position p = new Position(3, 3, CardinalDirection.S);

		Position newPostion = p.move();

		Assert.assertNotEquals(p, newPostion);
		Assert.assertEquals(3, newPostion.getX());
		Assert.assertEquals(2, newPostion.getY());
		Assert.assertEquals(CardinalDirection.S, newPostion.getDirection());
	}

	@Test
	public void ofPositionPointingEastShouldCreateNewPositionIncreasingXCoordinate() {
		Position p = new Position(3, 3, CardinalDirection.E);

		Position newPostion = p.move();

		Assert.assertNotEquals(p, newPostion);
		Assert.assertEquals(4, newPostion.getX());
		Assert.assertEquals(3, newPostion.getY());
		Assert.assertEquals(CardinalDirection.E, newPostion.getDirection());
	}

	@Test
	public void ofPositionPointingWestShouldCreateNewPositionDecreasingXCoordinate() {
		Position p = new Position(3, 3, CardinalDirection.W);

		Position newPostion = p.move();

		Assert.assertNotEquals(p, newPostion);
		Assert.assertEquals(2, newPostion.getX());
		Assert.assertEquals(3, newPostion.getY());
		Assert.assertEquals(CardinalDirection.W, newPostion.getDirection());
	}

}
