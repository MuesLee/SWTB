package de.fh.swt.schiffeversenken.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

import de.fh.swt.schiffeversenken.controller.GameManager;
import de.fh.swt.schiffeversenken.data.Coords;

public class SeamapComponent extends JComponent implements Observer
{

	private static final long serialVersionUID = 1L;
	private GameManager gameManager;
	private int cellSize = 30;
	private int spaceBetweenCells = 5;
	private int seamapSize = 12;
	private Color[][] currentViewOfActivePlayer;

	public SeamapComponent(GameManager gameManager, Dimension size)
	{

		this.gameManager = gameManager;
		this.gameManager.addObserver(this);
		currentViewOfActivePlayer = gameManager.getCurrentViewOfActivePlayer();
		seamapSize = currentViewOfActivePlayer.length;
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

				g.setColor(currentViewOfActivePlayer[x][y]);
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

				if (gameManager.activePlayerHasThePermissionToStartTheApocalypse())
				{
					super.mouseClicked(e);
					int x = ((int) e.getLocationOnScreen().getX() - getLocationOnScreen().x)
						/ (cellSize + spaceBetweenCells);
					int y = ((int) e.getLocationOnScreen().getY() - getLocationOnScreen().y)
						/ (cellSize + spaceBetweenCells);

					makeShot(x, y);
				}

			}
		});
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

	public void update(Observable o, Object arg)
	{
		currentViewOfActivePlayer = gameManager.getCurrentViewOfActivePlayer();
		repaint();
	}

}
