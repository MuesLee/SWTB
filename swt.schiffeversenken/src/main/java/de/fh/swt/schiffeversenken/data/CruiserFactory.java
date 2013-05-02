package de.fh.swt.schiffeversenken.data;

public class CruiserFactory extends ShipFactory{

	public CruiserFactory() {
		super();
	}

	@Override
	public Ship erzeugeSchiff(String name) {
		return new Cruiser(name);
	}
	
	
}
