package jeu;

public class PlanMachiavelique extends Carte {

	public PlanMachiavelique(Description description) {
		super(description, -3, -1);
	}

	@Override
	protected void appliquerEffet(Joueur joueur, Joueur adversaire) {
		if (adversaire.getPv() > 3) {
			adversaire.modifierVie(modifVie);
		}
		joueur.modifierPop(modifPop);

	}

}
