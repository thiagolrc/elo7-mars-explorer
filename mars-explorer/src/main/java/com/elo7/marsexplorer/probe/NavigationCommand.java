package com.elo7.marsexplorer.probe;

/**
 * Comandos de navegação no plano.
 * <p>
 * <lo>
 * <li>{@link NavigationCommand#L}: instrui a sonda a apontar para direção à esquerda de uma {@link Position}</li>
 * <li>{@link NavigationCommand#R}: instrui a sonda a apontar para direção à direita de uma {@link Position}</li>
 * <li>{@link NavigationCommand#M}: instrui a sonda a andar na direção do sentido em que se encontra {@link Position}</li> </lo>
 * </p>
 *
 */
public enum NavigationCommand {
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

	/**
	 * Calcula qual seria a nova posição ao aplicar o comando em determinada posição.
	 * 
	 * @param from
	 *            {@link Position} a partir da qual o comando deve se basear para o cálculo da nova posição
	 * @return nova {@link Position} após aplicar o comando na posição atual
	 */
	abstract Position calcNewPosition(final Position from);

}
