package de.fh.swt.schiffeversenken.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SeamapTest
{

	private int size;
	private Seamap seamap;

	@Before
	public void initiation()
	{
		size = 12;
		seamap = new Seamap(size);
	}

	@Test
	public void ShouldPlaceShipAtGivenCoordsAndRetrieveItAfterwards() throws IllegalShipPlacementException
	{
		Cruiser cruiser = new Cruiser("Dose");
		Coords fore = new Coords(size, 0);
		seamap.putShipOnSeamap(cruiser, fore, Direction.RIGHT);
		seamap.getShipParts();
		seamap = new Seamap(size);
	}

	@Test
	public void shouldNOTPlaceTheShipAtGivenCoordsBecauseOutofBounds() throws IllegalShipPlacementException
	{
		Cruiser cruiser = new Cruiser("Kreuzer 1");
		Coords coords = new Coords(5, size);
		seamap.putShipOnSeamap(cruiser, coords, Direction.DOWN);
		Assert.assertNull(seamap.getShipPart(coords));
		seamap = new Seamap(size);
	}

	@Test
	public void shouldPlaceTheShipAtGivenCoords() throws IllegalShipPlacementException
	{
		Cruiser cruiser = new Cruiser("Kreuzer 2");
		Coords coords = new Coords(2, 0);
		seamap.putShipOnSeamap(cruiser, coords, Direction.DOWN);
		Assert.assertNotNull(seamap.getShipPart(coords));
		Assert.assertNotNull(seamap.getShipPart(new Coords(2, 1)));
		Assert.assertNotNull(seamap.getShipPart(new Coords(2, 2)));
		seamap = new Seamap(size);
	}

	@Test(expected = IllegalShipPlacementException.class)
	public void shouldNOTPlaceTheShipAtGivenCoordsBecauseOfCollision1() throws IllegalShipPlacementException
	{
		//Schiff soll DIREKT auf bestehendes Schiff gesetzt werden 

		Cruiser cruiser = new Cruiser("Kreuzer 3");
		Cruiser cruiserIceberg = new Cruiser("Kreuzer Iceberg");
		Coords coords = new Coords(2, 0);
		seamap.putShipOnSeamap(cruiserIceberg, coords, Direction.RIGHT);
		seamap.putShipOnSeamap(cruiser, coords, Direction.RIGHT);
		Assert.assertNull(seamap.getShipPart(coords));
		seamap = new Seamap(size);
	}

	@Test(expected = IllegalShipPlacementException.class)
	public void shouldNOTPlaceTheShipAtGivenCoordsBecauseOfCollision2() throws IllegalShipPlacementException
	{
		//Ende des zu setzenden Schiffs kollidiert mit Anfang bestehenden Schiffs

		Cruiser cruiser = new Cruiser("Kreuzer 4");
		Cruiser cruiserIceberg = new Cruiser("Kreuzer Iceberg");
		Coords coordsIceberg = new Coords(0, 0);
		Coords coords = new Coords(2, 0);
		seamap.putShipOnSeamap(cruiserIceberg, coordsIceberg, Direction.RIGHT);
		seamap.putShipOnSeamap(cruiser, coords, Direction.RIGHT);
		Assert.assertNull(seamap.getShipPart(coords));

	}

}
