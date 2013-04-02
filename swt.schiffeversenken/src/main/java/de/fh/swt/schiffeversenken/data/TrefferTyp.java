package de.fh.swt.schiffeversenken.data;

public enum TrefferTyp {

	unbekannt("nicht überprüfter Schuss"), keinTreffer("Platsch! Kein Treffer"), treffer("Treffer!"), trefferAberSchonKaputt(
		"Dieses Schiffsteil ist bereits zerstört!");

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
