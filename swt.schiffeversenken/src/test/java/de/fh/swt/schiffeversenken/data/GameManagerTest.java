package de.fh.swt.schiffeversenken.data;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fh.swt.schiffeversenken.controller.GameManager;

public class GameManagerTest
{

	private GameManager gameManager;

	@Before
	public void initiate()
	{
		this.gameManager = GameManager.getInstance();

		ArrayList<Ship> shipsPlayerOne = new ArrayList<Ship>();
		ArrayList<Ship> shipsPlayerTwo = new ArrayList<Ship>();

		shipsPlayerOne.add(new Cruiser(""));
		shipsPlayerTwo.add(new Cruiser(""));

		gameManager.getPlayerOne().setShips(shipsPlayerOne);
		gameManager.getPlayerTwo().setShips(shipsPlayerTwo);

	}

	@Test
	public void testShotShouldMissShips()
	{
		gameManager.handleShot(new Coords(0, 0));
	}

	@Test
	public void testNextTurn()
	{
		gameManager.nextTurn();
		Assert.assertEquals(gameManager.getPlayerTwo(), gameManager.getActivePlayer());
	}
}
