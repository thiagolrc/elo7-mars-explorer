package com.elo7.marsexplorer.probe;

import static com.elo7.marsexplorer.probe.NavigationCommand.L;
import static com.elo7.marsexplorer.probe.NavigationCommand.M;
import static com.elo7.marsexplorer.probe.NavigationCommand.R;

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

import com.elo7.marsexplorer.probe.CardinalDirection;
import com.elo7.marsexplorer.probe.NavigationCommand;
import com.elo7.marsexplorer.probe.Plateau;
import com.elo7.marsexplorer.probe.Position;
import com.elo7.marsexplorer.probe.Probe;

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
		Mockito.when(plateau.isPositionValid(Matchers.any(Position.class))).thenReturn(true);
		probe = new Probe(position, plateau);
	}

	@Test
	public void executeCommandShouldDetermineNewPositionValidateItAndModifyProbesPosition() {
		NavigationCommand c = Mockito.mock(NavigationCommand.class);
		Position expectedPosition = Mockito.mock(Position.class);
		Mockito.when(c.calcNewPosition(position)).thenReturn(expectedPosition);

		Position newPosition = probe.executeCommand(c);

		Assert.assertEquals(expectedPosition, newPosition);
		Mockito.verify(plateau).isPositionValid(expectedPosition);
	}

	@Test
	public void executeCommandShouldFailWithoutModifyingCurrentPositionIfNewPositionIsNotAllowedOnPlateau() {
		NavigationCommand c = Mockito.mock(NavigationCommand.class);
		Position expectedPosition = Mockito.mock(Position.class);
		Mockito.when(c.calcNewPosition(position)).thenReturn(expectedPosition);
		Mockito.when(plateau.isPositionValid(expectedPosition)).thenReturn(false);

		try {
			probe.executeCommand(c);
			Assert.fail("it was supposed to fail");
		} catch (IllegalArgumentException e) {
			Assert.assertEquals(position, probe.getPosition());
		}
	}

	/*
	 * Ao executar vários comandos de uma vez, deve delegar para a execução de 1 comando simples, na sequência em que são recebidos
	 */
	@Test
	public void executeCommandsShouldExecuteAllCommandsIndividually() {
		Probe partialMock = Mockito.mock(Probe.class);
		Mockito.when(partialMock.executeCommands(Matchers.anyListOf(NavigationCommand.class))).thenCallRealMethod();

		NavigationCommand c1 = L;
		NavigationCommand c2 = M;
		NavigationCommand c3 = R;

		InOrder order = Mockito.inOrder(partialMock);
		Mockito.when(partialMock.executeCommand(Matchers.any(NavigationCommand.class))).thenReturn(position);

		partialMock.executeCommands(Arrays.asList(c1, c2, c3));

		order.verify(partialMock).executeCommand(c1);
		order.verify(partialMock).executeCommand(c2);
		order.verify(partialMock).executeCommand(c3);
	}

	@Test
	public void testSpecificationExamples() {
		Plateau realPlateau = new Plateau(5, 5);
		Probe probe1 = new Probe(new Position(1, 2, CardinalDirection.N), realPlateau);
		Position probe1FinalPosition = probe1.executeCommands(Arrays.asList(L, M, L, M, L, M, L, M, M));
		Assert.assertEquals("1 3 N", probe1FinalPosition.toString());
		Assert.assertEquals("1 3 N", probe1.getPosition().toString());

		Probe probe2 = new Probe(new Position(3, 3, CardinalDirection.E), realPlateau);
		Position probe2FinalPosition = probe2.executeCommands(Arrays.asList(M, M, R, M, M, R, M, R, R, M));
		Assert.assertEquals("5 1 E", probe2FinalPosition.toString());
		Assert.assertEquals("5 1 E", probe2.getPosition().toString());
	}

}
