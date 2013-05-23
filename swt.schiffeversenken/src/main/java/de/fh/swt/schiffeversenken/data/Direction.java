package de.fh.swt.schiffeversenken.data;


//Festlegen der Richtung und zugehörige getter-Methoden
public enum Direction {

	DOWN(0, 1), RIGHT(1, 0);

	private int x;
	private int y;

	Direction(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getModX()
	{
		return this.x;
	}

	public int getModY()
	{
		return this.y;
	}

}
