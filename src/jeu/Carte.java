package jeu;

public abstract class  Carte {
	private Effets effetCarte;

	public Carte(Effets effet) {
		this.effetCarte = effet;
	}


	protected Effets getEffetCarte() {
		return effetCarte;
	}
	//MAINDEFER(2,"du joueur",-1,"du joueur"),REVOLTEORGANISEE(1,"du joueur",0,"du joueur"),DISCOURSINSPIRANT(1,"du joueur",0,"du joueur"),ABORDAGEREUSSI(1,"du joueur",0,"du joueur"),COUPDESABRE(0,"du joueur",-2,"de l'adversaire");
	protected abstract void appliquerEffet();
}
