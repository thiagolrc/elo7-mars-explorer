package com.elo7.marsexplorer;

import java.util.ArrayList;
import java.util.List;

//TODO Para pensar: a única função do planalto seria para delimitar o plano de navegação da sonda? Não vejo outra aplicação/necessidade por eqt.
class Plateau {

	private final int x;
	private final int y;
	private final List<Probe> probes = new ArrayList<>();

	Plateau(final int x, final int y) {
		super();
		this.x = x;
		this.y = y;
	}

	List<Probe> deploy(final Probe probe) {
		// TODO
		throw new UnsupportedOperationException();
	}

	void validatePositioning(final Position position) throws IllegalArgumentException {
		// TODO
		throw new UnsupportedOperationException();
	}

}
