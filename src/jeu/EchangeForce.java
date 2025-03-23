package jeu;

import affichage.IAffichage;

public class EchangeForce extends Carte implements IAffichage {

	public EchangeForce(Description description) {
		super(description, 0, -2);
	}


	@Override
	protected void appliquerEffet(Joueur joueur, Joueur adversaire) {
		Joueur intermediaire = new Joueur();
		intermediaire.setMain(adversaire.getMain());
		adversaire.setMain(joueur.getMain());
		joueur.setMain(intermediaire.getMain());	
		joueur.modifierPop(modifPop);
		IAffichage.affichageEchangeForce();
	}

}
