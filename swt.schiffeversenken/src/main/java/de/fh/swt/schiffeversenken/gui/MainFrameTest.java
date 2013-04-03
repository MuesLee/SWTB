package de.fh.swt.schiffeversenken.gui;

import de.fh.swt.schiffeversenken.controller.GameManager;
import de.fh.swt.schiffeversenken.data.Battleship;
import de.fh.swt.schiffeversenken.data.Cruiser;
import de.fh.swt.schiffeversenken.data.ShipPart;

public class MainFrameTest
{

	public static void main(String args[])
	{

		GameManager gameManager = new GameManager();

		ShipPart[][] coordsPlayerOne = new ShipPart[12][12];
		ShipPart[][] coordsPlayerTwo = new ShipPart[12][12];
		Battleship schlachtschiff = new Battleship("asd");
		Cruiser kreuzer = new Cruiser("dhf");

		for (int i = 0; i < schlachtschiff.getShipParts().length; i++)
		{
			coordsPlayerOne[0][i] = schlachtschiff.getShipParts()[i];
			coordsPlayerTwo[i][4] = schlachtschiff.getShipParts()[i];
		}
		for (int i = 0; i < kreuzer.getShipParts().length; i++)
		{
			coordsPlayerOne[11][i] = kreuzer.getShipParts()[i];
			coordsPlayerTwo[i][7] = kreuzer.getShipParts()[i];
		}

		gameManager.getPlayerOne().getSeamap().setShipParts(coordsPlayerOne);
		gameManager.getPlayerTwo().getSeamap().setShipParts(coordsPlayerTwo);

		gameManager.start();
	}
}
