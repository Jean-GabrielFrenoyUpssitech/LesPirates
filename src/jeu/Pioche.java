package jeu;

import affichage.IAffichage;

public class Pioche {
private Carte[] cartes=new Carte[40];
private int carteRestante=39;


public Pioche initPioche() {
	Pioche pioche=new Pioche();
	return pioche;
}

public void modifCarteRestante() {
	this.carteRestante--;
}

public int getCarteRestante() {
	return carteRestante;
}
public void melanger() {
	
}


public Carte[] getPioche() {
return cartes;
}


public Carte donnerCarte() {
	if (carteRestante>0) {

		this.carteRestante--;
	Carte carte=this.cartes[carteRestante];
	return carte;
}

}
