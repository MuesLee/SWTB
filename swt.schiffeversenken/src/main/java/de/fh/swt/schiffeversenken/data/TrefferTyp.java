package de.fh.swt.schiffeversenken.data;

public enum TrefferTyp {

	unbekannt("nicht �berpr�fter Schuss"), keinTreffer("Platsch! Kein Treffer"), treffer("Treffer!"), trefferAberSchonKaputt(
		"Dieses Schiffsteil ist bereits zerst�rt!");

	private String text;

	TrefferTyp(String text)
	{
		this.text = text;
	}

	@Override
	public String toString()
	{

		return this.text;
	}
}
