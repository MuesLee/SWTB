package de.fh.swt.schiffeversenken.data;

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
