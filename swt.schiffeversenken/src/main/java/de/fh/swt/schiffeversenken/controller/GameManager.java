package de.fh.swt.schiffeversenken.controller;

import java.awt.Color;
import java.util.Observable;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import de.fh.swt.schiffeversenken.gui.Messages;

public class GameManager<playerTwo> extends Observable
{
	private static GameManager instance;
	private static Logger logger = LoggerFactory.getLogger(GameManager.class);
	private static AudioController audioController;
	private Player playerOne;
	private Player playerTwo;
	private Player activePlayer;
	private MainFrame mainFrame;

	private boolean fireAtWill = false;
	private Properties prop;

	//Initiierungsvorgang starten
	private GameManager()
	{
		audioController = new AudioController();
		initiate();

	}

	//GameManager Instanz sicherstellen
	public static GameManager getInstance()
	{
		if (instance == null)
		{
			instance = new GameManager();
		}
		return instance;
	}

	public static Logger getLogger()
	{
		return logger;
	}

	//Seekartengröße setzen und Spieler initiieren
	private void initiate()
	{
		int seamapSize = 12;
		playerOne = new Player(Messages.getString("GameManager.NamePlayer1Default"), seamapSize); //$NON-NLS-1$
		playerTwo = new Player(Messages.getString("GameManager.NamePlayer2Default"), seamapSize); //$NON-NLS-1$
		activePlayer = playerOne;

	}

	public void startUp()
	{
		mainFrame = new MainFrame(this);
	}

	//HitType setzen (kein Treffer, Treffer und zerstört, Treffer und nicht zerstört) und prüfen, ob gegnerischer Spieler noch intakte Schiffe besitzt
	public HitType handleShot(Coords coords)
	{
		HitType hitType = HitType.UNKNOWN;

		ceaseFire();

		ShipPart shipPart = getInactivePlayersShipPartForCoords(coords);

		if ((shipPart == null))
		{
			hitType = HitType.NOHIT;
			audioController.playSound("shot_nohit");
			logger.info("No ship was hit");
		}
		else
		{
			if (!shipPart.isIntact())
			{
				hitType = HitType.HITAGAIN;
				audioController.playSound("comment_wasted");
				logger.info("A part of a {} was hit again", shipPart.getShip().getName());
			}
			else
			{
				audioController.playSound("shot_hit");
				setHit(shipPart);
				hitType = HitType.HIT;
				
				logger.info("A part of a {} was hit", shipPart.getShip().getName());
				if (!shipPart.getShip().isIntact())
				{
					audioController.playSound("comment_terminated");
					hitType = HitType.DESTROYED;
					if (activePlayer == playerOne)
					{
						JOptionPane.showMessageDialog(mainFrame.getFramePlayerOne(), shipPart.getShip().getName()
							+ Messages.getString("GameManager.InfoTextHasbeenDestroyed")); //$NON-NLS-1$
						logger.info("{} of Player 2 was destroyed.", shipPart.getShip().getName());
					}
					else
					{
						JOptionPane.showMessageDialog(mainFrame.getFramePlayerTwo(), shipPart.getShip().getName()
							+ Messages.getString("GameManager.InfoTextHasbeenDestroyed")); //$NON-NLS-1$
						logger.info("{} of Player 1 was destroyed.", shipPart.getShip().getName());
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
			audioController.playSound("applause");
			audioController.playSound("comment_hail");
			endGame();
		}

		return hitType;
	}

	//aktiven Spieler festlegen
	public int getActivePlayerID()
	{
		if (activePlayer == playerOne)
		{
			return 1;
		}
		return 2;
	}

	//Koordinaten des beschossenen Schiffteils des inaktiven Spielers bestimmen
	private ShipPart getInactivePlayersShipPartForCoords(Coords coords)
	{
		return getInactivePlayer().getSeamap().getShipPart(coords);
	}

	//Spiel beenden
	private void endGame()
	{
		mainFrame
			.showMessage(Messages.getString("GameManager.InfoTextCongratulationsBitch") + activePlayer.getName() + Messages.getString("GameManager.InfoTextUWon")); //$NON-NLS-1$ //$NON-NLS-2$
		mainFrame.dispose();
		resetGame();
		logger.info("Game ended");
	}

	//Spiel zurücksetzen
	private void resetGame()
	{
		initiate();
		startUp();
	}

	//nächsten Zug starten und dabei aktiven Spieler benennen
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
		logger.info("Next Turn");
	}

	//getter- und setter-Methoden zu den Spielern(inkl. in/aktiver Spieler)
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

	//abgegebenen Schuss speichern
	private void storeShot(Shot schuss)
	{

		activePlayer.addShot(schuss);
	}

	//Schuss setzen
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

	//Spiel starten
	public void startGame()
	{
		
		setChanged();
		notifyObservers(GUIStatusCode.DataHasChanged);
		nextTurn();
		fireAtWill();
		logger.info("Game started");
	}

	//Schiffe setzen(Angabe des Schiffs, der Koordinaten, der Richtung)
	public void putShipOnSeamap(Ship ship, Coords coords, Direction dir) throws IllegalShipPlacementException
	{
		activePlayer.putShipOnSeamap(ship, coords, dir);
		setChanged();
		notifyObservers(GUIStatusCode.DataHasChanged);

	}

	//Haben beide Spieler ihre Schiffe platziert?
	public boolean bothPlayerPlacedTheirShips()
	{

		if (activePlayer == playerTwo)
		{
			audioController.playSound("comment_ready");
			return true;
		}
		return false;
	}

	//Sicht der eigenen Schiffe ausgeben(je nach Zustand der Schiffsteile eine unterschiedliche Farbe)
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

	public AudioController getAudioController() {
		return audioController;
	}
}
