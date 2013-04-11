package de.fh.swt.schiffeversenken.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import de.fh.swt.schiffeversenken.data.Coords;
import de.fh.swt.schiffeversenken.data.Direction;
import de.fh.swt.schiffeversenken.data.IllegalShipPlacementException;
import de.fh.swt.schiffeversenken.data.Ship;

public class ShipPlacementFrame extends JFrame

{

	private MainFrame mainFrame;
	private ShipPlacementComponent shipPlacementComponent;

	private JRadioButton down;
	private JRadioButton right;
	private ButtonGroup buttonGroup;
	private JButton start;
	private JComboBox shipBox;

	public MainFrame getMainFrame()
	{
		return mainFrame;
	}

	public ShipPlacementComponent getShipPlacementComponent()
	{
		return shipPlacementComponent;
	}

	public JRadioButton getDown()
	{
		return down;
	}

	public JRadioButton getRight()
	{
		return right;
	}

	public ButtonGroup getButtonGroup()
	{
		return buttonGroup;
	}

	public JButton getStart()
	{
		return start;
	}

	public JComboBox getShipBox()
	{
		return shipBox;
	}

	public ShipPlacementFrame(MainFrame mainFrame)
	{
		super("Platziere die Schiffe");
		this.mainFrame = mainFrame;
		this.shipPlacementComponent = new ShipPlacementComponent(this, mainFrame.getGameManager(), new Dimension(300,
			300));
		configureButtons();
		configureShipBox();
		configureFrame();
		addAllComps();
		pack();

	}

	private void addAllComps()
	{
		add(shipPlacementComponent, BorderLayout.CENTER);
		add(down, BorderLayout.SOUTH);
		add(right, BorderLayout.LINE_END);
		add(start, BorderLayout.WEST);
		add(shipBox, BorderLayout.NORTH);
	}

	private void configureButtons()
	{
		start = new JButton("Start");
		down = new JRadioButton("nach unten");
		right = new JRadioButton("nach rechts");
		buttonGroup = new ButtonGroup();
		buttonGroup.add(right);
		buttonGroup.add(down);
		down.setSelected(true);

		start.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				if (shipBox.getItemCount() != 0)
				{
					JOptionPane.showMessageDialog(shipPlacementComponent, "Es wurden noch nicht alle Schiffe gesetzt!");
				}

				else
				{
					if (mainFrame.getGameManager().bothPlayerPlacedTheirShips())
					{
						mainFrame.getGameManager().startGame();
						dispose();
					}
					else
					{
						mainFrame.getGameManager().notifyObservers();
						mainFrame.getGameManager().nextTurn();
						fillShipBox();
					}
				}

			}
		});

	}

	private void configureShipBox()
	{
		this.shipBox = new JComboBox();
		shipBox.setEditable(false);
		fillShipBox();

	}

	private void fillShipBox()
	{
		List<Ship> ships = mainFrame.getGameManager().getActivePlayer().getShips();
		for (Ship s : ships)
		{
			shipBox.addItem(s);
		}
	}

	private void configureFrame()
	{
		setLayout(new BorderLayout(8, 8));
		Dimension size = new Dimension(700, 700);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		setResizable(false);
		setBackground(Color.DARK_GRAY);
		setLocationRelativeTo(mainFrame);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}

	public void putShipOnSeamap(Ship ship, Coords coords, Direction dir) throws IllegalShipPlacementException
	{
		mainFrame.putShipOnSeamap(ship, coords, dir);
	}
}
