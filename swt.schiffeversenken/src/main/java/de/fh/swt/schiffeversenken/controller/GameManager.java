package de.fh.swt.schiffeversenken.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;

import javax.swing.JOptionPane;

import de.fh.swt.schiffeversenken.data.Coords;
import de.fh.swt.schiffeversenken.data.Direction;
import de.fh.swt.schiffeversenken.data.HitType;
import de.fh.swt.schiffeversenken.data.IllegalShipPlacementException;
import de.fh.swt.schiffeversenken.data.Ship;
import de.fh.swt.schiffeversenken.data.ShipPart;
import de.fh.swt.schiffeversenken.data.Shot;
import de.fh.swt.schiffeversenken.gui.ConfFrame;
import de.fh.swt.schiffeversenken.gui.MainFrame;
import de.fh.swt.schiffeversenken.gui.ShipComponent;
import de.fh.swt.schiffeversenken.gui.ShipPlacementComponent;
import de.fh.swt.schiffeversenken.gui.ShipPlacementFrame;

public class GameManager extends Observable
{

	private Player playerOne;
	private Player playerTwo;
	private Player activePlayer;
	private MainFrame mainFrame;
	private ConfFrame confFrame;
	private ShipPlacementFrame shipPlacementFrame;
	public ShipPlacementFrame getShipPlacementFrame() {
		return shipPlacementFrame;
	}

	private ShipComponent shipComponent;
	private ShipPlacementComponent shipPlacementComponent;
	
	private boolean fireAtWill = false;

	public GameManager()
	{
		intiate();
	}

	private void intiate()
	{
		playerOne = new Player("Spieler 1", 12);
		playerTwo = new Player("Spieler 1", 12);
		activePlayer = playerOne;

	}

	public void start()
	{	createShipComponent();
		mainFrame = new MainFrame(this, shipComponent);
	}

	public void handleShot(Shot shot)
	{

		ceaseFire();

		ShipPart shipPart = getInactivePlayer().getSeamap().getShipPart(shot.getCoords());
		System.out.println("schiffsteil" + shipPart);

		if ((shipPart == null))
		{
			shot.setHit(HitType.NOHIT);
		}
		else
		{
			if (!shipPart.isIntact())
			{
				shot.setHit(HitType.HITAGAIN);
			}
			else
			{
				setHit(shipPart);
				shot.setHit(HitType.HIT);
				if(!shipPart.getShip().isIntact())
				{
					JOptionPane.showMessageDialog(mainFrame, shipPart.getShip().getName() +" wurde versenkt");
				}
			}
		}
		storeShot(shot);
		setChanged();
		notifyObservers();

		switch (shot.getHit())
		{
			case HIT:
			//aktiver Spieler darf noch einmal schieﬂen
			break;
			case NOHIT:
			case HITAGAIN:
				nextTurn();
			break;
		}
		fireAtWill();
	}

	public void nextTurn()
	{
		if (activePlayer == playerOne)
		{
			activePlayer = playerTwo;
		}
		else
		{
			activePlayer = playerOne;
		}

		setChanged();
		notifyObservers(getCurrentViewOfActivePlayer());
		mainFrame.showMessage(activePlayer.getName() + " ist nun am Zug!");
	}

	public Color[][] getCurrentViewOfActivePlayer() {
		
		Color[][] colors = new Color[activePlayer.getSeamap().getSize()][activePlayer.getSeamap().getSize()];
		int size = getActivePlayer().getSeamap().getSize();
		
		
		for(int x = 0;x<size;x++)
		{
			for(int y = 0;y<size;y++)
			{
				colors[x][y]=Color.BLUE;
			}
		}
		
		for (Shot s : getActivePlayer().getShots())
		{
			switch (s.getHit())
			{
				case HITAGAIN:
				case HIT:
					colors[s.getCoords().getX()][s.getCoords().getY()] = Color.GREEN;
				break;
				case NOHIT:
					colors[s.getCoords().getX()][s.getCoords().getY()] =Color.RED;
				break;
			case UNKNOWN:
				default:
					continue;
			}
		}
		
		return colors;
	}

	public Player getPlayerOne()
	{
		return playerOne;
	}

	public void setPlayerOne(Player playerOne)
	{
		this.playerOne = playerOne;
	}

	public Player getPlayerTwo()
	{
		return playerTwo;
	}

	public void setPlayerTwo(Player playerTwo)
	{
		this.playerTwo = playerTwo;
	}

	public Player getActivePlayer()
	{
		return activePlayer;
	}

	public Player getInactivePlayer()
	{
		if (activePlayer == playerOne)
		{
			return playerTwo;
		}
		return playerOne;

	}

	private void storeShot(Shot schuss)
	{

		activePlayer.addShot(schuss);
	}

	private void setHit(ShipPart shipPart)
	{
		shipPart.wasHit();
	}

	public boolean activePlayerHasThePermissionToStartTheApocalypse()
	{
		return fireAtWill;
	}

	private void ceaseFire()
	{
		fireAtWill = false;
	}

	private void fireAtWill()
	{
		fireAtWill = true;
	}

	public void startGame()
	{
		JOptionPane.showMessageDialog(mainFrame, "Dies ist das Seegebiet deines Gegners./nTreffer werden gr¸n dargestellt./nFehlsch¸sse rot.");
		nextTurn();
		fireAtWill();
	}

	public void putShipOnSeamap(Ship ship, Coords coords, Direction dir) {
		try {
			activePlayer.putShipOnSeamap(ship,coords,dir);
		} catch (IllegalShipPlacementException e) {
			JOptionPane.showMessageDialog(mainFrame, e.getMessage());
		}
	}

	public void showConfFrame() {
		
		this.confFrame = new ConfFrame(this, mainFrame);
	}
	
	
	private void createShipComponent(){
		Dimension size = new Dimension(500, 500);
		shipComponent = new ShipComponent(this, size );
		shipComponent.setLocation(3, 50);
	}

	public boolean bothPlayerPlacedTheirShips() {

		//TODO: Muss smarter gemacht werden 
		
		if(activePlayer == playerTwo)
		{
			return true;
		}
		return false;
	}
	
	private void createShipPlacementComponent()
	{
		this.shipPlacementComponent = new ShipPlacementComponent(this, new Dimension(800,800));
	}

	public void showShipPlacementFrame() {
		playerOne.setName(confFrame.getNameForPlayerOne());
		playerTwo.setName(confFrame.getNameForPlayerTwo());
		createShipPlacementComponent();
		this.shipPlacementFrame = new ShipPlacementFrame(mainFrame, shipPlacementComponent);
		
	}

}
