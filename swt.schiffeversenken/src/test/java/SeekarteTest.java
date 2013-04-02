import org.junit.Test;

import de.fh.swt.schiffeversenken.data.Schiffsteil;
import de.fh.swt.schiffeversenken.data.UBoot;


public class SeekarteTest {

	
	@Test
	public void getOneSchiffsteil() {
		
		Schiffsteil expected = new Schiffsteil(new UBoot(""));	
		Schiffsteil actual = new Schiffsteil(new UBoot(""));	
		org.junit.Assert.assertEquals(expected, actual);
		
		
	}
}
