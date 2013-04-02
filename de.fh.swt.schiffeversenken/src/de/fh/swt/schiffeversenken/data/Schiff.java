package de.fh.swt.schiffeversenken.data;

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

}
