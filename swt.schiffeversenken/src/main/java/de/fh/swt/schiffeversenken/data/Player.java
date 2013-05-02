package de.fh.swt.schiffeversenken.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player
{

	private String name;
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
		this.shots = new HashSet<Shot>();
		this.ships = new ArrayList<Ship>();
		this.seamap = new Seamap(seamapSize);
		fillShipList();

	}

	private void fillShipList()
	{
		ShipFactory bShipFactory = new BattleshipFactory();
		ShipFactory cShipFactory = new CruiserFactory();
		ShipFactory dShipFactory = new DestroyerFactory();
		ShipFactory sShipFactory = new SubmarineFactory();

		ships.add(bShipFactory.erzeugeSchiff("Schlachtschiff"));
		ships.add(cShipFactory.erzeugeSchiff("Kreuzer 1"));
		ships.add(cShipFactory.erzeugeSchiff("Kreuzer 2"));
		ships.add(dShipFactory.erzeugeSchiff("Zerstörer 1"));
		ships.add(dShipFactory.erzeugeSchiff("Zerstörer 2"));
		ships.add(dShipFactory.erzeugeSchiff("Zerstörer 3"));
		ships.add(sShipFactory.erzeugeSchiff("U-Boot 1"));
		ships.add(sShipFactory.erzeugeSchiff("U-Boot 2"));
		ships.add(sShipFactory.erzeugeSchiff("U-Boot 3"));
		ships.add(sShipFactory.erzeugeSchiff("U-Boot 4"));
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

	public void putShipOnSeamap(Ship ship, Coords coords, Direction dir) throws IllegalShipPlacementException
	{
		seamap.putShipOnSeamap(ship, coords, dir);
	}

}
