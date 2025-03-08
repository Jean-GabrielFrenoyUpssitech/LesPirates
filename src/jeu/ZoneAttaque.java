package jeu;

import affichage.IAffichage;

public class ZoneAttaque extends Zone implements IAffichage {
private Carte carteZone;
	@Override
	public void ajouterCarte(Carte carte) {
		carteZone=carte;		
	}
	public void afficherZoneAttaque () {
	afficherCartePoseeSurZoneAttaque(carteZone);
	}

}
