package com.elo7.marsexplorer.probe;

import javax.persistence.Embeddable;

/** Posição da sonda: coordenadas + direção */
@Embeddable
public class Position {
//TODO validacao de notnull + testes
	private int x;
	private int y;
	private CardinalDirection direction;
	
	public Position(){
		
	}

	public Position(final int x, final int y, final CardinalDirection direction) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	/**
	 * Move 1 unidade no sentido da {@link Position#direction} atual.
	 * 
	 * @return Nova {@link Position} após relizar o movimento
	 */
	Position move() {
		Position newPosition = positionMover().moveTowardsDirection();
		return newPosition;
	}

	/**
	 * Gira para a esquerda da {@link Position#direction} atual.
	 * 
	 * @return Nova {@link Position} após relizar o giro
	 */
	Position rotateLeft() {
		return new Position(x, y, direction.rotateLeft());
	}

	/**
	 * Gira para direita da {@link Position#direction} atual.
	 * 
	 * @return Nova {@link Position} após relizar o giro
	 */
	Position rotateRight() {
		return new Position(x, y, direction.rotateRight());
	}

	/**
	 * 
	 * @return Coordenada do eixo X da posição
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return Coordenada do eixo Y da posição
	 */
	public int getY() {
		return y;
	}

	/**
	 * Gancho utilizado para facilitar verificação de testes apenas
	 * 
	 * @return {@link PositionMover} que executa o movimento, recalculando os pontos
	 */
	PositionMover positionMover() {
		return PositionMover.of(this);
	}

	/**
	 * 
	 * @return {@link CardinalDirection} para onde aponta a posição
	 */
	public CardinalDirection getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return x + " " + y + " " + direction;
	}

}
