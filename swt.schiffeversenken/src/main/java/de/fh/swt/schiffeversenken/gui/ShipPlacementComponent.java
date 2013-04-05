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
	private ShipPlacementFrame shipPlacementFrame;

	public ShipPlacementComponent(ShipPlacementFrame shipPlacementFrame, GameManager gameManager, Dimension size)
	{
		this.shipPlacementFrame = shipPlacementFrame;
		this.gameManager = gameManager;
		configure(size);
		seamapSize = gameManager.getActivePlayer().getSeamap().getShipParts().length;
		this.gameManager.addObserver(this);
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

				if (gameManager.getActivePlayer().getSeamap().getShipParts()[x][y] == null)
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

					System.out.println(gameManager.getActivePlayer().getSeamap().toString());

				}

			}
		});
	}

	public void placeShip(int x, int y)
	{
		Ship ship = null;

		try
		{
			ship = (Ship) shipPlacementFrame.getShipBox().getSelectedItem();
			Direction dir = null;

			if (shipPlacementFrame.getDown().isSelected())
			{
				dir = Direction.DOWN;
			}
			else
			{
				dir = Direction.RIGHT;
			}

			try
			{
				gameManager.getActivePlayer().getSeamap().putShipOnSeamap(ship, new Coords(x, y), dir);
				shipPlacementFrame.getShipBox().removeItem(shipPlacementFrame.getShipBox().getSelectedItem());
			}
			catch (IllegalShipPlacementException e)
			{
				JOptionPane.showMessageDialog(shipPlacementFrame, e.getMessage());
			}
		}
		catch (NullPointerException e)
		{
			JOptionPane.showMessageDialog(this, gameManager.getActivePlayer() + " hat bereits alle Schiffe platziert");
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
	{
		repaint();
	}

}
