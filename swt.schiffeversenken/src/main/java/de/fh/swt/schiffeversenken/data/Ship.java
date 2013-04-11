package de.fh.swt.schiffeversenken.data;

public abstract class Ship
{

	private ShipPart[] shipParts;
	private String name;
	protected int amountOfParts = 1;

	public boolean isIntact()
	{
		for (ShipPart s : shipParts)
		{
			if (s.isIntact())
			{
				return true;
			}
		}
		return false;
	}

	public Ship(String name, int amountOfParts)
	{
		this.amountOfParts = amountOfParts;
		this.name = name;
		initiate();
	}

	private void initiate()
	{
		this.shipParts = new ShipPart[amountOfParts];
		for (int i = 0; i < amountOfParts; i++)
		{
			shipParts[i] = new ShipPart(this);
		}
	}

	public ShipPart[] getShipParts()
	{
		return shipParts;
	}

	public void setShipParts(ShipPart[] schiffsteile)
	{
		this.shipParts = schiffsteile;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + amountOfParts;
		result = (prime * result) + ((name == null) ? 0 : name.hashCode());
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
		Ship other = (Ship) obj;
		if (amountOfParts != other.amountOfParts)
		{
			return false;
		}
		if (name == null)
		{
			if (other.name != null)
			{
				return false;
			}
		}
		else if (!name.equals(other.name))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return name + " - " + amountOfParts + "Hitpoints";
	}

}
