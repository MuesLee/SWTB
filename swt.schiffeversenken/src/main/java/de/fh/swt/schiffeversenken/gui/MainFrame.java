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

public class MainFrame extends JFrame
{

	private JMenuBar jMenuBar = new JMenuBar();
	private GameManager gameManager;
	private ConfFrame confFrame;
	private ShipComponent schiffComp;
	private ShipPlacementFrame shipPlacementFrame;

	public MainFrame(GameManager spielleiter)
	{
		super("Schiffe versenken");
		this.setGameManager(spielleiter);
		configureFrame();
		configureMenu();
		configurePane();
		configureConfFrame();
		pack();
	}

	private void configurePane()
	{
		schiffComp = new ShipComponent(getGameManager(), new Dimension(600, 600));
		getContentPane().add(schiffComp);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(jMenuBar, BorderLayout.PAGE_START);
		schiffComp.setLocation(5, 40);
	}

	private void configureConfFrame()
	{
		confFrame = new ConfFrame(getGameManager(), this);
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
				confFrame.setVisible(true);
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

	public GameManager getGameManager()
	{
		return gameManager;
	}

	public void setGameManager(GameManager gameManager)
	{
		this.gameManager = gameManager;
	}

}
