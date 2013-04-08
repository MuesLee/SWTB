package de.fh.swt.schiffeversenken.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.fh.swt.schiffeversenken.data.Battleship;
import de.fh.swt.schiffeversenken.data.Coords;
import de.fh.swt.schiffeversenken.data.Cruiser;
import de.fh.swt.schiffeversenken.data.Destroyer;
import de.fh.swt.schiffeversenken.data.Direction;
import de.fh.swt.schiffeversenken.data.IllegalShipPlacementException;
import de.fh.swt.schiffeversenken.data.Seamap;
import de.fh.swt.schiffeversenken.data.Ship;
import de.fh.swt.schiffeversenken.data.Shot;
import de.fh.swt.schiffeversenken.data.Submarine;

public class Player
{

	private String name;
	private final long id;
	private List<Ship> ships;
	private Set<Shot> shots = new HashSet<Shot>();
	private Seamap seamap;

	public boolean isDefeated()
	{
		for (Ship s : ships)
		{
			if (s.isIntact())
			{
				return false;
			}
		}

		return true;
	}

	public Player(String name, int seamapSize)
	{
		this.name = name;
		this.id = (long) Math.random();
		this.shots = new HashSet<Shot>();
		this.ships = new ArrayList<Ship>();
		this.seamap = new Seamap(seamapSize);
		ships.add(new Battleship("Schlachtschiff"));
		ships.add(new Cruiser("Kreuzer 1"));
		ships.add(new Cruiser("Kreuzer 2"));
		ships.add(new Destroyer("Zerstörer 1"));
		ships.add(new Destroyer("Zerstörer 2"));
		ships.add(new Destroyer("Zerstörer 3"));
		ships.add(new Submarine("U-Boot 1"));
		ships.add(new Submarine("U-Boot 2"));
		ships.add(new Submarine("U-Boot 3"));
		ships.add(new Submarine("U-Boot 4"));

	}

	public List<Ship> getShips()
	{
		return ships;
	}

	public void setShips(List<Ship> ships)
	{
		this.ships = ships;
	}

	public Seamap getSeamap()
	{
		return seamap;
	}

	public void setSeamap(Seamap coordsOfOwnShips)
	{
		this.seamap = coordsOfOwnShips;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Set<Shot> getShots()
	{
		return shots;
	}

	public void addShot(Shot shot)
	{
		this.shots.add(shot);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (int) (id ^ (id >>> 32));
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
		Player other = (Player) obj;
		if (id != other.id)
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

	public void putShipOnSeamap(Ship ship, Coords coords, Direction dir) throws IllegalShipPlacementException {
		seamap.putShipOnSeamap(ship, coords, dir);
	}

}
