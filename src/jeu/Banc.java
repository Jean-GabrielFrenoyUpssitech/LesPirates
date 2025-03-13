package jeu;

import java.util.Scanner;

public class Banc extends Zone {
	private Scanner scaner = new Scanner(System.in);
	private int cartePosee = 0;

	public Banc(Carte carte) {
		super();
		this.cartes[1]=carte;
	}
public void setCartePosee() {
	cartePosee++;
}

	public int getCartePosee() {
		return cartePosee;
	}

	public Carte[] getBanc() {
		return cartes;
	}
public void afficherBanc() {
}
	@Override
	public void ajouterCarte(Carte carte) {
		if(this.cartePosee ==0) {
			this.cartes[cartePosee] = carte;
		}else {
		this.cartes[cartePosee + 1] = carte;
		
		}
		cartePosee++;
	}

	public Carte modifierCarte(Carte carte) {
		int nb = scaner.nextInt();
		Carte ancienneCarte=cartes[nb];
		cartes[nb]=carte;
		return ancienneCarte;

	}

}
