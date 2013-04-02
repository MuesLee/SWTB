package de.fh.swt.schiffeversenken.gui;

import de.fh.swt.schiffeversenken.controller.Spieler;
import de.fh.swt.schiffeversenken.controller.Spielleiter;
import de.fh.swt.schiffeversenken.data.Kreuzer;
import de.fh.swt.schiffeversenken.data.Schiffsteil;
import de.fh.swt.schiffeversenken.data.Schlachtschiff;
import de.fh.swt.schiffeversenken.data.Seekarte;

public class MainFrameTest
{

	public static void main(String args[])
	{

		Seekarte seekarte = new Seekarte(12);
		Spieler spielerEins = new Spieler("Hans");
		Spieler spielerZwei = new Spieler("Wurst");
		Spielleiter spielleiter = new Spielleiter(seekarte, spielerEins, spielerZwei);

		MainFrame mainFrame = new MainFrame(spielleiter);

		Schiffsteil[][] schiffsteile = new Schiffsteil[12][12];
		Schlachtschiff schlachtschiff = new Schlachtschiff("asd");
		Kreuzer kreuzer = new Kreuzer("dhf");

		for (int i = 0; i < schlachtschiff.getSchiffsteile().length; i++)
		{
			schiffsteile[0][i] = schlachtschiff.getSchiffsteile()[i];
		}
		for (int i = 0; i < kreuzer.getSchiffsteile().length; i++)
		{
			schiffsteile[11][i] = kreuzer.getSchiffsteile()[i];
		}
		schiffsteile[11][0].setIntakt(false);
		schiffsteile[11][2].setIntakt(false);
		schiffsteile[0][0].setIntakt(false);
		schiffsteile[0][2].setIntakt(false);
		schiffsteile[0][4].setIntakt(false);

		seekarte.setKoordinaten(schiffsteile);

		//System.out.println(seekarte);

	}
}
