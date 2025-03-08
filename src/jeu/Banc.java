package jeu;

import java.util.Scanner;

public class Banc extends Zone {
	private Scanner scaner = new Scanner(System.in);
	private int cartePosee = 0;

	public Banc(Carte[] cartes) {
		super();
	}

	public void modifCarteNbRestante() {
		this.cartePosee--;
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
	}

	public void modifierCarte(Carte carte) {
		int nb = scaner.nextInt();
		cartes[nb]=carte;

	}

}
