package jeu;

import java.security.SecureRandom;


public class Pioche {
private Carte[] piocheTableau=new Carte[40];
private int carteRestante=39;
private SecureRandom random;

public void pioche() {
	try {
		random = SecureRandom.getInstanceStrong();
	} catch (Exception e) {
		e.printStackTrace();
	}
}

public Pioche initPioche() {

	Pioche piocheObjet=new Pioche();
	Carte revolteOrganisee = new Carte("Popularité", "Révolte Organisée", Effets.REVOLTEORGANISEE);
	Carte mainDeFer = new Carte("Popularité", "Révolte Organisée", Effets.MAINDEFER);
	Carte coupDeSabre= new Carte("PV", "Coup de Sabre", Effets.COUPDESABRE);
	Carte abordageReussi = new Carte("popularité", "Abordage Réussi", Effets.MAINDEFER);
	Carte discoursInspirant = new Carte("popularité", "Discours Inspirant", Effets.MAINDEFER);

	piocheObjet.piocheTableau[1]=mainDeFer;
	piocheObjet.piocheTableau[2]=mainDeFer;
	piocheObjet.piocheTableau[3]=mainDeFer;
	piocheObjet.piocheTableau[4]=mainDeFer;
	piocheObjet.piocheTableau[5]=mainDeFer;
	piocheObjet.piocheTableau[6]=mainDeFer;
	piocheObjet.piocheTableau[7]=mainDeFer;
	piocheObjet.piocheTableau[8]=mainDeFer;
	piocheObjet.piocheTableau[9]=revolteOrganisee;
	piocheObjet.piocheTableau[10]=revolteOrganisee;
	piocheObjet.piocheTableau[11]=revolteOrganisee;
	piocheObjet.piocheTableau[12]=revolteOrganisee;
	piocheObjet.piocheTableau[13]=revolteOrganisee;
	piocheObjet.piocheTableau[14]=revolteOrganisee;
	piocheObjet.piocheTableau[15]=revolteOrganisee;
	piocheObjet.piocheTableau[16]=revolteOrganisee;
	piocheObjet.piocheTableau[17]=revolteOrganisee;
	piocheObjet.piocheTableau[18]=revolteOrganisee;
	piocheObjet.piocheTableau[19]=revolteOrganisee;
	piocheObjet.piocheTableau[20]=revolteOrganisee;
	piocheObjet.piocheTableau[21]=revolteOrganisee;
	piocheObjet.piocheTableau[22]=coupDeSabre;
	piocheObjet.piocheTableau[23]=coupDeSabre;
	piocheObjet.piocheTableau[24]=coupDeSabre;
	piocheObjet.piocheTableau[25]=coupDeSabre;
	piocheObjet.piocheTableau[26]=abordageReussi;
	piocheObjet.piocheTableau[27]=abordageReussi;
	piocheObjet.piocheTableau[28]=abordageReussi;
	piocheObjet.piocheTableau[29]=abordageReussi;
	piocheObjet.piocheTableau[30]=abordageReussi;
	piocheObjet.piocheTableau[31]=abordageReussi;
	piocheObjet.piocheTableau[32]=discoursInspirant;
	piocheObjet.piocheTableau[33]=discoursInspirant;
	piocheObjet.piocheTableau[34]=discoursInspirant;
	piocheObjet.piocheTableau[35]=discoursInspirant;
	piocheObjet.piocheTableau[36]=discoursInspirant;
	piocheObjet.piocheTableau[37]=discoursInspirant;
	piocheObjet.piocheTableau[38]=coupDeSabre;
	piocheObjet.piocheTableau[39]=coupDeSabre;
	piocheObjet.piocheTableau[40]=coupDeSabre;

	
	return piocheObjet;
}


public int getCarteRestante() {
	return carteRestante;
}
public void melanger() {
	int nbMelange;

	for (int i = carteRestante; i > 0; i--) {
		do {
		nbMelange= random.nextInt(0, carteRestante);
		}while (nbMelange==i);
		
        Carte temp = piocheTableau[i];
        piocheTableau[i] = piocheTableau[nbMelange];
        piocheTableau[nbMelange] = temp;
    }

}


public Carte[] getPioche() {
return piocheTableau;
}


public Carte donnerCarte() {
	
	Carte carte=this.piocheTableau[carteRestante];
	carteRestante--;
	return carte;

}
}
