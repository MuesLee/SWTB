package de.fh.swt.schiffeversenken.data;

import java.util.Arrays;

public abstract class Schiff
{

	private Schiffsteil[] schiffsteile;
	private String name;
	protected int anzahlTeile = 1;

	public boolean istIntakt()
	{
		for (Schiffsteil s : schiffsteile)
		{
			if (s.isIntakt())
			{
				return true;
			}
		}
		return false;
	}

	public Schiff(String name, int anzahlTeile)
	{
		this.anzahlTeile = anzahlTeile;
		this.name = name;
		initiere();
	}

	private void initiere()
	{
		this.schiffsteile = new Schiffsteil[anzahlTeile];
		for (int i = 0; i < anzahlTeile; i++)
		{
			schiffsteile[i] = new Schiffsteil(this);
		}
	}

	public Schiffsteil[] getSchiffsteile()
	{
		return schiffsteile;
	}

	public void setSchiffsteile(Schiffsteil[] schiffsteile)
	{
		this.schiffsteile = schiffsteile;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + anzahlTeile;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schiff other = (Schiff) obj;
		if (anzahlTeile != other.anzahlTeile)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Schiff [name=" + name + ", anzahlTeile=" + anzahlTeile + "]";
	}
	

}
