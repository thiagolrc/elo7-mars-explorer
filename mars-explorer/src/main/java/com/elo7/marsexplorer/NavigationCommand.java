package com.elo7.marsexplorer;

enum NavigationCommand {
	L {
		@Override
		Position determineNewPosition(Position position) {
			return position.rotateLeft();
		}
	},
	R {
		@Override
		Position determineNewPosition(Position position) {
			return position.rotateRight();
		}
	},
	M {
		@Override
		Position determineNewPosition(Position position) {
			return position.moveForward();
		}
	};

	abstract Position determineNewPosition(final Position position);

}
