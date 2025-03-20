package jeu;

public class Banc {
	protected Carte[] banc = new Carte[5];
	private int cartePosee = 0;
public Banc(Carte carte) {
	this.banc[1]=carte;
}
	public void setCartePosee() {
		cartePosee++;
	}

	public int getCartePosee() {
		return cartePosee;
	}

	public Carte[] getBanc() {
		return banc;
	}

	public void ajouterCarte(Carte carte) {
		
			this.banc[cartePosee] = carte;

		
		cartePosee++;
	}

	public Carte modifierCarte(Carte carte, int nb) {
		Carte ancienneCarte = banc[nb];
		this.banc[nb] = carte;
		return ancienneCarte;

	}

}
