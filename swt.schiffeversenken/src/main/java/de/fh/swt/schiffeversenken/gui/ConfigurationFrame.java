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

	//Konfigurieren des Mainframes
	public ConfigurationFrame(MainFrame mainFrame)
	{

		this.mainFrame = mainFrame;

		configureFrame();
		configureTextFields();
		configureBoxes();
		repaint();
		setVisible(true);
	}

	//Konfigurieren des Frames(Größe, Layout etc.)
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

	//Konfigurieren der Buttons
	private void configureBoxes()
	{

		buttonStart = new JButton();
		buttonCancel = new JButton();

		buttonCancel.setText(Messages.getString("ConfigurationFrame.ButtonTextAbort")); //$NON-NLS-1$
		buttonStart.setText(Messages.getString("ConfigurationFrame.ButtonTextStart")); //$NON-NLS-1$

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

	//Konfigurieren der Textfelder
	private void configureTextFields()
	{
		textNamePlayerOne = new JTextField(Messages.getString("ConfigurationFrame.TextFieldPlaceholderP1")); //$NON-NLS-1$
		textNamePlayerTwo = new JTextField(Messages.getString("ConfigurationFrame.TextFieldPlaceholderP2")); //$NON-NLS-1$
		add(textNamePlayerOne);
		add(textNamePlayerTwo);
	}

	//Namen der Spieler auslesen
	public String getNameForPlayerOne()
	{
		return textNamePlayerOne.getText().trim();
	}

	public String getNameForPlayerTwo()
	{
		return textNamePlayerTwo.getText().trim();
	}

}
