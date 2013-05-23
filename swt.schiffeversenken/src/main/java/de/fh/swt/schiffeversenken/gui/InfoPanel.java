package de.fh.swt.schiffeversenken.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel
{
	private JLabel labelHit;
	private JLabel labelMiss;
	private JLabel labelDestroyed;
	private Canvas infoHit;
	private Canvas infoMiss;
	private Canvas infoDestroyed;

	public InfoPanel()
	{
		configure();
	}

	//Konfigurieren des Info Panels 
	private void configure()
	{
		setLayout(new GridLayout(3, 2));
		configurePanel();
		configureLabels();
		configureCanvas();

		add(infoHit);
		add(labelHit);
		add(infoMiss);
		add(labelMiss);
		add(infoDestroyed);
		add(labelDestroyed);

		setVisible(true);
	}

	//Dimension des Panels festlegen
	private void configurePanel()
	{
		Dimension properSizeByFactor = GUIHelper.getProperSizeRelativeToScreensize(0.2);
		setSize(properSizeByFactor);
		setPreferredSize(properSizeByFactor);
		setMaximumSize(properSizeByFactor);

	}

	//Konfigurieren der Canvas-Elemente (Zuordnung der Farben, Größe)
	private void configureCanvas()
	{
		Dimension size = GUIHelper.getProperSizeRelativeToScreensize(0.05);

		infoHit = new Canvas();
		infoMiss = new Canvas();
		infoDestroyed = new Canvas();

		infoHit.setBackground(Color.GREEN);
		infoMiss.setBackground(Color.RED);
		infoDestroyed.setBackground(Color.black);

		infoHit.setSize(size);
		infoMiss.setSize(size);
		infoDestroyed.setSize(size);
	}

	//Konfigurieren der Labels(Schriftart, Text)
	private void configureLabels()
	{
		Dimension size = GUIHelper.getProperSizeRelativeToScreensize(0.02);

		labelHit = new JLabel(Messages.getString("InfoPanel.LabelTextHit")); //$NON-NLS-1$
		labelMiss = new JLabel(Messages.getString("InfoPanel.LabelTextNoHit")); //$NON-NLS-1$
		labelDestroyed = new JLabel(Messages.getString("InfoPanel.LabelTextDestroyed")); //$NON-NLS-1$
		labelHit.setFont(new Font("Arial Bold", Font.PLAIN, (int) size.getHeight() / 2)); //$NON-NLS-1$
		labelMiss.setFont(new Font("Arial Bold", Font.PLAIN, (int) size.getHeight() / 2)); //$NON-NLS-1$
		labelDestroyed.setFont(new Font("Arial Bold", Font.PLAIN, (int) size.getHeight() / 2)); //$NON-NLS-1$
	}
}
