package jeu;

public class DiscoursInspirant extends Carte {

	public DiscoursInspirant(Description description) {
		super(description, 0, 1);
	}

	@Override
	protected void appliquerEffet(Joueur joueur, Joueur adversaire) {
		joueur.modifierVie(modifVie);
		joueur.modifierPop(modifPop);
		
	}

}
