package com.thiagolrc.marsexplorer.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.thiagolrc.marsexplorer.domain.NavigationCommand;
import com.thiagolrc.marsexplorer.domain.Position;

/** Testes para {@link NavigationCommand} */
@RunWith(MockitoJUnitRunner.class)
public class NavigationCommandTest {

	@Mock
	private Position position;
	@Mock
	private Position expectedNewPosition;

	@Test
	public void LCommandCalcNewPositionShouldRotatePostionLeft() {
		Mockito.when(position.rotateLeft()).thenReturn(expectedNewPosition);
		Position newPosition = NavigationCommand.L.calcNewPosition(position);
		Assert.assertEquals(expectedNewPosition, newPosition);
	}

	@Test
	public void RCommandCalcNewPositionShouldRotatePostionRight() {
		Mockito.when(position.rotateRight()).thenReturn(expectedNewPosition);
		Position newPosition = NavigationCommand.R.calcNewPosition(position);
		Assert.assertEquals(expectedNewPosition, newPosition);
	}

	@Test
	public void MCommandCalcNewPositionShouldMovePostionForward() {
		Mockito.when(position.move()).thenReturn(expectedNewPosition);
		Position newPosition = NavigationCommand.M.calcNewPosition(position);
		Assert.assertEquals(expectedNewPosition, newPosition);
	}

}
