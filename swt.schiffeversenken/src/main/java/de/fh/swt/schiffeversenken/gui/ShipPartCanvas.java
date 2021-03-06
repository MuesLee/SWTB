package de.fh.swt.schiffeversenken.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import de.fh.swt.schiffeversenken.controller.GameManager;
import de.fh.swt.schiffeversenken.data.Coords;
import de.fh.swt.schiffeversenken.data.HitType;

public class ShipPartCanvas extends Canvas

{
	private Coords coords;
	private Dimension size;
	private SeamapPanel seamapComponent;

	//setzt alle n�tigen Komponenten f�r die ShipParts
	public ShipPartCanvas(SeamapPanel seamapComponent, Coords coords, Dimension size)
	{
		this.size = size;
		this.setCoords(coords);
		this.seamapComponent = seamapComponent;
		configure();
		setVisible(true);
	}

	//Konfigurieren des Grids(blau f�rben etc.)
	private void configure()
	{
		setSize(size);
		setPreferredSize(size);
		setBackground(Color.BLUE);

		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (seamapComponent.isClickable())
				{
					setColor(getColorForHitType(seamapComponent.handleShot(coords)));
				}
				else
				{
					JOptionPane.showMessageDialog(getParent(), Messages.getString("ShipPartCanvas.ErrorTextNotUrTurnFucker")); //$NON-NLS-1$
					GameManager.getLogger().info("Not players turn");
				}
			}
		});
	}

	//Je nach HitType wird eine andere Farbe f�r das Grid zur�ckgegeben
	private Color getColorForHitType(HitType hitType)
	{
		Color color;

		switch (hitType)
		{
			case DESTROYED:
				color = Color.BLACK;
			break;
			case HIT:
				color = Color.GREEN;
			break;
			case HITAGAIN:
				color = Color.GREEN;
			break;
			case NOHIT:
				color = Color.RED;
			break;
			default:
				color = Color.blue;
			break;
		}
		return color;
	}

	//Farben setzen
	public void setColor(Color color)
	{
		setBackground(color);
		repaint();
	}

	//getter- und setter-Methoden f�r die Koordinaten
	public Coords getCoords()
	{
		return coords;
	}

	public void setCoords(Coords coords)
	{
		this.coords = coords;
	}
}
