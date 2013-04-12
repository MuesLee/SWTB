package de.fh.swt.schiffeversenken.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel
{
	private JLabel labelHit;
	private JLabel labelMiss;
	private Canvas infoHit;
	private Canvas infoMiss;

	public InfoPanel()
	{
		configure();
	}

	private void configure()
	{
		setLayout(new FlowLayout());
		configurePanel();
		configureLabels();
		configureCanvas();

		add(labelHit);
		add(infoHit);
		add(labelMiss);
		add(infoMiss);

		setVisible(true);
	}

	private void configurePanel()
	{
		Dimension properSizeByFactor = GUIHelper.getProperSizeRelativeToScreensize(0.2);
		setSize(properSizeByFactor);
		setPreferredSize(properSizeByFactor);
		setMaximumSize(properSizeByFactor);

	}

	private void configureCanvas()
	{
		Dimension size = GUIHelper.getProperSizeRelativeToScreensize(0.05);

		infoHit = new Canvas();
		infoMiss = new Canvas();

		infoHit.setBackground(Color.GREEN);
		infoMiss.setBackground(Color.RED);

		infoHit.setSize(size);
		infoMiss.setSize(size);
	}

	private void configureLabels()
	{
		Dimension size = GUIHelper.getProperSizeRelativeToScreensize(0.02);

		labelHit = new JLabel("Treffer");
		labelMiss = new JLabel("kein Treffer");
		labelHit.setFont(new Font("Arial Bold", Font.PLAIN, (int) size.getHeight()));
		labelMiss.setFont(new Font("Arial Bold", Font.PLAIN, (int) size.getHeight()));
	}

}
