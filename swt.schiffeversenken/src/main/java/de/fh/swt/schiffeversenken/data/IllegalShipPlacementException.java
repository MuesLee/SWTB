package de.fh.swt.schiffeversenken.data;

public class IllegalShipPlacementException extends Exception
{
	private int failureAtShipSegmentNumber;

	//Aufruf bei illegaler Platzierung eines Schiffes
	public IllegalShipPlacementException(String string)
	{
		super(string);

	}

	public IllegalShipPlacementException(String string, int failureAtShipSegmentNumber)
	{
		super(string);
		this.setFailureAtShipSegmentNumber(failureAtShipSegmentNumber);

	}

	//Grund der Exception ermitteln
	public int getFailureAtShipSegmentNumber()
	{
		return failureAtShipSegmentNumber;
	}

	//Grund der Exception ausgeben
	private void setFailureAtShipSegmentNumber(int failureAtShipSegmentNumber)
	{
		this.failureAtShipSegmentNumber = failureAtShipSegmentNumber;
	}
}
