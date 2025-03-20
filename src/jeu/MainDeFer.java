package jeu;

public class MainDeFer extends Carte{
	
	public MainDeFer(Description description) {
		super(description,-1,2);
	}
	@Override
	protected void appliquerEffet(Joueur joueur, Joueur adversaire) {
		joueur.modifierVie(modifVie);
		joueur.modifierPop(modifPop);

	}
	

}
