package com.elo7.marsexplorer;

/**
 * Pontos/direções cardinais.
 * <p>
 * Seqüencia no sentido horário: {@link CardinalDirection#N}, {@link CardinalDirection#E},{@link CardinalDirection#S}, {@link CardinalDirection#W} (Rosa dos Ventos)
 * </p>
 */
enum CardinalDirection {
	/**
	 * Norte
	 */
	N {
		@Override
		CardinalDirection rotateLeft() {
			return W;
		}

		@Override
		CardinalDirection rotateRight() {
			return E;
		}
	},
	/**
	 * Leste
	 */
	E {
		@Override
		CardinalDirection rotateLeft() {
			return N;
		}

		@Override
		CardinalDirection rotateRight() {
			return S;
		}
	},
	/**
	 * Sul
	 */
	S {
		@Override
		CardinalDirection rotateLeft() {
			return E;
		}

		@Override
		CardinalDirection rotateRight() {
			return W;
		}
	},
	/**
	 * Oeste
	 */
	W {
		@Override
		CardinalDirection rotateLeft() {
			return S;
		}

		@Override
		CardinalDirection rotateRight() {
			return N;
		}
	};

	/**
	 * Gira a direção cardinal no sentido anti-horário
	 * 
	 * @return {@link CardinalDirection} apontando para direção à esquerda(no sentido anti-horário) da posição atual
	 */
	abstract CardinalDirection rotateLeft();

	/**
	 * Gira a direção cardinal no sentido horário
	 * 
	 * @return {@link CardinalDirection} apontando para a direção à direita(no sentido horário) da posição atual
	 */
	abstract CardinalDirection rotateRight();
}
