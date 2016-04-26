package com.elo7.marsexplorer.probe;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/** Sonda em Marte. Inicia em uma posição e recebe comandos de navegação */
@Entity
public class Probe {

	@Id
	@GeneratedValue
	private int id;
	private Position position;// TODO notNull + testes
	@ManyToOne(optional = false)
	private Plateau plateau;

	public Probe() {
	}

	public int getId() {
		return id;
	}

	/**
	 * @param id
	 * @param position
	 * @param plateau
	 * @throws IllegalArgumentException
	 *             ao tentar criar uma sonda fora da área do planalto
	 */
	public Probe(final int id, final Position position, final Plateau plateau) throws IllegalArgumentException {
		this(position, plateau);
		this.id = id;
	}

	// TODO Sera q nao faz mais sentido o plateau fazer parte da posicao ou
	// coisa parecida?
	/**
	 * 
	 * @param position
	 * @param plateau
	 * @throws IllegalArgumentException
	 *             ao tentar criar uma sonda fora da área do planalto
	 */
	public Probe(final Position position, final Plateau plateau) throws IllegalArgumentException {
		this.plateau = plateau;
		validatePositionOnPlateau(position);
		this.position = position;
	}

	/**
	 * Executa um {@link NavigationCommand} e altera a posição da sonda conforme o comando
	 * 
	 * @return {@link Position} representando a FS nova localização da câmera
	 * @throws IllegalArgumentException
	 *             caso o comando tente navegar a sonda para fora do planalto de exploração a que ela pertence.
	 */
	Position executeCommand(final NavigationCommand navigationCommand) throws IllegalArgumentException {
		Position newPosition = navigationCommand.calcNewPosition(position);
		validatePositionOnPlateau(newPosition);
		this.position = newPosition;
		return position;
	}

	/**
	 * Executa um conjunto de {@link NavigationCommand}, recalculando, validando e alterando a posição da sonda a cada comando.
	 */
	Position executeCommands(final List<NavigationCommand> navigationCommands) {
		navigationCommands.stream().forEach(c -> this.executeCommand(c));
		return position;
	}

	/**
	 * @return {@link Position} atual da sonda.
	 */
	public Position getPosition() {
		return position;
	}

	/***
	 * 
	 * @return {@link Plateau} no qual a sonda foi instalada
	 */
	public Plateau getPlateau() {
		return plateau;
	}

	private void validatePositionOnPlateau(final Position position) throws IllegalArgumentException {
		if (!plateau.isPositionValid(position)) {
			throw new IllegalArgumentException("Posição da sonda está fora do planalto");
		}
	}

}
