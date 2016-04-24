package com.elo7.marsexplorer;

public interface PositionMover {

	Position moveTowardsDirection();

	public static PositionMover of(Position p) {
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
