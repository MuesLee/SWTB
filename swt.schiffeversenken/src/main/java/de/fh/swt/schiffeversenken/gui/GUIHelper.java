package de.fh.swt.schiffeversenken.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

public class GUIHelper
{

	//Dimension der GUI relativ der Bildschirmgröße anpassen
	public static Dimension getProperSizeRelativeToScreensize(double screenSizeFactor)
	{
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		size.setSize(size.getHeight() * screenSizeFactor, size.getHeight() * screenSizeFactor);
		return size;
	}

	public static Dimension getProperSizeRelativeToScreensize(double screenSizeFactorWidth,
		double screenSizeFactorHeight)
	{
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		size.setSize(size.getWidth() * screenSizeFactorWidth, size.getHeight() * screenSizeFactorHeight);
		return size;
	}

}
