package de.fh.swt.schiffeversenken.data;
import org.junit.Test;

import de.fh.swt.schiffeversenken.data.Schiffsteil;
import de.fh.swt.schiffeversenken.data.Seekarte;
import de.fh.swt.schiffeversenken.data.UBoot;


public class SeekarteTest {

	
	@Test
	public void getOneSchiffsteil() {
		
		Schiffsteil expected = new Schiffsteil(new UBoot(""));	
		Seekarte seekarte = new Seekarte(12);
		seekarte.getKoordinaten()[0][0]=expected;
		
		org.junit.Assert.assertEquals(expected, seekarte.getSchiffsteil(0, 0));
		
		
	}
}
