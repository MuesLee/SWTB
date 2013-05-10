package de.fh.swt.schiffeversenken.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class ConfigurationFrame extends JFrame
{

	private JButton buttonStart;
	private JButton buttonCancel;
	private JTextField textNamePlayerOne;
	private JTextField textNamePlayerTwo;
	private MainFrame mainFrame;

	public ConfigurationFrame(MainFrame mainFrame)
	{

		this.mainFrame = mainFrame;

		configureFrame();
		configureTextFields();
		configureBoxes();
		repaint();
		setVisible(true);
	}

	private void configureFrame()
	{

		Dimension size = GUIHelper.getProperSizeRelativeToScreensize(0.3, 0.4);
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

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				mainFrame.showShipPlacementFrame();
				dispose();
			}

		});

		buttonCancel.addActionListener(new ActionListener()
		{

			@Override
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
