package de.fh.swt.schiffeversenken.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import de.fh.swt.schiffeversenken.controller.GameManager;

public class ConfFrame extends JFrame
{

	private JButton buttonStart;
	private JButton buttonCancel;
	private JTextField textNamePlayerOne;
	private JTextField textNamePlayerTwo;
	private GameManager spielleiter;
	private MainFrame mainFrame;
	private ShipPlacementFrame shipPlacementFrame;

	public ConfFrame(GameManager spielleiter, MainFrame mainFrame)
	{

		this.spielleiter = spielleiter;
		this.mainFrame = mainFrame;

		configureFrame();
		configureTextFields();
		configureBoxes();
		repaint();
		setVisible(true);
	}

	private void configureFrame()
	{
		Dimension size = new Dimension(400, 300);
		setSize(size);
		setLayout(new FlowLayout());
		setPreferredSize(size);
		setMaximumSize(size);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void configureBoxes()
	{

		buttonStart = new JButton();
		buttonCancel = new JButton();

		buttonCancel.setText("Abbrechen");
		buttonStart.setText("Start");

		buttonStart.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{
				mainFrame.showShipPlacementFrame();
				dispose();
			}

		});

		buttonCancel.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});
		add(buttonStart);
		add(buttonCancel);
	}

	private void configureTextFields()
	{
		textNamePlayerOne = new JTextField("Name Spieler 1");
		textNamePlayerTwo = new JTextField("Name Spieler 2");
		add(textNamePlayerOne);
		add(textNamePlayerTwo);
	}

	public String getNameForPlayerOne()
	{
		return textNamePlayerOne.getText().trim();
	}
	public String getNameForPlayerTwo()
	{
		return textNamePlayerTwo.getText().trim();
	}

}
