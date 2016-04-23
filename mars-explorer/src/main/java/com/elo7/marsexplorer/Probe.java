package com.elo7.marsexplorer;

import java.util.List;

/** Sonda em Marte. Inicia em uma posição e recebe comandos de navegação */
class Probe {

	private Position position;
	private final Plateau plateau;

	Probe(final Position position, final Plateau plateau) {
		this.position = position;
		this.plateau = plateau;
	}

	Position executeCommand(final NavigationCommand navigationCommand) {
		Position newPosition = navigationCommand.determineNewPosition(position);
		plateau.validatePositioning(newPosition);
		this.position = newPosition;
		return position;
	}

	Position executeCommands(final List<NavigationCommand> navigationCommands) {
		navigationCommands.stream().forEach(c -> this.executeCommand(c));
		return position;
	}

	Position getPosition() {
		return position;
	}

}
