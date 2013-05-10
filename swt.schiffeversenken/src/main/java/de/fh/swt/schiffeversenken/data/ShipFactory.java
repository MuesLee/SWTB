package de.fh.swt.schiffeversenken.data;

public abstract class ShipFactory {
	
	public ShipFactory() {
		super();
	}

	public abstract Ship createShip(String name);
}
