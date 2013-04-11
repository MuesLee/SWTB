package de.fh.swt.schiffeversenken.data;

public class IllegalShipPlacementException extends Exception
{
	private int failureAtShipSegmentNumber;

	public IllegalShipPlacementException(String string)
	{
		super(string);

	}

	public IllegalShipPlacementException(String string, int failureAtShipSegmentNumber)
	{
		super(string);
		this.setFailureAtShipSegmentNumber(failureAtShipSegmentNumber);

	}

	public int getFailureAtShipSegmentNumber()
	{
		return failureAtShipSegmentNumber;
	}

	private void setFailureAtShipSegmentNumber(int failureAtShipSegmentNumber)
	{
		this.failureAtShipSegmentNumber = failureAtShipSegmentNumber;
	}
}
