package jeu;

public class AbordageReussi extends Carte {

	public AbordageReussi(Description description) {
		super(description, 0, 1);
	}

	@Override
	protected void appliquerEffet(Joueur joueur, Joueur adversaire) {
		joueur.modifierVie(modifVie);
		joueur.modifierPop(modifPop);
		
	}
}
