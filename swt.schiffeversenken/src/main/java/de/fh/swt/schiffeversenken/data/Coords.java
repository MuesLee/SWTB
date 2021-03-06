package de.fh.swt.schiffeversenken.data;

//getter- und setter-Methoden zu den Koordinaten
public class Coords
{
	private int x, y;

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public Coords(int x, int y)
	{
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + x;
		result = (prime * result) + y;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Coords other = (Coords) obj;
		if (x != other.x)
		{
			return false;
		}
		if (y != other.y)
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "Coords [x=" + x + ", y=" + y + "]";
	}

}
