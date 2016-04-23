package com.elo7.marsexplorer;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/** Testes para {@link Probe} */
@RunWith(MockitoJUnitRunner.class)
public class ProbeTest {

	@Mock
	private Position position;
	@Mock
	private Plateau plateau;

	private Probe probe;

	@Before
	public void before() {
		probe = new Probe(position, plateau);
	}

	@Test
	public void executeCommandShouldDetermineNewPositionValidateItAndModifyProbesPosition() {
		NavigationCommand c = Mockito.mock(NavigationCommand.class);
		Position expectedPosition = Mockito.mock(Position.class);
		Mockito.when(c.determineNewPosition(position)).thenReturn(expectedPosition);

		Position newPosition = probe.executeCommand(c);

		Assert.assertEquals(expectedPosition, newPosition);
		Mockito.verify(plateau).validatePositioning(expectedPosition);
	}

	@Test
	public void executeCommandShouldFailWithoutModifyingCurrentPositionIfNewPositionIsNotAllowedOnPlateau() {
		NavigationCommand c = Mockito.mock(NavigationCommand.class);
		Position expectedPosition = Mockito.mock(Position.class);
		Mockito.when(c.determineNewPosition(position)).thenReturn(expectedPosition);
		Mockito.doThrow(new IllegalArgumentException()).when(plateau).validatePositioning(expectedPosition);

		try {
			probe.executeCommand(c);
			Assert.fail("it was supposed to fail");
		} catch (IllegalArgumentException e) {
			Assert.assertEquals(position, probe.getPosition());
		}
	}

	/*
	 * Ao executar vários comandos de uma vez, deve delegar para a execução de 1
	 * comando simples, na sequência em que são recebidos
	 */
	@Test
	public void executeCommandsShouldExecuteAllCommandsIndividually() {
		Probe partialMock = Mockito.mock(Probe.class);
		Mockito.when(partialMock.executeCommands(Matchers.anyListOf(NavigationCommand.class))).thenCallRealMethod();

		NavigationCommand c1 = NavigationCommand.L;
		NavigationCommand c2 = NavigationCommand.M;
		NavigationCommand c3 = NavigationCommand.R;

		InOrder order = Mockito.inOrder(partialMock);
		Mockito.when(partialMock.executeCommand(Matchers.any(NavigationCommand.class))).thenReturn(position);

		partialMock.executeCommands(Arrays.asList(c1, c2, c3));

		order.verify(partialMock).executeCommand(c1);
		order.verify(partialMock).executeCommand(c2);
		order.verify(partialMock).executeCommand(c3);
	}

}
