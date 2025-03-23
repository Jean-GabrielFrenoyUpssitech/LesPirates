package jeu;


public abstract class  Carte {
	private Description description;
	protected int modifVie;
	protected int modifPop;

	protected Carte(Description description, int modifVie, int modifPop) {
		this.description = description;
		this.modifVie=modifVie;
		this.modifPop=modifPop;
	}


	public Description getDescription() {
		return description;
	}
	protected abstract void appliquerEffet(Joueur joueur, Joueur adversaire);
	public int getModifVie() {
		return modifVie;
	}

	public int getModifPop() {
		return modifPop;
	}
	/* Permet de retirer l'effet de la carte car elle a été retirer du banc */
	public void retirerEffet(Joueur joueur) {
		joueur.modifierVie(-this.getModifVie());
		joueur.modifierPop(-this.getModifPop());
	}
	
}