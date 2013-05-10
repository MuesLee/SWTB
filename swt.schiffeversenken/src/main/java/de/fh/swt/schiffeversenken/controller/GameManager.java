package de.fh.swt.schiffeversenken.controller;

import java.awt.Color;
import java.util.Observable;

import javax.swing.JOptionPane;

import de.fh.swt.schiffeversenken.data.Coords;
import de.fh.swt.schiffeversenken.data.Direction;
import de.fh.swt.schiffeversenken.data.HitType;
import de.fh.swt.schiffeversenken.data.IllegalShipPlacementException;
import de.fh.swt.schiffeversenken.data.Player;
import de.fh.swt.schiffeversenken.data.Ship;
import de.fh.swt.schiffeversenken.data.ShipPart;
import de.fh.swt.schiffeversenken.data.Shot;
import de.fh.swt.schiffeversenken.gui.GUIStatusCode;
import de.fh.swt.schiffeversenken.gui.MainFrame;

public class GameManager extends Observable
{
	private static GameManager instance;
	private Player playerOne;
	private Player playerTwo;
	private Player activePlayer;
	private MainFrame mainFrame;

	private boolean fireAtWill = false;

	private GameManager()
	{
		initiate();
	}

	public static GameManager getInstance()
	{
		if (instance == null)
		{
			instance = new GameManager();
		}
		return instance;
	}

	private void initiate()
	{
		int seamapSize = 12;
		playerOne = new Player("Spieler 1", seamapSize);
		playerTwo = new Player("Spieler 2", seamapSize);
		activePlayer = playerOne;

	}

	public void startUp()
	{
		mainFrame = new MainFrame(this);
	}

	public HitType handleShot(Coords coords)
	{
		HitType hitType = HitType.UNKNOWN;

		ceaseFire();

		ShipPart shipPart = getInactivePlayersShipPartForCoords(coords);

		if ((shipPart == null))
		{
			hitType = HitType.NOHIT;
		}
		else
		{
			if (!shipPart.isIntact())
			{
				hitType = HitType.HITAGAIN;
			}
			else
			{
				setHit(shipPart);
				hitType = HitType.HIT;
				if (!shipPart.getShip().isIntact())
				{
					hitType = HitType.DESTROYED;
					if(activePlayer == playerOne)
					{
						JOptionPane.showMessageDialog(mainFrame.getFramePlayerOne(), shipPart.getShip().getName() + " wurde versenkt");
					}
					else{
						JOptionPane.showMessageDialog(mainFrame.getFramePlayerTwo(), shipPart.getShip().getName() + " wurde versenkt");   
					}
				}
			}

		}
		Shot shot = new Shot(coords, hitType);
		storeShot(shot);
		setChanged();
		notifyObservers(GUIStatusCode.DataHasChanged);

		if (!getInactivePlayer().isDefeated())
		{
			switch (shot.getHit())
			{
				case NOHIT:
				case HITAGAIN:
					nextTurn();
				break;
				case DESTROYED:
				case HIT:
				default:
				break;
			}
			fireAtWill();
		}
		else
		{
			endGame();
		}

		return hitType;
	}

	public int getActivePlayerID()
	{
		if (activePlayer == playerOne)
		{
			return 1;
		}
		return 2;
	}

	private ShipPart getInactivePlayersShipPartForCoords(Coords coords)
	{
		return getInactivePlayer().getSeamap().getShipPart(coords);
	}

	private void endGame()
	{
		mainFrame.showMessage("Herzlichen Glückwunsch " + activePlayer.getName() + ",\nDu hast gewonnen!");
		mainFrame.dispose();
		resetGame();
	}

	private void resetGame()
	{
		initiate();
		startUp();
	}

	public void nextTurn()
	{
		setChanged();
		if (activePlayer.equals(playerOne))
		{
			activePlayer = playerTwo;
			notifyObservers(GUIStatusCode.ItsPlayerTwosTurnNow);
		}
		else
		{
			activePlayer = playerOne;
			notifyObservers(GUIStatusCode.ItsPlayerOnesTurnNow);
		}
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
		notifyObservers(GUIStatusCode.DataHasChanged);
		nextTurn();
		fireAtWill();
	}

	public void putShipOnSeamap(Ship ship, Coords coords, Direction dir) throws IllegalShipPlacementException
	{
		activePlayer.putShipOnSeamap(ship, coords, dir);
		setChanged();
		notifyObservers(GUIStatusCode.DataHasChanged);

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
