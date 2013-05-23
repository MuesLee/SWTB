package de.fh.swt.schiffeversenken.data;

public class SubmarineFactory extends ShipFactory{

	public SubmarineFactory() {
		super();
	}

	@Override
	public Ship createShip(String name) {
		return new Submarine(name);
	}
	
	
}
