package com.elo7.marsexplorer;

import java.util.List;

/** Sonda em Marte. Inicia em uma posição e recebe comandos de navegação */
class Probe {

	private Position position;
	private final Plateau plateau;

	//TODO Sera q nao faz mais sentido o plateau fazer parte da posicao ou coisa parecida?
	Probe(final Position position, final Plateau plateau) {
		this.position = position;
		this.plateau = plateau;
	}

	/**
	 * Executa um {@link NavigationCommand} e altera a posição da sonda conforme
	 * o comando
	 * 
	 * @return {@link Position} representando a FS nova localização da câmera
	 * @throws IllegalArgumentException
	 *             caso o comando tente navegar a sonda para fora do planalto de
	 *             exploração a que ela pertence.
	 */
	Position executeCommand(final NavigationCommand navigationCommand) throws IllegalArgumentException {
		Position newPosition = navigationCommand.determineNewPosition(position);
		plateau.validatePositioning(newPosition);
		this.position = newPosition;
		return position;
	}

	/**
	 * Executa um conjunto de {@link NavigationCommand}, recalculando, validando
	 * e alterando a posição da sonda a cada comando.
	 */
	Position executeCommands(final List<NavigationCommand> navigationCommands) {
		navigationCommands.stream().forEach(c -> this.executeCommand(c));
		return position;
	}

	/**
	 * @return {@link Position} atual da sonda.
	 */
	Position getPosition() {
		return position;
	}

}
