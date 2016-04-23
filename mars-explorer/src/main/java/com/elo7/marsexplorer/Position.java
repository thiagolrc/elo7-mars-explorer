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

	Position moveForward() {
		// TODO
		throw new UnsupportedOperationException();
	}

	Position rotateLeft() {
		// TODO
		throw new UnsupportedOperationException();
	}

	Position rotateRight() {
		// TODO
		throw new UnsupportedOperationException();
	}

	int getX() {
		return x;
	}

	int getY() {
		return y;
	}

	CardinalDirection getDirection() {
		return direction;
	}
	
	
}
