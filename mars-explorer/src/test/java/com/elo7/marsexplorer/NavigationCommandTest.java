package com.elo7.marsexplorer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/** Testes para {@link NavigationCommand} */
@RunWith(MockitoJUnitRunner.class)
public class NavigationCommandTest {

	@Mock
	private Position position;
	@Mock
	private Position expectedNewPosition;

	@Test
	public void LCommandDetermineNewPositionShouldRotatePostionLeft() {
		Mockito.when(position.rotateLeft()).thenReturn(expectedNewPosition);
		Position newPosition = NavigationCommand.L.determineNewPosition(position);
		Assert.assertEquals(expectedNewPosition, newPosition);
	}

	@Test
	public void RCommandDetermineNewPositionShouldRotatePostionRight() {
		Mockito.when(position.rotateRight()).thenReturn(expectedNewPosition);
		Position newPosition = NavigationCommand.R.determineNewPosition(position);
		Assert.assertEquals(expectedNewPosition, newPosition);
	}

	@Test
	public void MCommandDetermineNewPositionShouldMovePostionForward() {
		Mockito.when(position.moveForward()).thenReturn(expectedNewPosition);
		Position newPosition = NavigationCommand.M.determineNewPosition(position);
		Assert.assertEquals(expectedNewPosition, newPosition);
	}

}
