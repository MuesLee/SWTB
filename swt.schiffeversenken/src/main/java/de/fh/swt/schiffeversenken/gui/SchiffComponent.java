package de.fh.swt.schiffeversenken.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import de.fh.swt.schiffeversenken.controller.Spielleiter;
import de.fh.swt.schiffeversenken.data.Schiffsteil;
import de.fh.swt.schiffeversenken.data.Schuss;
import de.fh.swt.schiffeversenken.data.TrefferTyp;

public class SchiffComponent extends JComponent
{

	private static final long serialVersionUID = 1L;
	private Spielleiter spielleiter;
	private int zellGroesse = 30;
	private int platzZwischenZellen = 5;

	public SchiffComponent(Spielleiter spielleiter, Dimension size)
	{

		this.spielleiter = spielleiter;
		configure(size);
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		zellGroesse = (int) calculateCellSize(spielleiter.getSeekarte().getKoordinaten().length, spielleiter
			.getSeekarte().getKoordinaten().length);

		for (int i = 0; i < spielleiter.getSeekarte().getKoordinaten().length; i++)
		{
			for (int o = 0; o < spielleiter.getSeekarte().getKoordinaten().length; o++)
			{
				Schiffsteil schiffsteil = spielleiter.getSeekarte().getSchiffsteil(i, o);
				if (schiffsteil == null)
				{
					g.setColor(Color.BLUE);
				}

				else
				{

					if (schiffsteil.isIntakt())
					{
						g.setColor(Color.GREEN);
					}
					else
					{
						g.setColor(Color.RED);
					}
				}
				g.fillRect(i * (zellGroesse + platzZwischenZellen), o * (zellGroesse + platzZwischenZellen),
					zellGroesse, zellGroesse);
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
				//super.mouseClicked(e);
				int x = ((int) e.getLocationOnScreen().getX() - getLocationOnScreen().x) / zellGroesse;
				int y = ((int) e.getLocationOnScreen().getY() - getLocationOnScreen().y) / zellGroesse;

				System.out.println("x = " + x + " ..... y = " + y);

				setzeSchuss(x, y);

			}
		});
	}

	public void setzeSchuss(int x, int y)
	{
		spielleiter.verarbeiteSchuss(new Schuss(x, y, TrefferTyp.unbekannt));
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

		return Math.min(tHeight, tWidth);
	}

}
