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
import de.fh.swt.schiffeversenken.data.Seamap;
import de.fh.swt.schiffeversenken.data.Ship;
import de.fh.swt.schiffeversenken.data.ShipPart;

public class ShipPlacementComponent extends JComponent implements Observer
{

	private static final long serialVersionUID = 1L;
	private int cellSize = 30;
	private int spaceBetweenCells = 5;
	private int seamapSize = 12;
	private ShipPart[][] shipParts;
	private GameManager gameManager;
	
	public ShipPlacementComponent(GameManager gameManager, Dimension size)
	{   
		this.gameManager = gameManager;
		this.shipParts = gameManager.getPlayerOne().getSeamap().getShipParts();
		seamapSize = shipParts.length;
		configure(size);
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		cellSize = (int) calculateCellSize(seamapSize, seamapSize);
		System.out.println("CellSize " + cellSize);

		
		for (int x = 0; x < seamapSize; x++)
		{

			for (int y = 0; y < seamapSize; y++)
			{
				
			
				if (shipParts[x][y] == null)
				{
					g.setColor(Color.blue);
				}
				else
				{
					g.setColor(Color.GREEN);
				}
				g.fillRect(x * (cellSize + spaceBetweenCells), y * (cellSize + spaceBetweenCells), cellSize, cellSize);
			}
		}

	}

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

				{
					super.mouseClicked(e);
					int x = ((int) e.getLocationOnScreen().getX() - getLocationOnScreen().x) / cellSize;
					int y = ((int) e.getLocationOnScreen().getY() - getLocationOnScreen().y) / cellSize;

					System.out.println("SHIPLACEMENT: x = " + x + " ..... y = " + y);

					placeShip(x, y);
				}

			}
		});
	}

	public void placeShip(int x, int y)
	{
		Ship ship = null;
		ShipPlacementFrame shipPlacementFrame = gameManager.getShipPlacementFrame();
		
		try
		{
			ship = (Ship) shipPlacementFrame .getShipBox().getSelectedItem();
			Direction dir = null;
			
			if (shipPlacementFrame.getDown().isSelected())
			{
				dir = Direction.DOWN;
			}
			else
			{
				dir = Direction.RIGHT;
			}

			shipPlacementFrame.putShipOnSeamap(ship, new Coords(x, y), dir);
			shipPlacementFrame.getShipBox().removeItem(shipPlacementFrame.getShipBox().getSelectedItem());
		}
		catch (NullPointerException e)
		{	
			JOptionPane.showMessageDialog(this, "Dieser Spieler hat bereits alle Schiffe platziert");
		}
	}

	private double calculateCellSize(double height, double width)
	{
		double tWidth = (this.getWidth() / width) * 0.8;
		double tHeight = (this.getHeight() / height) * 0.8;

		if (tHeight < 1)
		{
			tHeight = 1;
		}
		if (tWidth < 1)
		{
			tWidth = 1;
		}

		return Math.min(tHeight, tWidth);
	}

	public void update(Observable o, Object arg)
	{	this.shipParts = (ShipPart[][]) arg;
		repaint();
	}
	


}
