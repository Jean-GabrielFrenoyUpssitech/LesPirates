package jeu;

import affichage.IAffichage;

public class ZoneAttaque extends Zone implements IAffichage {
	private Carte carteZoneAttaque;

	public ZoneAttaque(Carte carte) {
		super();
		this.cartes[1] = carte;
	}

	@Override
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
