package de.fh.swt.schiffeversenken.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import de.fh.swt.schiffeversenken.controller.GameManager;
import de.fh.swt.schiffeversenken.data.Coords;
import de.fh.swt.schiffeversenken.data.Direction;
import de.fh.swt.schiffeversenken.data.IllegalShipPlacementException;
import de.fh.swt.schiffeversenken.data.Ship;

public class ShipPlacementComponent extends JComponent implements Observer
{

	private static final long serialVersionUID = 1L;
	private GameManager gameManager;
	private int cellSize = 30;
	private int spaceBetweenCells = 5;
	private int seamapSize = 12;
	private Color[][] currentViewOfPlayer;
	private ShipPlacementFrame shipPlacementFrame;

	//setzt alle nötigen Komponenten für die ShipPlacementComponent
	public ShipPlacementComponent(ShipPlacementFrame shipPlacementFrame, GameManager gameManager, Dimension size)
	{
		this.gameManager = gameManager;
		this.gameManager.addObserver(this);
		this.shipPlacementFrame = shipPlacementFrame;

		currentViewOfPlayer = gameManager.getViewOfCurrrentPlayersOwnShips();
		seamapSize = currentViewOfPlayer.length;
		configure(size);
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		cellSize = (int) calculateCellSize(seamapSize, seamapSize);

		for (int x = 0; x < seamapSize; x++)
		{
			for (int y = 0; y < seamapSize; y++)
			{
				g.setColor(currentViewOfPlayer[x][y]);
				g.fillRect(x * (cellSize + spaceBetweenCells), y * (cellSize + spaceBetweenCells), cellSize, cellSize);

			}
		}

	}

	//gibt die GUI zurück, welche zum Setzen der Schiffe fungiert
	private Color[][] getViewOfPlayer()
	{
		return gameManager.getViewOfCurrrentPlayersOwnShips();
	}

	//Konfigurieren der GUI zum Schiffe setzen
	private void configure(Dimension size)
	{
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);

		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				super.mouseClicked(e);
				int x = ((int) e.getLocationOnScreen().getX() - getLocationOnScreen().x)
					/ (cellSize + spaceBetweenCells);
				int y = ((int) e.getLocationOnScreen().getY() - getLocationOnScreen().y)
					/ (cellSize + spaceBetweenCells);

				placeShip(x, y);
			}
		});
	}

	//Setzen der Schiffe in richtiger Richtung(wenn möglich)
	public void placeShip(int x, int y)
	{
		Ship ship = null;

		try
		{
			ship = (Ship) getShipPlacementFrame().getShipBox().getSelectedItem();
		}
		catch (NullPointerException e)
		{
			JOptionPane.showMessageDialog(this, Messages.getString("ShipPlacementComponent.ErrorTextAllShipsPlaced")); //$NON-NLS-1$
			GameManager.getLogger().info("All ships already placed");
		}
		Direction dir = null;

		if (getShipPlacementFrame().getDown().isSelected())
		{
			dir = Direction.DOWN;
		}
		else
		{
			dir = Direction.RIGHT;
		}

		try
		{
			gameManager.putShipOnSeamap(ship, new Coords(x, y), dir);
			getShipPlacementFrame().getShipBox().removeItem(getShipPlacementFrame().getShipBox().getSelectedItem());
		}
		catch (IllegalShipPlacementException e)
		{
			JOptionPane.showMessageDialog(getShipPlacementFrame(), e.getMessage());
		}

	}

	//Größe der einzelnen Felder bestimmen
	private double calculateCellSize(double height, double width)
	{
		double tWidth = this.getWidth() / width;
		double tHeight = this.getHeight() / height;

		if (tHeight < 1)
		{
			tHeight = 1;
		}
		if (tWidth < 1)
		{
			tWidth = 1;
		}

		return (Math.min(tHeight, tWidth)) - spaceBetweenCells;
	}

	//gibt ShipPlacementFrame zurück
	public ShipPlacementFrame getShipPlacementFrame()
	{
		return shipPlacementFrame;
	}

	//Neu zeichnen der Oberfläche
	@Override
	public void update(Observable o, Object arg)
	{
		currentViewOfPlayer = getViewOfPlayer();

		repaint();
	}

}
