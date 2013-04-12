package de.fh.swt.schiffeversenken.data;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class PlayerTest
{
	private Player player;

	@Before
	public void initiate()
	{
		player = new Player("Dummy", 12);
		ArrayList<Ship> ships = new ArrayList<Ship>();
		ships.add(new Submarine("Yellow Submarine"));
		player.setShips(ships);
	}

	@Test
	public void shouldStoreAShotAndRetrieveItAfterwards()
	{
		Shot expected = new Shot(new Coords(0, 0), HitType.HIT);
		player.addShot(expected);
		Assert.assertTrue((player.getShots().contains(expected)));
	}

	@Test
	public void shouldntBeDefeated()
	{
		Assert.assertFalse(player.isDefeated());
	}

	@Test
	public void shouldBeDefeated()
	{
		for (Ship s : player.getShips())
		{
			for (ShipPart sp : s.getShipParts())
			{
				sp.wasHit();
			}
		}
		Assert.assertTrue(player.isDefeated());
	}

}
