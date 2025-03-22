package jeu;

import affichage.IAffichage;

public class BlocageDefensif extends Carte implements IAffichage {

	public BlocageDefensif(Description description) {
		super(description, 0, 0);
	}

	@Override
	protected void appliquerEffet(Joueur joueur, Joueur adversaire) {
		IAffichage.affichageBlocageDefensif();

		
	}

}
