package jeu;

public abstract class  Carte {
	private Description description;
	protected int modifVie;
	protected int modifPop;

	public Carte(Description description, int modifVie, int modifPop) {
		this.description = description;
		this.modifVie=modifVie;
		this.modifPop=modifPop;
	}


	public Description getEffetCarte() {
		return description;
	}
	//MAINDEFER(2,"du joueur",-1,"du joueur"),REVOLTEORGANISEE(1,"du joueur",0,"du joueur"),DISCOURSINSPIRANT(1,"du joueur",0,"du joueur"),ABORDAGEREUSSI(1,"du joueur",0,"du joueur"),COUPDESABRE(0,"du joueur",-2,"de l'adversaire");
	protected abstract void appliquerEffet(Joueur joueur, Joueur adversaire);
	public int getModifVie() {
		return modifVie;
	}

	public int getModifPop() {
		return modifPop;
	}
}
