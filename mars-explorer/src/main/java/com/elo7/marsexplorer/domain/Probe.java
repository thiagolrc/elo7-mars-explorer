package com.elo7.marsexplorer.domain;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elo7.marsexplorer.exception.BadRequestException;

/** Sonda em Marte. Inicia em uma posição e recebe comandos de navegação */
@Entity
public class Probe {

	@Transient
	private final Log logger = LogFactory.getLog(this.getClass());

	@Id
	@GeneratedValue
	private int id;
	@Basic(optional = false)
	private Position position;
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
	 * @throws BadRequestException
	 *             ao tentar criar uma sonda fora da área do planalto
	 */
	public Probe(final int id, final Position position, final Plateau plateau) throws BadRequestException {
		this(position, plateau);
		this.id = id;
	}

	/**
	 * 
	 * @param position
	 * @param plateau
	 * @throws BadRequestException
	 *             ao tentar criar uma sonda fora da área do planalto
	 */
	public Probe(final Position position, final Plateau plateau) throws BadRequestException {
		this.plateau = plateau;
		validatePositionOnPlateau(position);
		this.position = position;
	}

	/**
	 * Executa um {@link NavigationCommand} e altera a posição da sonda conforme o comando
	 * 
	 * @return {@link Position} representando com a nova localização da câmera
	 * @throws BadRequestException
	 *             caso o comando tente navegar a sonda para fora do planalto de exploração a que ela pertence.
	 */
	public Position executeCommand(final NavigationCommand navigationCommand) throws BadRequestException {
		Position newPosition = navigationCommand.calcNewPosition(position);
		validatePositionOnPlateau(newPosition);
		logger.debug(String.format("Movendo sonda %s da posicao [%s] para [%s]", id, position.toString(), newPosition.toString()));
		this.position = newPosition;
		return position;
	}

	/**
	 * Executa um conjunto de {@link NavigationCommand}, recalculando, validando e alterando a posição da sonda a cada comando.
	 */
	public Position executeCommands(final List<NavigationCommand> navigationCommands) {
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

	private void validatePositionOnPlateau(final Position newPosition) throws BadRequestException {
		if (!plateau.isPositionValid(newPosition)) {
			logger.warn(String.format("Tentativa de mover sonda %s da posicao [%s] para [%s] que fica fora do planalto", id, this.position.toString(), newPosition.toString()));
			throw new BadRequestException("Não é possível mover a sonda para a posição " + newPosition.toString() + "poisi esta está fora do planalto.");
		}
	}

}
