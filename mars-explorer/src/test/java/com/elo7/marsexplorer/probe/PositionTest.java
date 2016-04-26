package com.elo7.marsexplorer.probe;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.elo7.marsexplorer.probe.CardinalDirection;
import com.elo7.marsexplorer.probe.Position;
import com.elo7.marsexplorer.probe.PositionMover;

/** Testes para {@link Position} */
public class PositionTest {

	/* Garantindo que o move vai criar um mover e delegar */
	@Test
	public void moveShouldCreatePositionMoverAndMoveTowardsDirection() {
		Position partialMock = Mockito.mock(Position.class);
		Mockito.when(partialMock.move()).thenCallRealMethod();
		PositionMover mover = Mockito.mock(PositionMover.class);
		Position newPosition = Mockito.mock(Position.class);
		Mockito.when(mover.moveTowardsDirection()).thenReturn(newPosition);
		Mockito.when(partialMock.positionMover()).thenReturn(mover);

		Assert.assertEquals(newPosition, partialMock.move());
	}

	@Test
	public void rotateLeftShouldCreateNewPositionWithCardinalDirectionLeftRotated() {
		CardinalDirection direction = Mockito.mock(CardinalDirection.class);
		Position p = new Position(2, 3, direction);

		CardinalDirection newDirection = Mockito.mock(CardinalDirection.class);
		Mockito.when(direction.rotateLeft()).thenReturn(newDirection);

		Position newPostion = p.rotateLeft();

		Assert.assertNotEquals(p, newPostion);
		Assert.assertEquals(2, newPostion.getX());
		Assert.assertEquals(3, newPostion.getY());
		Assert.assertEquals(newDirection, newPostion.getDirection());
	}

	@Test
	public void rotateRightShouldCreateNewPositionWithCardinalDirectionRightRotated() {
		CardinalDirection direction = Mockito.mock(CardinalDirection.class);
		Position p = new Position(2, 3, direction);

		CardinalDirection newDirection = Mockito.mock(CardinalDirection.class);
		Mockito.when(direction.rotateRight()).thenReturn(newDirection);

		Position newPostion = p.rotateRight();

		Assert.assertNotEquals(p, newPostion);
		Assert.assertEquals(2, newPostion.getX());
		Assert.assertEquals(3, newPostion.getY());
		Assert.assertEquals(newDirection, newPostion.getDirection());
	}

	@Test
	public void toStringShouldReturnXYAndCoordinateValuesFormatted() {
		Position p = new Position(1, 3, CardinalDirection.N);
		Assert.assertEquals("1 3 N", p.toString());
	}
}
