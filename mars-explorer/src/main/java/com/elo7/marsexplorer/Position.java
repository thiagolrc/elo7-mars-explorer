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
		// TODO Esquisito isso, talvez a direção cardinal devesse virar um
		// CardinalPosition e não só conter a direção, mas tb se mover. Isso
		// pode evitar esse switch bizarro
		Position newPosition;
		switch (direction) {
		case N:
			newPosition = new Position(x, y + 1, direction);
			break;
		case S:
			newPosition = new Position(x, y - 1, direction);
			break;
		case E:
			newPosition = new Position(x + 1, y, direction);
			break;
		case W:
			newPosition = new Position(x - 1, y, direction);
			break;

		default:
			throw new UnsupportedOperationException("Direção cardinal não reconhecida");

		}
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

	CardinalDirection getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return x + " " + y + " " + direction;
	}

}
