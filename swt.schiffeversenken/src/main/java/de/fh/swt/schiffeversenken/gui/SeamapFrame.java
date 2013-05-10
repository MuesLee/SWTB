package de.fh.swt.schiffeversenken.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import de.fh.swt.schiffeversenken.controller.GameManager;

public class SeamapFrame extends JFrame
{

	private GameManager gameManager;
	private SeamapPanel seamapPanel;
	private int playerID;

	public SeamapFrame(GameManager gameManager, SeamapPanel seamapComponent, int playerID)
	{
		super("Spieler " + playerID);
		this.gameManager = gameManager;
		this.playerID = playerID;
		this.seamapPanel = seamapComponent;
		configure();
	}

	private void configure()
	{
		double screenSizeFactor = 0.8;
		Dimension size = GUIHelper.getProperSizeRelativeToScreensize(screenSizeFactor);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		setLocation((int) (((playerID - 1) * size.getWidth())), 100);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(seamapPanel);
		pack();
		setVisible(true);
	}

}
