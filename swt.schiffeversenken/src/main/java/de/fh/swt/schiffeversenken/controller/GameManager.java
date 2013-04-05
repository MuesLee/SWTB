package de.fh.swt.schiffeversenken.controller;

import java.util.Observable;

import de.fh.swt.schiffeversenken.data.HitType;
import de.fh.swt.schiffeversenken.data.ShipPart;
import de.fh.swt.schiffeversenken.data.Shot;
import de.fh.swt.schiffeversenken.gui.MainFrame;

public class GameManager extends Observable
{

	private Player playerOne;
	private Player playerTwo;
	private Player activePlayer;
	private MainFrame mainFrame;
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
	{
		mainFrame = new MainFrame(this);
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
		notifyObservers();
		mainFrame.showMessage(activePlayer.getName() + " ist nun am Zug!");
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

	}

}
