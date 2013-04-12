package de.fh.swt.schiffeversenken.data;


public class Shot
{

	private Coords coords;

	private HitType hit;

	public HitType getHit()
	{
		return hit;
	}

	public Shot(Coords coords, HitType hit)
	{
		this.coords = coords;
		this.hit = hit;
	}

	public void setHit(HitType hitType)
	{
		this.hit = hitType;
	}

	public Coords getCoords()
	{
		return coords;
	}

	public void setCoords(Coords coords)
	{
		this.coords = coords;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((coords == null) ? 0 : coords.hashCode());
		result = (prime * result) + ((hit == null) ? 0 : hit.hashCode());
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
		Shot other = (Shot) obj;
		if (coords == null)
		{
			if (other.coords != null)
			{
				return false;
			}
		}
		else if (!coords.equals(other.coords))
		{
			return false;
		}
		if (hit != other.hit)
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "Shot [coords=" + coords + ", hit=" + hit + "]";
	}

}
