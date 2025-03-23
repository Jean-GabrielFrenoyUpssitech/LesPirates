package jeu;

import java.security.SecureRandom;


public class Pioche {
private Carte[] piocheTableau=new Carte[40];
private int carteRestante=39;
private SecureRandom random;

public Pioche(Carte carte) {
this.piocheTableau[0]=carte;
}


public int getCarteRestantePioche() {
	return carteRestante;
}
public void melanger() {
	int nbMelange;

	for (int i = carteRestante; i > 0; i--) {
		do {
		nbMelange= random.nextInt(carteRestante+1);
		}while (nbMelange==i);
		
        Carte temp = getPiocheTableau()[i];
        getPiocheTableau()[i] = getPiocheTableau()[nbMelange];
        getPiocheTableau()[nbMelange] = temp;
    }

}


public Carte[] getPioche() {
return getPiocheTableau();
}


public Carte donnerCarte(Pioche objetPioche) {

	Carte carte=objetPioche.getPiocheTableau()[carteRestante];
	carteRestante--;
	return carte;

}




public Carte[] getPiocheTableau() {
	return piocheTableau;
}




public void setPiocheTableau(Carte[] piocheTableau) {
	this.piocheTableau = piocheTableau;
}
}