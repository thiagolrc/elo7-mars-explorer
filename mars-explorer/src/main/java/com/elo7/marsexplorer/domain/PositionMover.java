package com.elo7.marsexplorer.domain;

/**
 * 
 * Movimentador de {@link Position} na direção de sua {@link CardinalDirection}.
 *
 */
interface PositionMover {

	/**
	 * Move uma {@link Position} na direção de sua {@link CardinalDirection}
	 * 
	 * @return Nova {@link Position} com novas coordenadas recalculadas
	 */
	Position moveTowardsDirection();

	/**
	 * Cria um movimentador específico para cada posição, baseando-se nas suas coordenadas e {@link CardinalDirection}.
	 * 
	 * @param p
	 *            {@link Position} para a qual o movimentador deve ser criado
	 * @return {@link PositionMover} capaz de mover corretamete o a posição em questão
	 */
	static PositionMover of(Position p) {
		switch (p.getDirection()) {
		case N:
			return (() -> new Position(p.getX(), p.getY() + 1, p.getDirection()));
		case S:
			return (() -> new Position(p.getX(), p.getY() - 1, p.getDirection()));
		case E:
			return (() -> new Position(p.getX() + 1, p.getY(), p.getDirection()));
		case W:
			return (() -> new Position(p.getX() - 1, p.getY(), p.getDirection()));

		default:
			throw new UnsupportedOperationException("Direção cardinal não reconhecida");

		}
	}
}
