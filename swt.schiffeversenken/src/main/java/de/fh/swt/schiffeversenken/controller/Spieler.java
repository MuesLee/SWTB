package de.fh.swt.schiffeversenken.controller;

import java.util.HashSet;
import java.util.Set;

import de.fh.swt.schiffeversenken.data.Schiff;
import de.fh.swt.schiffeversenken.data.Schuss;

public class Spieler
{

	private String name;
	private Schiff[] schiffe;
	private Set<Schuss> schussliste = new HashSet<Schuss>();

	public Spieler(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Schiff[] getSchiffe()
	{
		return schiffe;
	}

	public void setSchiffe(Schiff[] schiffe)
	{
		this.schiffe = schiffe;
	}

	public boolean istBesiegt()
	{
		for (Schiff s : schiffe)
		{
			if (s.istIntakt())
			{
				return false;
			}
		}

		return true;
	}

	public Set<Schuss> getSchussliste()
	{
		return schussliste;
	}

	public void setSchussliste(Set<Schuss> schussliste)
	{
		this.schussliste = schussliste;
	}

	public void addSchuss(Schuss schuss) {
		this.schussliste.add(schuss);
	}

}
