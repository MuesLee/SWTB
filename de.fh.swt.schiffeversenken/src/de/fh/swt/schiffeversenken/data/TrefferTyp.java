package de.fh.swt.schiffeversenken.data;

public enum TrefferTyp {

	unbekannt("nicht �berpr�fter Schuss"), keinTreffer("Kein Treffer"), treffer("Treffer"), trefferAberSchonKaputt(
		"Treffer, aber dieser Schiffsteil ist bereits zerst�rt!");

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
