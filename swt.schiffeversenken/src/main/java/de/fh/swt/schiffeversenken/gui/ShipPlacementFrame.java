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

	//getter- und setter-Methoden zu den jeweiligen Elementen(MainFrame, Richtungswahl, Buttons, Schiffsliste)
	public MainFrame getMainFrame()
	{
		return mainFrame;
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

	//setzt alle nötigen Komponenten für das ShipPlacementFrame
	public ShipPlacementFrame(MainFrame mainFrame)
	{
		super(Messages.getString("ShipPlacementFrame.frameTitle")); //$NON-NLS-1$
		this.mainFrame = mainFrame;
		configureComponent(mainFrame);
		configureButtons();
		configureShipBox();
		configureFrame();
		addAllComps();
		pack();
	}

	//Konfigurieren der Komponente(Größe)
	private void configureComponent(MainFrame mainFrame)
	{
		this.shipPlacementComponent = new ShipPlacementComponent(this, mainFrame.getGameManager(),
			GUIHelper.getProperSizeRelativeToScreensize(0.6));
	}

	//Hinzufügen der benötigten Komponenten
	private void addAllComps()
	{
		add(shipPlacementComponent, BorderLayout.CENTER);
		add(down, BorderLayout.SOUTH);
		add(right, BorderLayout.LINE_END);
		add(start, BorderLayout.WEST);
		add(shipBox, BorderLayout.NORTH);
	}

	//Konfigurieren der Buttons
	private void configureButtons()
	{
		start = new JButton(Messages.getString("ShipPlacementFrame.ButtonTextStart")); //$NON-NLS-1$
		down = new JRadioButton(Messages.getString("ShipPlacementFrame.RadioButtonDown")); //$NON-NLS-1$
		right = new JRadioButton(Messages.getString("ShipPlacementFrame.RadioButtonRight")); //$NON-NLS-1$
		buttonGroup = new ButtonGroup();
		buttonGroup.add(right);
		buttonGroup.add(down);
		down.setSelected(true);

		start.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (shipBox.getItemCount() != 0)
				{
					JOptionPane.showMessageDialog(shipPlacementComponent, Messages.getString("ShipPlacementFrame.ErrorTextNotAllShipsPlaced")); //$NON-NLS-1$
				}

				else
				{
					if (mainFrame.getGameManager().bothPlayerPlacedTheirShips())
					{
						setVisible(false);
						mainFrame.showSeamapFrames();
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

	//Konfigurieren der Liste mit den Schiffen
	private void configureShipBox()
	{
		this.shipBox = new JComboBox();
		shipBox.setEditable(false);
		fillShipBox();

	}

	//Füllen der Schiffsliste
	private void fillShipBox()
	{
		List<Ship> ships = mainFrame.getGameManager().getActivePlayer().getShips();
		for (Ship s : ships)
		{
			shipBox.addItem(s);
		}
	}

	//Konfigurieren des Frames(Größe, Location, Hintergrundfarbe etc.)
	private void configureFrame()
	{
		double screenSizeFactor = 0.8;
		setLayout(new BorderLayout(8, 8));
		Dimension size = GUIHelper.getProperSizeRelativeToScreensize(screenSizeFactor);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		setResizable(true);
		setBackground(Color.DARK_GRAY);
		setLocationRelativeTo(mainFrame);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}
