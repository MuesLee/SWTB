package de.fh.swt.schiffeversenken.gui;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages
{
	private static final String BUNDLE_NAME = "de.fh.swt.schiffeversenken.gui.messages"; //$NON-NLS-1$
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

	//Bekommt Nachrichten übergeben, welche aufgrund der Sprachauswahl in korrekter Sprache zurückgegeben werden
	public static String getString(String key)
	{
		try
		{
			return resourceBundle.getString(key);
		}
		catch (MissingResourceException e)
		{
			return '!' + key + '!';
		}
	}

	public static void setResourceBundle(Locale chosenLocale)
	{
		resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, chosenLocale);
	}
}
