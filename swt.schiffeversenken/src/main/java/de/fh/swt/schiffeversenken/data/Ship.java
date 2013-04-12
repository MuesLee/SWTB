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
	public String toString()
	{
		return name + " - " + amountOfParts + "Hitpoints";
	}

}
