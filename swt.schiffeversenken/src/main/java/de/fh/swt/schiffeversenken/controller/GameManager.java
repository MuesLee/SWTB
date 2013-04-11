package de.fh.swt.schiffeversenken.controller;

import java.awt.Color;
import java.util.Observable;

import de.fh.swt.schiffeversenken.data.Coords;
import de.fh.swt.schiffeversenken.data.Direction;
import de.fh.swt.schiffeversenken.data.HitType;
import de.fh.swt.schiffeversenken.data.IllegalShipPlacementException;
import de.fh.swt.schiffeversenken.data.Ship;
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
		initiate();
	}

	private void initiate()
	{
		playerOne = new Player("Spieler 1", 12);
		playerTwo = new Player("Spieler 2", 12);
		activePlayer = playerOne;

	}

	public void start()
	{
		mainFrame = new MainFrame(this);
	}

	public void handleShot(Coords coords)
	{
		Shot shot = new Shot(coords, HitType.UNKNOWN);

		ceaseFire();

		ShipPart shipPart = getInactivePlayersShipPartForCoords(coords);

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
				if (!shipPart.getShip().isIntact())
				{
					mainFrame.showMessage(shipPart.getShip().getName() + " wurde versenkt");
				}
			}

		}
		storeShot(shot);
		setChanged();
		notifyObservers();

		if (!getInactivePlayer().isDefeated())
		{
			switch (shot.getHit())
			{
				case NOHIT:
				case HITAGAIN:
					nextTurn();
				break;
				case HIT:
					//aktiver Spieler darf noch einmal schießen
				default:
				break;
			}
			fireAtWill();
		}
		else
		{
			endGame();
		}
	}

	private ShipPart getInactivePlayersShipPartForCoords(Coords coords)
	{
		return getInactivePlayer().getSeamap().getShipPart(coords);
	}

	private void endGame()
	{
		mainFrame.showMessage("Herzlichen Glückwunsch " + activePlayer.getName() + ",\nDu hast gewonnen!");
		resetGame();
	}

	private void resetGame()
	{
		initiate();
		start();
	}

	public void nextTurn()
	{
		if (activePlayer.equals(playerOne))
		{
			activePlayer = playerTwo;
		}
		else
		{
			activePlayer = playerOne;
		}
		setChanged();
		notifyObservers();
		getMainFrame().showMessage(activePlayer.getName() + " ist nun am Zug!");
	}

	public Color[][] getCurrentViewOfActivePlayer()
	{

		Color[][] colors = new Color[activePlayer.getSeamap().getSize()][activePlayer.getSeamap().getSize()];
		int size = getActivePlayer().getSeamap().getSize();

		for (int x = 0; x < size; x++)
		{
			for (int y = 0; y < size; y++)
			{
				colors[x][y] = Color.BLUE;
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
					colors[s.getCoords().getX()][s.getCoords().getY()] = Color.RED;
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
		setChanged();
		notifyObservers();
		mainFrame
			.showMessage("Dies ist das Seegebiet deines Gegners.\nTreffer werden grün dargestellt.\nFehlschüsse rot.");
		nextTurn();
		fireAtWill();
	}

	public void putShipOnSeamap(Ship ship, Coords coords, Direction dir) throws IllegalShipPlacementException
	{
		activePlayer.putShipOnSeamap(ship, coords, dir);
		setChanged();
		notifyObservers();

	}

	public boolean bothPlayerPlacedTheirShips()
	{

		//TODO: Muss smarter gemacht werden 

		if (activePlayer == playerTwo)
		{
			return true;
		}
		return false;
	}

	public Color[][] getViewOfCurrrentPlayersOwnShips()
	{
		ShipPart[][] shipParts = activePlayer.getSeamap().getShipParts();
		int seamapSize = shipParts.length;

		Color[][] colors = new Color[seamapSize][seamapSize];

		for (int x = 0; x < seamapSize; x++)
		{

			for (int y = 0; y < seamapSize; y++)
			{

				if (shipParts[x][y] == null)
				{
					colors[x][y] = Color.blue;
				}
				else
				{
					if (shipParts[x][y].isIntact())
					{
						colors[x][y] = Color.GREEN;
					}
					else
					{
						colors[x][y] = Color.RED;
					}
				}
			}
		}
		return colors;
	}

	public MainFrame getMainFrame()
	{
		return mainFrame;
	}

}
