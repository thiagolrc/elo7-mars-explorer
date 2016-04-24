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

	Position move() {
		Position newPosition = positionMover().moveTowardsDirection();
		return newPosition;
	}

	Position rotateLeft() {
		return new Position(x, y, direction.rotateLeft());
	}

	Position rotateRight() {
		return new Position(x, y, direction.rotateRight());
	}

	int getX() {
		return x;
	}

	int getY() {
		return y;
	}
	
	PositionMover positionMover(){
		return PositionMover.of(this);
	}

	CardinalDirection getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return x + " " + y + " " + direction;
	}

}
