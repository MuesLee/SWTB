package de.fh.swt.schiffeversenken.data;

import org.junit.Assert;
import org.junit.Test;

import de.fh.swt.schiffeversenken.data.Cruiser;
import de.fh.swt.schiffeversenken.data.ShipPart;

public class CruiserTest
{
	@Test
	public void shouldHaveThreeShipParts()
	{
		Cruiser cruiser = new Cruiser("Kreuzer");
		int i = 0;
		for (ShipPart sp : cruiser.getShipParts())
		{
			i++;
		}

		Assert.assertEquals(3, i);

	}

}
