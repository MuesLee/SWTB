package de.fh.swt.schiffeversenken.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import de.fh.swt.schiffeversenken.controller.GameManager;

public class LanguageFrame extends JFrame
{
	private JComboBox languageBox;
	private JButton buttonStart;
	private JButton buttonCancel;

	//Sprach-Auswahl
	public LanguageFrame()
	{
		super("Choose a language");
		configure();
	}

	////Konfigurieren des LanguageFrames
	private void configure()
	{
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		Dimension size = GUIHelper.getProperSizeRelativeToScreensize(0.2, 0.12);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		fillLanguageBox();
		configureButtons();
		addStuff();
		setVisible(true);
	}

	//Konfigurieren der Buttons
	private void configureButtons()
	{
		buttonCancel = new JButton("Cancel");
		buttonStart = new JButton("Start");

		buttonCancel.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		buttonStart.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(final ActionEvent e)
			{
				Messages.setResourceBundle(getChosenLocale());
				GameManager gameManager = GameManager.getInstance();
				gameManager.startUp();
				dispose();
			}

		});

	}

	//gewählte Sprache zurückgeben
	private Locale getChosenLocale()
	{
		return (Locale) languageBox.getSelectedItem();
	}

	//Elemente dem Frame hinzufügen
	private void addStuff()
	{
		Container contentPane = getContentPane();
		contentPane.add(languageBox);
		contentPane.add(buttonStart);
		contentPane.add(buttonCancel);
	}

	//Sprach-Elemente der Liste hinzufügen
	private void fillLanguageBox()
	{
		languageBox = new JComboBox();
		languageBox.addItem(new Locale("de"));
		languageBox.addItem(new Locale("en"));
		languageBox.setEditable(false);
	}

}
