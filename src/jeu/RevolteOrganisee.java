package jeu;

public class RevolteOrganisee extends Carte {

	public RevolteOrganisee(Description description) {
		super(description, 0, 1);
		}
		@Override
		protected void appliquerEffet(Joueur joueur, Joueur adversaire) {
			joueur.modifierVie(modifVie);
			joueur.modifierPop(modifPop);	}

}
