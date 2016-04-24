package com.elo7.marsexplorer;

enum NavigationCommand {
	L {
		@Override
		Position calcNewPosition(Position from) {
			return from.rotateLeft();
		}
	},
	R {
		@Override
		Position calcNewPosition(Position from) {
			return from.rotateRight();
		}
	},
	M {
		@Override
		Position calcNewPosition(Position from) {
			return from.move();
		}
	};

	abstract Position calcNewPosition(final Position from);

}
