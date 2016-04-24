package com.elo7.marsexplorer;

//TODO Para pensar: a única função do planalto seria para delimitar o plano de navegação da sonda? Não vejo outra aplicação/necessidade por eqt.
class Plateau {

	private final int x;
	private final int y;

	Plateau(final int x, final int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/** Determina se uma data posição está dentro do planalto especificado **/
	boolean isPositionValid(final Position position) {
		return (position.getX() >= 0 && position.getY() >= 0 && position.getX() <= x && position.getY() <= y);
	}

}
