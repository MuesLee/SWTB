package de.fh.swt.schiffeversenken.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import de.fh.swt.schiffeversenken.controller.GameManager;

public class ConfigurationFrame extends JFrame
{

	private JButton buttonStart;
	private JButton buttonCancel;
	private JTextField textNamePlayerOne;
	private JTextField textNamePlayerTwo;
	private JCheckBox checkBoxRandomShipsPlayerOne;
	private JCheckBox checkBoxRandomShipsPlayerTwo;
	private MainFrame mainFrame;

	//Konfigurieren des Mainframes
	public ConfigurationFrame(MainFrame mainFrame)
	{

		this.mainFrame = mainFrame;
		configure();
		repaint();
		setVisible(true);
	}

	private void configure()
	{
		configureFrame();
		configureTextFields();
		configureBoxes();
		configureCheckBoxes();
		add(textNamePlayerOne);
		add(checkBoxRandomShipsPlayerOne);
		add(textNamePlayerTwo);
		add(checkBoxRandomShipsPlayerTwo);
		add(buttonStart);
		add(buttonCancel);
	}

	private void configureCheckBoxes()
	{
		checkBoxRandomShipsPlayerOne = new JCheckBox("Zufällige Schiffsplatzierung");
		checkBoxRandomShipsPlayerTwo = new JCheckBox("Zufällige Schiffsplatzierung");
	}

	//Konfigurieren des Frames(Größe, Layout etc.)
	private void configureFrame()
	{
		setLayout(new GridLayout(3, 2));
		Dimension size = GUIHelper.getProperSizeRelativeToScreensize(0.3, 0.4);
		setSize(size);
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
				GameManager gameManager = mainFrame.getGameManager();
				if (checkBoxRandomShipsPlayerOne.isSelected() && checkBoxRandomShipsPlayerTwo.isSelected())
				{
					gameManager.putShipsRandomOnSeamap(gameManager.getPlayerOne());
					gameManager.putShipsRandomOnSeamap(gameManager.getPlayerTwo());
					mainFrame.startGame();
					dispose();
				}
				else
				{
					if (checkBoxRandomShipsPlayerOne.isSelected())
					{
						gameManager.putShipsRandomOnSeamap(gameManager.getPlayerOne());
						gameManager.nextTurn();
					}
					else
					{
						gameManager.putShipsRandomOnSeamap(gameManager.getPlayerTwo());
					}
					mainFrame.showShipPlacementFrame();
				}
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
	}

	//Konfigurieren der Textfelder
	private void configureTextFields()
	{
		textNamePlayerOne = new JTextField(Messages.getString("ConfigurationFrame.TextFieldPlaceholderP1")); //$NON-NLS-1$
		textNamePlayerTwo = new JTextField(Messages.getString("ConfigurationFrame.TextFieldPlaceholderP2")); //$NON-NLS-1$
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
