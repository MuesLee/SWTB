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

/* 
 * Implements the seamap in both ship-placing and shot-handling.
 * Type defines the type of the seamap.
 * false = ship placement
 * true = sea map (shots etc.)
 */
public class SeamapComponent extends JComponent implements Observer
{

	private static final long serialVersionUID = 1L;
	private GameManager gameManager;
	private int cellSize = 30;
	private int spaceBetweenCells = 5;
	private int seamapSize = 12;
	private Color[][] currentViewOfPlayer;
	private ShipPlacementFrame shipPlacementFrame;
	private boolean isPlayerOne;

	public boolean isPlayerOne() {
		return isPlayerOne;
	}

	public void setPlayerOne(boolean isPlayerOne) {
		this.isPlayerOne = isPlayerOne;
	}

	public SeamapComponent(boolean isPlayerOne, ShipPlacementFrame shipPlacementFrame, GameManager gameManager, Dimension size, boolean type)
	{

		this.gameManager = gameManager;
		this.gameManager.addObserver(this);
		this.shipPlacementFrame = shipPlacementFrame;
		
		currentViewOfPlayer = gameManager.getCurrentViewOfPlayerOne();
		seamapSize = currentViewOfPlayer.length;
		configure(size, type);
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
	
	private Color[][] getViewOfPlayer()
	{
		if(isPlayerOne)
		{
			return gameManager.getCurrentViewOfPlayerOne();
		}
		return gameManager.getCurrentViewOfPlayerTwo();
	}

	private void configure(Dimension size, final boolean type)
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

						if (gameManager.activePlayerHasThePermissionToStartTheApocalypse())
						{
						makeShot(x, y);
						}
					}});
		}

	public void makeShot(int x, int y)
	{
		gameManager.handleShot(new Coords(x, y));
	}
	

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

	public ShipPlacementFrame getShipPlacementFrame()
	{
		return shipPlacementFrame;
	}
	
	public void update(Observable o, Object arg)
	{
			currentViewOfPlayer = getViewOfPlayer();
			
		repaint();
	}

}
