package de.fh.swt.schiffeversenken.controller;

import de.fh.swt.schiffeversenken.data.Schiffsteil;
import de.fh.swt.schiffeversenken.data.Schuss;
import de.fh.swt.schiffeversenken.data.Seekarte;

public class Spielleiter
{

	private Seekarte seekarte;
	private Spieler spielerEins;

	public Seekarte getSeekarte()
	{
		return seekarte;
	}

	public void setSeekarte(Seekarte seekarte)
	{
		this.seekarte = seekarte;
	}

	public Spieler getSpielerEins()
	{
		return spielerEins;
	}

	public void setSpielerEins(Spieler spielerEins)
	{
		this.spielerEins = spielerEins;
	}

	public Spieler getSpielerZwei()
	{
		return spielerZwei;
	}

	public void setSpielerZwei(Spieler spielerZwei)
	{
		this.spielerZwei = spielerZwei;
	}

	public Spieler getAktiverSpieler()
	{
		return aktiverSpieler;
	}

	public void setAktiverSpieler(Spieler aktiverSpieler)
	{
		this.aktiverSpieler = aktiverSpieler;
	}

	private Spieler spielerZwei;
	private Spieler aktiverSpieler;

	public Spielleiter(Seekarte seekarte, Spieler spielerEins, Spieler spielerZwei)
	{
		this.seekarte = seekarte;
		this.spielerEins = spielerEins;
		this.spielerZwei = spielerZwei;
		aktiverSpieler = spielerEins;
	}

	public boolean pruefeSchuss(Schuss schuss)
	{

		Schiffsteil schiffsteil = seekarte.getSchiffsteil(schuss.getX(), schuss.getY());
		System.out.println("schiffsteil" + schiffsteil);
		if ((schiffsteil == null) || !schiffsteil.isIntakt())
		{
			return false;
		}

		else
		{
			setzeTreffer(schiffsteil);

			return true;
		}
	}

	private void setzeTreffer(Schiffsteil schiffsteil)
	{
		schiffsteil.setIntakt(false);
	}

}
