package com.elo7.marsexplorer;

enum CardinalDirection {
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

	abstract CardinalDirection rotateLeft();

	abstract CardinalDirection rotateRight();
}
