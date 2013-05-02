package de.fh.swt.schiffeversenken.data;

public class SubmarineFactory extends ShipFactory{

	public SubmarineFactory() {
		super();
	}

	@Override
	public Ship erzeugeSchiff(String name) {
		return new Submarine(name);
	}
	
	
}
