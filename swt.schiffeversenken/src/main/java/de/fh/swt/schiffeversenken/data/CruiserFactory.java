package de.fh.swt.schiffeversenken.data;

public class CruiserFactory extends ShipFactory{

	public CruiserFactory() {
		super();
	}

	@Override
	public Ship createShip(String name) {
		return new Cruiser(name);
	}
	
	
}
