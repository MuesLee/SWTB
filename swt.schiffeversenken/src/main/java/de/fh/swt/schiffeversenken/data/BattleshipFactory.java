package de.fh.swt.schiffeversenken.data;

public class BattleshipFactory extends ShipFactory{

	public BattleshipFactory() {
		super();
	}

	@Override
	public Ship erzeugeSchiff(String name) {
		return new Battleship(name);
	}
	
	
}
