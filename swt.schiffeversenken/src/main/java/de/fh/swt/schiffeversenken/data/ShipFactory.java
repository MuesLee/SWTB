package de.fh.swt.schiffeversenken.data;

//�bergeordnete Klasse zum Erstellen der einzelnen Schiffe
public abstract class ShipFactory {
	
	public ShipFactory() {
		super();
	}

	public abstract Ship createShip(String name);
}
