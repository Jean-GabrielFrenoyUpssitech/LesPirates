package affichage;

import java.util.Scanner;

import jeu.Carte;
import jeu.Joueur;
import jeu.Description;

public class AffichagePreview {

	private Scanner scaner = new Scanner(System.in);

	public void donnerContexte() {
		System.out.println(
				"Bienvenue dans le monde merveilleux de ONE PIECE... \nGold roger : Mon trésors ? Je vous le laisse Je l'ai laisser quelque pars dans ce monde.Trouver le !\n\n");
	}

	public void donnerRegles(String regles) {
		System.out.println("Pour gagner suivre les règles : \n\n" + regles + "\n\n");

		System.out.println("Les Vaillant pirates sont : \n");
	}

	public String donnerJoueur(int numJoueur) {
		String joueur = scaner.next();
		System.out.println(
				"le joueur " + numJoueur + " se nomme : " + joueur + " et commence avec : \nVie =5 \npopularite = 0\n");
		return joueur;
	}

	

	public void afficherNbTour(int nbTour) {
		System.out.println("nb tour : " + nbTour + "\n\n");
	}

	public String[] piocher(String[] main) {
		main[4] = "carte Z";
		return main;
	}

	private void donnerStatusJoueur1(String nomJoueur, int vie, int pop, String[] main, String[] banc) {
		System.out.println(nomJoueur + " : " + vie + "Vie / " + pop + " popularité\n");
		afficherMain(main);
		afficherBanc(banc);

	}

	private void afficherBanc(String[] banc) {
		System.out.println("Banc : ");
		for (int i = 0; i < 2; i++) {
			System.out.println("- " + banc[i]);
		}
	}

	private void afficherMain(String[] main) {
		System.out.println("Main : ");

		for (int i = 0; i < 2; i++) {
			System.out.println("- " + main[i]);
		}
	}

	private void donnerStatusJoueur2(String nomJoueur, int vie, int pop, String[] banc) {
		System.out.println(nomJoueur + " : " + vie + "Vie / " + pop + " popularité\n" + "banc : " + banc);
		afficherBanc(banc);
	}

	private void getTourJoueur() {
		/*
		 * int nbCarteEnMain; int numCarte;
		 * System.out.println("Combien de carte en main il vous reste ?");// temporaire
		 * juste pour l'affichage nbCarteEnMain = scaner.nextInt(); if (nbCarteEnMain >=
		 * 5) { System.out.
		 * println("Votre main est déjà pleine vous ne piocherez pas de carte ce tour-ci"
		 * ); nbCarteEnMain = 5; } else if (nbCarteEnMain < 1) {
		 * System.out.println("Comment veux-tu avoir un nombre négatif de cartes T-T");
		 * } else { System.out.println("Vous piocher une carte"); nbCarteEnMain++; }
		 * 
		 * System.out.println("Vous avez " + nbCarteEnMain +
		 * " carte en main.\nChoisissait la carte à utiliser, taper un chiffre entre 1 et "
		 * + nbCarteEnMain); // futur affichage de toute les cartes en main numCarte =
		 * scaner.nextInt();
		 * 
		 * if (numCarte < 1) { System.out.println("Pas de carte négatif"); } else if
		 * (numCarte < nbCarteEnMain + 1) {
		 * System.out.println("J'ACTIVE LA CARTE NUMERO " + numCarte +
		 * " DE MA MAIN ! ELLE SE NOMME xxxx ET FAIT xxxx !!!!\n\n"); } else {
		 * System.out.println("Oups je suis aller trop loin"); nbCarteEnMain++; }
		 * nbCarteEnMain--;
		 * 
		 * System.out.println("Il vous reste " + nbCarteEnMain +
		 * " carte en main.\nfin de tour !\n\n");// futur affichage // de toute les
		 */
		// cartes en main

		System.out.println("vus piocher");
	}

	private void utiliserCarte(String[] mainJoueur1) {
		System.out.println("Choisissait la carte à utiliser, taper un chiffre entre 1 et 5"); // futur affichage de
																								// toute les cartes en
																								// main
		int numCarte = scaner.nextInt();
		if (numCarte < 1) {
			System.out.println("Pas de carte négatif");
		} else if (numCarte < 5) {

			System.out.println("J'ACTIVE LA CARTE NUMERO " + numCarte + " DE MA MAIN ! ELLE SE NOMME"
					+ mainJoueur1[numCarte] + " ET FAIT xxxx !!!!\n\n");
		} else {
			System.out.println("Oups je suis aller trop loin");
		}
	}

	public static void main(String[] args) {
		AffichagePreview affichage = new AffichagePreview();
		affichage.donnerContexte();
		affichage.donnerRegles("regle");
		String joueur1 = affichage.donnerJoueur(1);
		String joueur2 = affichage.donnerJoueur(2);

		int i = 0;
		String[] mainJoueur1 = { "CarteA", "CarteB", "CarteA", "CarteB", "" };
		String[] bancJoueur1 = { "CarteC", "CarteB", "CarteA", "CarteB","" };
		String[] bancJoueur2 = { "CarteD", "CarteB" };
		affichage.afficherNbTour(1);
		mainJoueur1 = affichage.piocher(mainJoueur1);
		affichage.afficherMain(mainJoueur1);
		affichage.donnerStatusJoueur1(joueur1, 5, 0, mainJoueur1, bancJoueur1);
		affichage.donnerStatusJoueur2(joueur2, 5, 0, bancJoueur2);
		affichage.utiliserCarte(mainJoueur1);
	}
	

}
