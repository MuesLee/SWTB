package de.fh.swt.schiffeversenken.data;

public class Schiffsteil
{

	private boolean intakt = true;

	public boolean isIntakt()
	{
		return intakt;
	}

	public void setIntakt(boolean istIntakt)
	{
		this.intakt = istIntakt;
	}

	public Schiff getSchiff()
	{
		return schiff;
	}

	private Schiff schiff;

	public Schiffsteil(Schiff schiff)
	{
		this.schiff = schiff;
	}

}

