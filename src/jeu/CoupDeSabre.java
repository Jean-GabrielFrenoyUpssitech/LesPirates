package jeu;

public class CoupDeSabre extends Carte {

	public CoupDeSabre(Description description) {
		super(description, -2, 0);
	}

	@Override
	protected void appliquerEffet(Joueur joueur, Joueur adversaire) {
		adversaire.modifierVie(modifVie);
		
	}

}
