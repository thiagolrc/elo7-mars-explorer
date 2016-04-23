package com.elo7.marsexplorer;

import java.util.List;

/** Sonda em Marte. Inicia em uma posição e recebe comandos de navegação */
class Probe {

	private Position position;

	Probe(final Position position) {
		this.position = position;
	}

	Position executeCommand(final NavigationCommand navigationCommand) {
		this.position = navigationCommand.determineNewPosition(position);
		return position;
	}

	Position executeCommands(final List<NavigationCommand> navigationCommands) {
		navigationCommands.stream().forEach(c -> this.executeCommand(c));
		return position;
	}
}
