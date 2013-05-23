package de.fh.swt.schiffeversenken.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.fh.swt.schiffeversenken.gui.Messages;

public class Player
{

	private String name;
	private List<Ship> ships;
	private Set<Shot> shots = new HashSet<Shot>();
	private Seamap seamap;

	//prüfen, ob ein Spieler besiegt wurde
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

	//Daten zum Spieler festlegen(Name, abgegebene Schüsse, Schiffsplatzierung)
	public Player(String name, int seamapSize)
	{
		this.name = name;
		this.shots = new HashSet<Shot>();
		this.ships = new ArrayList<Ship>();
		this.seamap = new Seamap(seamapSize);
		fillShipList();

	}

	//füllen der Liste mit den zu platzierenden Schiffen
	private void fillShipList()
	{
		ShipFactory bShipFactory = new BattleshipFactory();
		ShipFactory cShipFactory = new CruiserFactory();
		ShipFactory dShipFactory = new DestroyerFactory();
		ShipFactory sShipFactory = new SubmarineFactory();

		ships.add(bShipFactory.createShip(Messages.getString("Player.NameBattleship"))); //$NON-NLS-1$
		ships.add(cShipFactory.createShip(Messages.getString("Player.NameCruiser1"))); //$NON-NLS-1$
		ships.add(cShipFactory.createShip(Messages.getString("Player.NameCruiser2"))); //$NON-NLS-1$
		ships.add(dShipFactory.createShip(Messages.getString("Player.NameDestroyer1"))); //$NON-NLS-1$
		ships.add(dShipFactory.createShip(Messages.getString("Player.NameDestroyer2"))); //$NON-NLS-1$
		ships.add(dShipFactory.createShip(Messages.getString("Player.NameDestroyer3"))); //$NON-NLS-1$
		ships.add(sShipFactory.createShip(Messages.getString("Player.NameSubmarine1"))); //$NON-NLS-1$
		ships.add(sShipFactory.createShip(Messages.getString("Player.NameSubmarine2"))); //$NON-NLS-1$
		ships.add(sShipFactory.createShip(Messages.getString("Player.NameSubmarine3"))); //$NON-NLS-1$
		ships.add(sShipFactory.createShip(Messages.getString("Player.NameSubmarine4"))); //$NON-NLS-1$
	}

	//getter- und setter-Methoden zu den benötigten Attributen für einen Spieler(Schiffe, Seekarte, Name, Schüsse)
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

	//Schiff auf Seekarte setzen
	public void putShipOnSeamap(Ship ship, Coords coords, Direction dir) throws IllegalShipPlacementException
	{
		seamap.putShipOnSeamap(ship, coords, dir);
	}

}
