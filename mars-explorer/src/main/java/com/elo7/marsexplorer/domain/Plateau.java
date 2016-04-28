package com.elo7.marsexplorer.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Planalto/área onde as sondas serão implantadas e navegarão
 *
 */
@Entity
public class Plateau implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Max(0)
	@Min(0) // quando tivermos o PUT teremos que criar grupos de validações para separa pois no PUT deve ser não nulo
	private int id;
	@Min(1)
	private int x;
	@Min(1)
	private int y;

	public Plateau() {
		super();
	}

	public Plateau(final int x, final int y) {
		this();
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

	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
