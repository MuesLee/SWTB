package de.fh.swt.schiffeversenken.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import de.fh.swt.schiffeversenken.controller.GameManager;

public class MainFrame extends JFrame
{

	private JMenuBar jMenuBar = new JMenuBar();
	private GameManager gameManager;
	private SeamapComponent seamapComponentOne;
	private ShipPlacementFrame shipPlacementFrame;
	private ConfigurationFrame confFrame;
	private InfoPanel infoPanel;
	private SeamapFrame framePlayerOne;
	private SeamapFrame framePlayerTwo;
	private SeamapComponent seamapComponentTwo;

	public MainFrame(GameManager gameManager)
	{
		super("Schiffe versenken");
		this.setGameManager(gameManager);
		configure();
	}

	private void configure()
	{
		configureFrame();
		configureSeamapComponents();
		configureMenu();
		configureInfoPanel();
		configureSeamapFrames();
		configurePane();
		pack();
	}

	private void configureSeamapFrames() {
		framePlayerOne = new SeamapFrame(gameManager, seamapComponentOne, 1);
		framePlayerTwo = new SeamapFrame(gameManager, seamapComponentTwo, 2);
	}

	private void configureInfoPanel()
	{
		infoPanel = new InfoPanel();
	}

	private void configureSeamapComponents()
	{
		double screenSizeFactor = 0.8;
		Dimension size = GUIHelper.getProperSizeRelativeToScreensize(screenSizeFactor);
		this.seamapComponentOne = new SeamapComponent(true,null, gameManager, size, true);
		this.seamapComponentTwo= new SeamapComponent(false, null, gameManager, size, true);
		seamapComponentOne.setVisible(false);
		seamapComponentTwo.setVisible(false);

	}

	private void configurePane()
	{
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.setBackground(Color.WHITE);
		contentPane.add(jMenuBar, BorderLayout.PAGE_START);
		contentPane.add(infoPanel, BorderLayout.EAST);
	}

	private void configureFrame()
	{
		setLayout(new BorderLayout());
		double screenSizeFactor = 0.6;
		Dimension size = GUIHelper.getProperSizeRelativeToScreensize(screenSizeFactor);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Schiffe versenken");
		setSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		setResizable(true);
		setLocationRelativeTo(null);
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
				showShipComponent();
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
		this.confFrame = new ConfigurationFrame(this);
	}

	public GameManager getGameManager()
	{
		return gameManager;
	}

	public void setGameManager(GameManager gameManager)
	{
		this.gameManager = gameManager;
	}

	public void showShipPlacementFrame()
	{
		gameManager.getPlayerOne().setName(confFrame.getNameForPlayerOne());
		gameManager.getPlayerTwo().setName(confFrame.getNameForPlayerTwo());
		this.shipPlacementFrame = new ShipPlacementFrame(this);
	}

	public void showShipComponent()
	{
		seamapComponentOne.setVisible(true);
	}

	public ShipPlacementFrame getShipPlacementFrame()
	{
		return shipPlacementFrame;
	}

}
