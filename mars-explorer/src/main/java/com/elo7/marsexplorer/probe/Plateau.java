package com.elo7.marsexplorer.probe;

import java.io.Serializable;

/**
 * Planalto/área onde as sondas serão implantadas e navegarão
 *
 */
public class Plateau implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private int x;
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
