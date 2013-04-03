package de.fh.swt.schiffeversenken.data;

public enum HitType {

	UNKNOWN("nicht überprüfter Schuss"), NOHIT("Platsch! Kein Treffer"), HIT("Treffer!"), HITAGAIN(
		"Dieses Schiffsteil ist bereits zerstört!");

	private String text;

	HitType(String text)
	{
		this.text = text;
	}

	@Override
	public String toString()
	{

		return this.text;
	}
}
