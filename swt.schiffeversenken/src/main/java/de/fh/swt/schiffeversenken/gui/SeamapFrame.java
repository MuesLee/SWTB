package de.fh.swt.schiffeversenken.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.fh.swt.schiffeversenken.controller.GameManager;

public class SeamapFrame extends JFrame implements Observer {

	private GameManager gameManager;
	private SeamapComponent seamapComponent;
	private int playerID;
	private boolean hasTurn;

	public SeamapFrame(GameManager gameManager,
			SeamapComponent seamapComponent, int playerID) {
		super("Spieler " + playerID);
		this.gameManager = gameManager;
		this.playerID = playerID;
		this.seamapComponent = seamapComponent;
		configure();
	}

	private void configure() {
		double screenSizeFactor = 0.4;
		Dimension size = GUIHelper.getProperSizeRelativeToScreensize(screenSizeFactor);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		setLocation((int)( (playerID-1)*size.getWidth()+100), 100);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(seamapComponent);
		gameManager.addObserver(this);
		pack();

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!hasTurn)
				JOptionPane.showMessageDialog(e.getComponent(),
							"Sie sind nicht am Zug!");
				}
		});
		setVisible(true);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(!(arg1 == null))
		{
			hasTurn = (int)arg1==playerID;
		}
	}

}
