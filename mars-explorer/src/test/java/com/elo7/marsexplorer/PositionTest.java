package com.elo7.marsexplorer;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class PositionTest {

	@Test
	public void moveForwardWhenPointingToNorthShouldCreateNewPositionWithIncreasedYCoordinate() {
		Position p = new Position(3, 3, CardinalDirection.N);
		
		Position newPostion = p.moveForward();
		
		Assert.assertNotEquals(p, newPostion);
		Assert.assertEquals(3,newPostion.getX());
		Assert.assertEquals(4,newPostion.getY());
		Assert.assertEquals(CardinalDirection.N,newPostion.getDirection());
	}
	@Test
	public void moveForwardWhenPointingToSouthShouldCreateNewPositionWithDecreasedYCoordinate() {
		Position p = new Position(3, 3, CardinalDirection.S);

		Position newPostion = p.moveForward();
		
		Assert.assertNotEquals(p, newPostion);
		Assert.assertEquals(3,newPostion.getX());
		Assert.assertEquals(2,newPostion.getY());
		Assert.assertEquals(CardinalDirection.S,newPostion.getDirection());
	}
	@Test
	public void moveForwardWhenPointingToEastShouldCreateNewPositionWithIncreasedXCoordinate() {
		Position p = new Position(3, 3, CardinalDirection.E);
		
		Position newPostion = p.moveForward();
		
		Assert.assertNotEquals(p, newPostion);
		Assert.assertEquals(4,newPostion.getX());
		Assert.assertEquals(3,newPostion.getY());
		Assert.assertEquals(CardinalDirection.E,newPostion.getDirection());
	}
	@Test
	public void moveForwardWhenPointingToWestShouldCreateNewPositionWithDecreasedXCoordinate() {
		Position p = new Position(3, 3, CardinalDirection.W);
		
		Position newPostion = p.moveForward();
		
		Assert.assertNotEquals(p, newPostion);
		Assert.assertEquals(2,newPostion.getX());
		Assert.assertEquals(3,newPostion.getY());
		Assert.assertEquals(CardinalDirection.W,newPostion.getDirection());
	}
	@Test
	public void rotateLeftShouldCreateNewPositionWithCardinalDirectionLeftRotated(){
		CardinalDirection direction = Mockito.mock(CardinalDirection.class);
		Position p = new Position(2, 3, direction);
		
		CardinalDirection newDirection = Mockito.mock(CardinalDirection.class);
		Mockito.when(direction.rotateLeft()).thenReturn(newDirection);
		
		Position newPostion = p.rotateLeft();
		
		Assert.assertNotEquals(p, newPostion);
		Assert.assertEquals(2,newPostion.getX());
		Assert.assertEquals(3,newPostion.getY());
		Assert.assertEquals(newDirection,newPostion.getDirection());
	}
	@Test
	public void rotateRightShouldCreateNewPositionWithCardinalDirectionRightRotated(){
		CardinalDirection direction = Mockito.mock(CardinalDirection.class);
		Position p = new Position(2, 3, direction);
		
		CardinalDirection newDirection = Mockito.mock(CardinalDirection.class);
		Mockito.when(direction.rotateRight()).thenReturn(newDirection);
		
		Position newPostion = p.rotateRight();
		
		Assert.assertNotEquals(p, newPostion);
		Assert.assertEquals(2,newPostion.getX());
		Assert.assertEquals(3,newPostion.getY());
		Assert.assertEquals(newDirection,newPostion.getDirection());
	}
	@Test
	public void toStringShouldReturnXYAndCoordinateValuesFormatted(){
		Position p = new Position(1, 3, CardinalDirection.N);
		Assert.assertEquals("1 3 N", p.toString());
	}
}
