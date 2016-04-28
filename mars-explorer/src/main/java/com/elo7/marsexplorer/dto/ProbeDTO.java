package com.elo7.marsexplorer.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.elo7.marsexplorer.domain.CardinalDirection;

public class ProbeDTO {
	@Max(0)
	@Min(0) // quando tivermos o PUT teremos que criar grupos de validações para separa pois no PUT deve ser não nulo
	private int id;

	@Min(0)
	private int x;
	@Min(0)
	private int y;
	@NotNull
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
