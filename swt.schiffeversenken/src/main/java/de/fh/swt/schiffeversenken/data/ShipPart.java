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
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (intact ? 1231 : 1237);
		result = (prime * result) + ((schiff == null) ? 0 : schiff.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		ShipPart other = (ShipPart) obj;
		if (intact != other.intact)
		{
			return false;
		}
		if (schiff == null)
		{
			if (other.schiff != null)
			{
				return false;
			}
		}
		else if (!schiff.equals(other.schiff))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "Schiffsteil [intakt=" + intact + ", schiff=" + schiff + "]";
	}

}
