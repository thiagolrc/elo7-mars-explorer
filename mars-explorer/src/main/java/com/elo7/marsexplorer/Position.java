package com.elo7.marsexplorer;

/** Posição da sonda: coordenadas + direção */
class Position {

	private final int x;
	private final int y;
	private final CardinalDirection direction;

	Position(final int x, final int y, final CardinalDirection direction) {
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
	int getX() {
		return x;
	}

	/**
	 * 
	 * @return Coordenada do eixo Y da posição
	 */
	int getY() {
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
	CardinalDirection getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return x + " " + y + " " + direction;
	}

}
