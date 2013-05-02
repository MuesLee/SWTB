package de.fh.swt.schiffeversenken.data;

public class DestroyerFactory extends ShipFactory{

	public DestroyerFactory() {
		super();
	}

	@Override
	public Ship erzeugeSchiff(String name) {
		return new Destroyer(name);
	}
	
	
}
