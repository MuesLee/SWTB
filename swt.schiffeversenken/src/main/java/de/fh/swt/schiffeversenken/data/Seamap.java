package de.fh.swt.schiffeversenken.data;

import java.util.HashSet;
import java.util.Set;

import de.fh.swt.schiffeversenken.controller.GameManager;
import de.fh.swt.schiffeversenken.gui.Messages;

public class Seamap
{

	private ShipPart[][] shipParts;
	private int size;

	public Seamap(int size)
	{
		this.size = size;
		initiate();
	}

	// Seekarte initialisieren
	private void initiate()
	{
		this.shipParts = new ShipPart[size][size];
	}

	// gibt Schiffsteile zurück
	public ShipPart[][] getShipParts()
	{
		return shipParts;
	}

	// Schiffe auf Seekarte setzen(Schiffstyp, vorderste Koordinate, Richtung
	// übergeben), falls möglich
	public void putShipOnSeamap(Ship ship, Coords fore, Direction direction) throws IllegalShipPlacementException
	{

		if (ship == null)
		{
			GameManager.getLogger().error(Messages.getString("Seamap.LogTextNoShipChosen")); //$NON-NLS-1$
			throw new IllegalShipPlacementException(Messages.getString("Seamap.ErrorTextNoShipChosen")); //$NON-NLS-1$
			// throw new
			// IllegalShipPlacementException(Messages.getString("Seamap.InfoTextNoShip"));
		}

		/**
		 * Falls das Schiff nach unten ausgerichtet ist, wird x nicht erhöht und nur y verändert,
		 * weshalb der X-Startwert aus der Variablen fore genommen werden soll Was das bringt? Es
		 * erspart eine weitere for-Schleife. Keine Angst, es gibt für diese Methode diverse Tests^^
		 * 
		 * **/
		try
		{
			iterateOverShipPartsAndSetNewValues(fore, direction, ship.getShipParts());
			GameManager.getLogger().debug("Koordinaten des Schiffes: X=" + fore.getX() + " Y=" + fore.getY());
		}
		catch (IllegalShipPlacementException e)
		{
			try
			{
				removeShipParts(fore, direction, e.getFailureAtShipSegmentNumber());
			}
			catch (IllegalShipPlacementException e2)
			{
			}
			throw e;
		}

	}

	// Schiffsteile wieder entfernen, wenn ungültiger Weise gesetzt
	private void removeShipParts(Coords fore, Direction direction, int part) throws IllegalShipPlacementException
	{
		ShipPart[] shipPartsToDelete = new ShipPart[part];

		iterateOverShipPartsAndSetNewValues(fore, direction, shipPartsToDelete);
	}

	// über die einzelnen Schiffsteile iterieren und neue Werte setzen, wenn die
	// Schiffe korrekt positioniert wurden, ansonsten Fehlermeldung ausgeben
	private void iterateOverShipPartsAndSetNewValues(Coords fore, Direction direction, ShipPart[] newValues)
		throws IllegalShipPlacementException
	{

		for (int startwert = (fore.getX() * direction.getModX()) + (fore.getY() * direction.getModY()), segment = 0; segment < newValues.length; segment++, startwert++)
		{
			Coords coords = new Coords(((direction.getModX() * startwert) + (fore.getX() * direction.getModY())),
				((direction.getModX() * fore.getY()) + (startwert * direction.getModY())));

			try
			{
				if (legitShipPlacement(coords, newValues[segment]))
				{

					shipParts[(direction.getModX() * startwert) + (fore.getX() * direction.getModY())][(direction
						.getModX() * fore.getY()) + (startwert * direction.getModY())] = newValues[segment];

				}
				else
				{
					GameManager.getLogger().error(Messages.getString("Seamap.LogTextIllegalPlacementNearAnotherShip")); //$NON-NLS-1$
					throw new IllegalShipPlacementException(
						Messages.getString("Seamap.ErrorTextIllegalPlacementNearAnotherShip"), //$NON-NLS-1$
						segment);
				}
			}

			catch (ArrayIndexOutOfBoundsException a)
			{
				GameManager.getLogger().error(Messages.getString("Seamap.LogTextIllegalPlacementOutsideMap")); //$NON-NLS-1$
				throw new IllegalShipPlacementException(
					Messages.getString("Seamap.ErrorTextIllegalPlacementOutsideMap"), //$NON-NLS-1$
					segment);
			}
		}

	}

	public boolean shipCanBePlaced(Coords fore, Direction direction, Ship ship)
	{
		for (int startwert = (fore.getX() * direction.getModX()) + (fore.getY() * direction.getModY()), segment = 0; segment < ship.amountOfParts; segment++, startwert++)
		{
			Coords coords = new Coords(((direction.getModX() * startwert) + (fore.getX() * direction.getModY())),
				((direction.getModX() * fore.getY()) + (startwert * direction.getModY())));

			if (!legitShipPlacement(coords, ship.getShipParts()[segment]))
			{
				return false;
			}
		}
		return true;
	}

	// Schiffsteile ermitteln
	public ShipPart getShipPart(Coords coords)
	{
		if (coordsAreInsideOfTheField(coords))
		{
			return shipParts[coords.getX()][coords.getY()];
		}
		else
		{
			return null;
		}
	}

	// Platzierung der Schiffsteile korrekt?
	private boolean legitShipPlacement(Coords coords, ShipPart shipPart)
	{
		if (!coordsAreInsideOfTheField(coords))
		{
			return false;
		}

		if (!theseCoordsAndSurroundingCoordsAreFree(coords, shipPart))
		{
			return false;
		}

		return true;
	}

	private boolean coordsAreInsideOfTheField(Coords coords)
	{
		if ((coords.getX() >= 0) && (coords.getY() >= 0) && (coords.getX() < size) && (coords.getY() < size))
		{
			return true;
		}
		return false;
	}

	// berechnen, ob die Koordinaten des platzierten Schiffsteils zulässig sind
	private boolean theseCoordsAndSurroundingCoordsAreFree(Coords coords, ShipPart shipPartToPlace)
	{
		for (int x = -1; x < 2; x++)
		{
			for (int y = -1; y < 2; y++)
			{
				ShipPart excistingShipPart = null;
				try
				{
					excistingShipPart = shipParts[coords.getX() + x][coords.getY() + y];
				}
				catch (ArrayIndexOutOfBoundsException e)
				{

				}
				if (excistingShipPart != null)
				{
					if (!shipPartBelongsToGivenShip(shipPartToPlace, excistingShipPart.getShip()))
					{
						return false;
					}
				}
			}
		}
		return true;
	}

	// prüfen, ob Schiffsteil zu Schiff gehört
	private boolean shipPartBelongsToGivenShip(ShipPart givenShipPart, Ship givenShip)
	{

		if (givenShipPart == null)
		{
			return true;
		}

		return givenShipPart.getShip().equals(givenShip);

	}

	// getter- und setter-Methoden zu Schiffsteilen und Größe
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
						sb.append("O"); //$NON-NLS-1$
					}
					else
					{
						sb.append("X"); //$NON-NLS-1$
					}
				}
				else
				{
					sb.append(" "); //$NON-NLS-1$
				}
			}
			sb.append("\n"); //$NON-NLS-1$
		}

		return sb.toString();
	}

	public Set<Ship> getPlacedShips()
	{
		HashSet<Ship> ships = new HashSet<Ship>();

		for (ShipPart[] partpart : shipParts)
		{
			if (partpart == null)
			{
				continue;
			}
			for (ShipPart part : partpart)
			{
				if (part == null)
				{
					continue;
				}
				ships.add(part.getShip());
			}
		}
		return ships;
	}
}
