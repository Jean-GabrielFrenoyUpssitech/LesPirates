package jeu;

public class Banc {
	private Carte[] bancTableau = new Carte[5];
	private int cartePoseeBanc = 0;
public Banc(Carte carte) {
	this.bancTableau[1]=carte;
}
	public void setCartePosee() {
		cartePoseeBanc++;
	}

	public int getCartePoseeBanc() {
		return cartePoseeBanc;
	}

	public Carte[] getBanc() {
		return bancTableau;
	}

	public void ajouterCarte(Carte carte) {
		
			this.bancTableau[cartePoseeBanc] = carte;

		
		cartePoseeBanc++;
	}

	public Carte modifierCarte(Carte carte, int nb) {
		Carte ancienneCarte = bancTableau[nb];
		this.bancTableau[nb] = carte;
		return ancienneCarte;

	}

}
