package de.fh.swt.schiffeversenken.data;

public class BattleshipFactory extends ShipFactory{

	public BattleshipFactory() {
		super();
	}

	@Override
	public Ship createShip(String name) {
		return new Battleship(name);
	}
	
	
}
