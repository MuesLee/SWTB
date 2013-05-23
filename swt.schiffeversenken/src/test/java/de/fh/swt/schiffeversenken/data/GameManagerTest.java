package de.fh.swt.schiffeversenken.data;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fh.swt.schiffeversenken.controller.GameManager;

public class GameManagerTest
{

	private GameManager gameManager;
	private int size;
	private Seamap seamap;
	private Seamap seamap2;

	@Before
	public void initiate() throws IllegalShipPlacementException
	{
		this.gameManager = GameManager.getInstance();

		ArrayList<Ship> shipsPlayerOne = new ArrayList<Ship>();
		ArrayList<Ship> shipsPlayerTwo = new ArrayList<Ship>();

		Cruiser cruiser = new Cruiser("Dose");
		Cruiser cruiser2 = new Cruiser("Dose2");

		shipsPlayerOne.add(cruiser);
		shipsPlayerTwo.add(cruiser2);

		gameManager.getPlayerOne().setShips(shipsPlayerOne);
		gameManager.getPlayerTwo().setShips(shipsPlayerTwo);

		size = 12;
		seamap = new Seamap(size);
		Coords coordsCruiser1 = new Coords(0, 11);
		seamap.putShipOnSeamap(cruiser, coordsCruiser1, Direction.RIGHT);

		seamap2 = new Seamap(size);
		Coords coordsCruiser2 = new Coords(1, 11);
		seamap2.putShipOnSeamap(cruiser, coordsCruiser2, Direction.RIGHT);

	}

	@Test
	public void handleShot()
	{
		Coords coordsCruiser1 = new Coords(0, 11);
		gameManager.handleShot(coordsCruiser1);

		Coords coordsCruiser2 = new Coords(1, 11);
		gameManager.handleShot(coordsCruiser2);

		Assert.assertEquals(seamap.getShipPart(coordsCruiser1).isIntact(), false);
		Assert.assertEquals(seamap2.getShipPart(coordsCruiser2).isIntact(), false);
	}

}
