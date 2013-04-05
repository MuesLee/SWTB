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

		configureButtons();
		configureShipBox();
		configureFrame();
		configureShipComponent();
		addAllComps();
		pack();

	}

	private void addAllComps()
	{
		add(shipPlacementComponent, BorderLayout.CENTER);
		add(down, BorderLayout.NORTH);
		add(right, BorderLayout.LINE_END);
		add(start, BorderLayout.WEST);
		add(shipBox, BorderLayout.SOUTH);
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
				if (shipBox.getItemCount() == 0)
				{
					mainFrame.getGameManager().startGame();
					dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(mainFrame, "Es wurden noch nicht alle Schiffe gesetzt!");
				}

			}
		});

	}

	private void configureShipBox()
	{
		this.shipBox = new JComboBox();
		shipBox.setEditable(false);
		List<Ship> ships = mainFrame.getGameManager().getActivePlayer().getShips();
		for (Ship s : ships)
		{
			shipBox.addItem(s);
		}

	}

	private void configureFrame()
	{
		setLayout(new BorderLayout());
		Dimension size = new Dimension(900, 900);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		setResizable(false);
		setBackground(Color.DARK_GRAY);
		setLocationRelativeTo(mainFrame);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}

	private void configureShipComponent()
	{

		this.shipPlacementComponent = new ShipPlacementComponent(this, mainFrame.getGameManager(), new Dimension(300,
			300));
	}
}
