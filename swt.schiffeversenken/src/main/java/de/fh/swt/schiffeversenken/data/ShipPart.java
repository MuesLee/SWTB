package de.fh.swt.schiffeversenken.data;

//prüfen, ob Schiffsteil intakt ist oder getroffen wurde und einem Schiff zuordnen
public class ShipPart
{

	private boolean intact = true;

	public boolean isIntact()
	{
		return intact;
	}

	public void wasHit()
	{
		this.intact = false;
	}

	public Ship getShip()
	{
		return schiff;
	}

	private Ship schiff;

	public ShipPart(Ship schiff)
	{
		this.schiff = schiff;
	}

	@Override
	public String toString()
	{
		return "Schiffsteil [intakt=" + intact + ", schiff=" + schiff + "]";
	}

}
