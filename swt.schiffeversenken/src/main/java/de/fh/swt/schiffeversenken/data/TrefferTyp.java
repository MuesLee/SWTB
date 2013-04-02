package de.fh.swt.schiffeversenken.data;

public enum TrefferTyp {

	unbekannt("nicht überprüfter Schuss"), keinTreffer("Kein Treffer"), treffer("Treffer"), trefferAberSchonKaputt(
		"Treffer, aber dieser Schiffsteil ist bereits zerstört!");

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
