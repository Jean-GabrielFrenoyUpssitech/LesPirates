package jeu;

public class Banc {
	protected Carte[] banc = new Carte[5];
	private int cartePoseeBanc = 0;
public Banc(Carte carte) {
	this.banc[1]=carte;
}
	public void setCartePosee() {
		cartePoseeBanc++;
	}

	public int getCartePoseeBanc() {
		return cartePoseeBanc;
	}

	public Carte[] getBanc() {
		return banc;
	}

	public void ajouterCarte(Carte carte) {
		
			this.banc[cartePoseeBanc] = carte;

		
		cartePoseeBanc++;
	}

	public Carte modifierCarte(Carte carte, int nb) {
		Carte ancienneCarte = banc[nb];
		this.banc[nb] = carte;
		return ancienneCarte;

	}

}
