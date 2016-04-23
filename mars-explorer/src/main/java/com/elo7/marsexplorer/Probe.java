package com.elo7.marsexplorer;

import java.util.List;

/** A sonda. Inicia em uma posição e recebe comandos de navegação */
class Probe {

	private Position position;

	Probe(final Position position) {
		this.position = position;
	}

	Position execute(final List<NavigationCommand> commands) {
		// TODO
		throw new UnsupportedOperationException();
	}
}
