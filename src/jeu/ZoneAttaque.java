package jeu;

import affichage.IAffichage;

public class ZoneAttaque implements IAffichage {
	private Carte carteZoneAttaque;

	public ZoneAttaque(Carte carte) {
		super();
	this.carteZoneAttaque=carte;
	}

	
	public void ajouterCarte(Carte carte) {
		carteZoneAttaque = carte;
	}

	public void afficherZoneAttaque() {
		IAffichage.afficherCartePoseeSurZoneAttaque(carteZoneAttaque);
	}

	public Carte getCarteZoneAttaque() {
		return carteZoneAttaque;
	}

}
