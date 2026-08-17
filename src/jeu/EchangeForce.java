package jeu;

import affichage.IAffichage;

public class EchangeForce extends Carte implements IAffichage {

	public EchangeForce(Description description) {
		super(description, 0, -2);
	}


	@Override
	protected void appliquerEffet(Joueur joueur, Joueur adversaire) {
		joueur.echangerMainAvec(adversaire);
		joueur.modifierPop(modifPop);
		IAffichage.affichageEchangeForce();
	}

}
