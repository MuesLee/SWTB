package de.fh.swt.schiffeversenken.data;

import java.util.Observable;

public class Seamap extends Observable
{

	private ShipPart[][] shipParts;
	private int size;

	public Seamap(int size)
	{
		this.size = size;
		initiate();
	}

	private void initiate()
	{

		this.shipParts = new ShipPart[size][size];
	}

	public ShipPart[][] getShipParts()
	{
		return shipParts;
	}

	public void putShipOnSeamap(Ship ship, Coords fore, Direction direction) throws IllegalShipPlacementException
	{

		if (ship == null)
		{
			throw new IllegalShipPlacementException("Kein Schiff ausgewählt!");
		}

		int modX = 1, modY = 1;

		try
		{

			if (direction == Direction.DOWN)
			{
				/**
				 * Falls das Schiff nach unten ausgerichtet ist, wird x nicht erhöht und nur y
				 * verändert, weshalb der X-Startwert aus der Variablen fore genommen werden soll
				 * Was das bringt? Es erspart eine weitere for-Schleife Keine Angst, es gibt für
				 * diese Methode diverse Tests^^
				 */
				modX = 0;
				modY = 1;
			}
			else
			{
				//s.o. nur umgekehrt
				modX = 1;
				modY = 0;
			}

			for (int startwert = (fore.getX() * modX) + (fore.getY() * modY), i = 0; startwert < ship.getShipParts().length; i++, startwert++)
			{

				ShipPart shipPart = shipParts[(modX * startwert) + (fore.getX() * modY)][(modX * fore.getY())
					+ (startwert * modY)];

				if (shipPart == null)
				{
					shipParts[(modX * startwert) + (fore.getX() * modY)][(modX * fore.getY()) + (startwert * modY)] = ship
						.getShipParts()[i];
					setChanged();
					notifyObservers(shipParts);
				}
				else
				{
					throw new IllegalShipPlacementException("Schiff konnte nicht platziert werden. Das Schiff "
						+ shipPart.getSchiff().getName() + " ist im Weg!");
				}

			}
		}

		catch (ArrayIndexOutOfBoundsException a)
		{
			throw new IllegalShipPlacementException(
				"Das Schiff ragte über den Rand der Welt und wäre herunter gefallen, hätten wir nicht so ein tolles Exceptionhandling");
		}
	}

	public ShipPart getShipPart(Coords coords)
	{
		if ((coords.getX() >= 0) && (coords.getY() >= 0) && (coords.getX() < size) && (coords.getY() < size))
		{
			return shipParts[coords.getX()][coords.getY()];
		}

		else
		{
			return null;
		}
	}

	public void setShipParts(ShipPart[][] coords)
	{
		this.shipParts = coords;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int groesse)
	{
		this.size = groesse;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < shipParts.length; i++)
		{
			for (int o = 0; o < shipParts.length; o++)
			{
				ShipPart s = shipParts[i][o];
				if (s != null)
				{
					if (s.isIntact())
					{
						sb.append("O");
					}
					else
					{
						sb.append("X");
					}
				}
				else
				{
					sb.append(" ");
				}
			}
			sb.append("\n");
		}

		return sb.toString();
	}

}
