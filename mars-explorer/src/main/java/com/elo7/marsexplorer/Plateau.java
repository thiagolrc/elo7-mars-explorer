package com.elo7.marsexplorer;

/**
 * Planalto/área onde as sondas serão implantadas e navegarão
 *
 */
class Plateau {

	private final int x;
	private final int y;

	Plateau(final int x, final int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Determina se uma dada posição está dentro do planalto especificado
	 * 
	 * @param position
	 *            {@link Position} a ser validada
	 **/
	boolean isPositionValid(final Position position) {
		return (position.getX() >= 0 && position.getY() >= 0 && position.getX() <= x && position.getY() <= y);
	}

}
