package de.fh.swt.schiffeversenken.controller;

import de.fh.swt.schiffeversenken.data.Schiffsteil;
import de.fh.swt.schiffeversenken.data.Schuss;
import de.fh.swt.schiffeversenken.data.Seekarte;
import de.fh.swt.schiffeversenken.data.TrefferTyp;

public class Spielleiter {

	private Seekarte seekarte;
	private Spieler spielerEins;

	public Seekarte getSeekarte() {
		return seekarte;
	}

	public void setSeekarte(Seekarte seekarte) {
		this.seekarte = seekarte;
	}

	public Spieler getSpielerEins() {
		return spielerEins;
	}

	public void setSpielerEins(Spieler spielerEins) {
		this.spielerEins = spielerEins;
	}

	public Spieler getSpielerZwei() {
		return spielerZwei;
	}

	public void setSpielerZwei(Spieler spielerZwei) {
		this.spielerZwei = spielerZwei;
	}

	public Spieler getAktiverSpieler() {
		return aktiverSpieler;
	}

	public void setAktiverSpieler(Spieler aktiverSpieler) {
		this.aktiverSpieler = aktiverSpieler;
	}

	private Spieler spielerZwei;
	private Spieler aktiverSpieler;

	public Spielleiter(Seekarte seekarte, Spieler spielerEins,
			Spieler spielerZwei) {
		this.seekarte = seekarte;
		this.spielerEins = spielerEins;
		this.spielerZwei = spielerZwei;
		aktiverSpieler = spielerEins;
	}

	public void verarbeiteSchuss(Schuss schuss) {

		Schiffsteil schiffsteil = seekarte.getSchiffsteil(schuss.getX(),
				schuss.getY());
		System.out.println("schiffsteil" + schiffsteil);

		if ((schiffsteil == null)) {
			schuss.setTreffer(TrefferTyp.keinTreffer);
		}

		else {

			if (!schiffsteil.isIntakt()) {
				schuss.setTreffer(TrefferTyp.trefferAberSchonKaputt);
			}

			else {
				setzeTreffer(schiffsteil);
				schuss.setTreffer(TrefferTyp.treffer);
			}

		}
		speichereSchuss(schuss);
			
		switch(schuss.getTreffer())
		{
		case treffer:
			//aktiver Spieler darf noch einmal schieﬂen
			break;
		case keinTreffer:
		case trefferAberSchonKaputt:
			n‰chsterZug();
			break;
		}
		
	}

	private void speichereSchuss(Schuss schuss) {

		aktiverSpieler.addSchuss(schuss);
	}

	private void setzeTreffer(Schiffsteil schiffsteil) {
		schiffsteil.setIntakt(false);
	}

	public void n‰chsterZug() {
		if(aktiverSpieler == spielerEins)
		{
			aktiverSpieler = spielerZwei;
		}
		else{
			aktiverSpieler = spielerEins;
		}
	}

}
