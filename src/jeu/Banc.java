package jeu;

import java.util.Scanner;

public class Banc extends Zone {
	private Scanner scaner = new Scanner(System.in);
	private int cartePosee = 0;

	public Banc(Carte[] cartes) {
		super();
	}


	public int getCartePosee() {
		return cartePosee;
	}

	public Carte[] getBanc() {
		return cartes;
	}

	@Override
	public void ajouterCarte(Carte carte) {
		cartes[cartePosee + 1] = carte;
		cartePosee++;
	}

	public Carte modifierCarte(Carte carte) {
		int nb = scaner.nextInt();
		Carte ancienneCarte=cartes[nb];
		cartes[nb]=carte;
		return ancienneCarte;

	}

}
