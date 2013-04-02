package de.fh.swt.schiffeversenken.data;

public class Schuss
{

	private int x;
	private int y;

	private TrefferTyp treffer;

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

	public TrefferTyp getTreffer()
	{
		return treffer;
	}

	public void setTreffer(TrefferTyp treffer)
	{
		this.treffer = treffer;
	}

	public Schuss(int x, int y, TrefferTyp treffer)
	{
		super();
		this.x = x;
		this.y = y;
		this.treffer = treffer;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((treffer == null) ? 0 : treffer.hashCode());
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
		Schuss other = (Schuss) obj;
		if (treffer != other.treffer)
		{
			return false;
		}
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

		return "X: " + x + " Y: " + y + "    " + treffer;
	}

}
