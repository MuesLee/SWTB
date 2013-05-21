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
	private SeamapPanel seamapComponentOne;
	private ShipPlacementFrame shipPlacementFrame;
	private ConfigurationFrame confFrame;
	private InfoPanel infoPanel;
	private SeamapFrame framePlayerOne;
	private SeamapFrame framePlayerTwo;
	private SeamapPanel seamapComponentTwo;

	public MainFrame(GameManager gameManager)
	{
		super(Messages.getString("MainFrame.FrameTitle")); //$NON-NLS-1$
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

	private void configureSeamapFrames()
	{
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
		this.seamapComponentOne = new SeamapPanel(1, gameManager, size);
		this.seamapComponentTwo = new SeamapPanel(2, gameManager, size);

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
		double screenSizeFactor = 0.4;
		Dimension size = GUIHelper.getProperSizeRelativeToScreensize(screenSizeFactor);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public SeamapPanel getSeamapComponentOne()
	{
		return seamapComponentOne;
	}

	public void setSeamapComponentOne(SeamapPanel seamapComponentOne)
	{
		this.seamapComponentOne = seamapComponentOne;
	}

	public SeamapFrame getFramePlayerOne()
	{
		return framePlayerOne;
	}

	public void setFramePlayerOne(SeamapFrame framePlayerOne)
	{
		this.framePlayerOne = framePlayerOne;
	}

	public SeamapFrame getFramePlayerTwo()
	{
		return framePlayerTwo;
	}

	public void setFramePlayerTwo(SeamapFrame framePlayerTwo)
	{
		this.framePlayerTwo = framePlayerTwo;
	}

	public SeamapPanel getSeamapComponentTwo()
	{
		return seamapComponentTwo;
	}

	public void setSeamapComponentTwo(SeamapPanel seamapComponentTwo)
	{
		this.seamapComponentTwo = seamapComponentTwo;
	}

	public void showMessage(String message)
	{
		JOptionPane.showMessageDialog(this, message);
	}

	private void configureMenu()
	{
		final MainFrame frame = this;
		JMenu jMenu = new JMenu(Messages.getString("MainFrame.MenuData")); //$NON-NLS-1$
		JMenuItem menuItemStart = new JMenuItem(Messages.getString("MainFrame.MenuStart")); //$NON-NLS-1$
		menuItemStart.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				showConfFrame();

			}
		});
		JMenuItem menuItemBeenden = new JMenuItem(Messages.getString("MainFrame.MenuExit")); //$NON-NLS-1$
		menuItemBeenden.addActionListener(new ActionListener()
		{

			@Override
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

	public void showSeamapFrames()
	{
		framePlayerOne.setTitle(gameManager.getPlayerOne().getName());
		framePlayerTwo.setTitle(gameManager.getPlayerTwo().getName());
		framePlayerOne.setVisible(true);
		framePlayerTwo.setVisible(true);
	}

	public ShipPlacementFrame getShipPlacementFrame()
	{
		return shipPlacementFrame;
	}

}
