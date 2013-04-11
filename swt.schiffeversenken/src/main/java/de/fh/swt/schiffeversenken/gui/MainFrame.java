package de.fh.swt.schiffeversenken.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import de.fh.swt.schiffeversenken.controller.GameManager;
import de.fh.swt.schiffeversenken.data.Coords;
import de.fh.swt.schiffeversenken.data.Direction;
import de.fh.swt.schiffeversenken.data.IllegalShipPlacementException;
import de.fh.swt.schiffeversenken.data.Ship;

public class MainFrame extends JFrame
{

	private JMenuBar jMenuBar = new JMenuBar();
	private GameManager gameManager;
	private ShipComponent shipComponent;
	private ShipPlacementFrame shipPlacementFrame;
	private ConfFrame confFrame;

	public MainFrame(GameManager gameManager)
	{
		super("Schiffe versenken");
		this.setGameManager(gameManager);
		configureFrame();
		configureShipComponent();
		configureMenu();
		configurePane();
		pack();
	}

	private void configureShipComponent()
	{
		this.shipComponent = new ShipComponent(gameManager, new Dimension(600, 600));
		shipComponent.setLocation(2, 50);

	}

	private void configurePane()
	{
		getContentPane().add(shipComponent);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(jMenuBar, BorderLayout.PAGE_START);
	}

	private void configureFrame()
	{
		setLayout(new BorderLayout());
		Dimension size = new Dimension(700, 700);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Schiffe versenken");
		setLocationRelativeTo(null);
		setSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		setResizable(false);
		setVisible(true);
	}

	public void showMessage(String message)
	{
		JOptionPane.showMessageDialog(this, message);
	}

	private void configureMenu()
	{
		final MainFrame frame = this;
		JMenu jMenu = new JMenu("Datei");
		JMenuItem menuItemStart = new JMenuItem("Start");
		menuItemStart.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				showConfFrame();
			}
		});
		JMenuItem menuItemBeenden = new JMenuItem("Beenden");
		menuItemBeenden.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		jMenu.add(menuItemStart);
		jMenu.add(menuItemBeenden);
		jMenuBar.add(jMenu);
		jMenuBar.setVisible(true);
	}

	public void showConfFrame()
	{
		this.confFrame = new ConfFrame(gameManager, this);
	}

	public GameManager getGameManager()
	{
		return gameManager;
	}

	public void setGameManager(GameManager gameManager)
	{
		this.gameManager = gameManager;
	}

	public void putShipOnSeamap(Ship ship, Coords coords, Direction dir) throws IllegalShipPlacementException
	{
		gameManager.putShipOnSeamap(ship, coords, dir);
	}

	public void showShipPlacementFrame()
	{
		gameManager.getPlayerOne().setName(confFrame.getNameForPlayerOne());
		gameManager.getPlayerTwo().setName(confFrame.getNameForPlayerTwo());
		this.shipPlacementFrame = new ShipPlacementFrame(this);
	}

	public ShipPlacementFrame getShipPlacementFrame()
	{
		return shipPlacementFrame;
	}

}
