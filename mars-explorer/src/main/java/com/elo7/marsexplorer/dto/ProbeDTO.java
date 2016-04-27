package com.elo7.marsexplorer.dto;

import com.elo7.marsexplorer.domain.CardinalDirection;

public class ProbeDTO {
	private int id;// TODO notnull + testes
	private int x;
	private int y;
	private CardinalDirection direction;

	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public CardinalDirection getDirection() {
		return direction;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setDirection(CardinalDirection direction) {
		this.direction = direction;
	}

}
