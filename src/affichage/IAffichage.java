package affichage;

import jeu.Banc;
import jeu.Carte;
import jeu.Joueur;

public interface IAffichage {

	public static void afficherMain(Joueur joueur) {
		Carte[] main = joueur.getMain();
		Carte carte;
		int numCarte = 1;
		System.out.println("\nVos cartes sont : \n\n");
		for (int i = 0; i < joueur.getNbCarteEnMain(); i++) {
			carte = main[i];
			System.out.println("(" + numCarte + ")\t" + carte.getDescription().getNom() + " - effet : "
					+ carte.getDescription().getDescription() + " Type Carte : " + carte.getDescription().getType()
					+ "\n");
			numCarte++;
		}
	}

	public static void afficherBanc(Joueur joueur) {
		Banc objetBanc = joueur.getBanc();
		Carte[] tableauBanc = objetBanc.getBanc();

		if (joueur.getCarteBancRestante() != 0) {
			System.out.println("\nVos cartes de banc sont : \n");
			for (int i = 0; i < joueur.getCarteBancRestante(); i++) {
				System.out.println(tableauBanc[i].getDescription().getNom() + "\n");
			}
		} else {
			afficherBancVide();
		}
	}

	public default void afficherMsgCartePoseeSurBanc(Carte carte) {
		System.out.println("La carte " + carte.getDescription().getNom() + " est ajouté à votre banc");
	}

	public default void afficherCarteRemplacerSurBanc() {
		System.out.println(
				"\nIl n'y a plus de place sur votre banc veuillez choisir quel carte remplacer entre 1 et 5: ");
	}

	public static void afficherCartePoseeSurZoneAttaque(Carte carteZoneAttaque) {
		if (carteZoneAttaque != null) {
			System.out.println("La dernière carte jouer sur la zone d'attaque est :  "
					+ carteZoneAttaque.getDescription().getNom());
		} else {
			afficherZonneAttaqueVide();
		}
	}

	public static void afficherPiocheVide() {
		System.out.println("la pioche est vide");
	}

	public static void donnerContexte() {
		System.out.println(
				"Bienvenue dans le monde merveilleux de ONE PIECE... \nGold roger : Mon trésors ? Je vous le laisse Je l'ai laisser quelque pars dans ce monde.Trouver le !\n\n");
	}

	public static void donnerRegles() {
		System.out.println(
				"Pour gagner suivre les règles : \n\n Au début de la partie chaque joueur commence avec 5 pv et 0 de points de popularité. Pour gagné il faut soit obtenir 5 points de popularité soit que les pv de l'adversaire tombe à 0. Au début de chaque tour un joueur pioche puis il joue une carte au maximum. Le joueur est obligé de jouer. Au tour suivant l'autre joueur fait pareil\n\n");
	}

	public static void donnerStatusJoueur(Joueur joueur, Joueur adversaire) {
		int vieJ = joueur.getPv();
		int popJ = joueur.getPopularite();
		int vieA = adversaire.getPv();
		int popA = adversaire.getPopularite();
		afficherSeparation("");
		donnerStatusSubAdversaire(adversaire, vieA, popA);
		afficherSeparation("\n");
		donnerStatusSubJoueur(joueur, vieJ, popJ);

	}

	public static void donnerStatusSubJoueur(Joueur joueur, int vieJ, int popJ) {
		System.out
				.println("Status Joueur (" + joueur.getNom() + ") : " + "\nVie : " + vieJ + " \npopularité : " + popJ);
		afficherMain(joueur);
		if (joueur.getBanc() != null) {
			afficherBanc(joueur);
		} else {
			afficherBancVide();
		}
		if (joueur.getZoneAttaque() == null) {
			IAffichage.afficherZonneAttaqueVide();
		} else {
			IAffichage.afficherCartePoseeSurZoneAttaque(joueur.getZoneAttaque().getCarteZoneAttaque());

		}
	}

	public static void donnerStatusSubAdversaire(Joueur adversaire, int vieA, int popA) {
		System.out.println(
				"Status adversaire (" + adversaire.getNom() + ") : " + "\nVie : " + vieA + " \npopularité : " + popA);
		if (adversaire.getBanc() != null) {
			afficherBanc(adversaire);
		} else {
			afficherBancVide();
		}
		if (adversaire.getZoneAttaque() == null) {
			IAffichage.afficherZonneAttaqueVide();
		} else {
			IAffichage.afficherCartePoseeSurZoneAttaque(adversaire.getZoneAttaque().getCarteZoneAttaque());

		}
	}

	public static void afficherSeparation(String string) {
		System.out.println("--------------" + string);
	}

	public static void affichageDonnerJoueur(int num) {
		System.out.println("Joueur " + num + ", donner votre nom");
	}

	public static void afficherNbTour(int nbTour) {
		System.out.println("\n\n______________\nnb tour : " + nbTour + "\n\n");
	}

	public static void afficherChoisirCarte() {
		System.out.println("\nVeuillez choisir un nombre entre 1 et 5, correspondant à vous cartes");
	}

	public static void afficherBancVide() {
		System.out.println("Le banc est vide\n");
	}

	public static void afficherCarteJouer(String nom) {
		System.out.println("La carte jouer est : " + nom);
	}

	public static void afficherZonneAttaqueVide() {
		System.out.println("La zone d'attaque est vide");
	}

	/* Determination du gagnant */
	public static void afficherVictoire(Joueur joueur, Joueur adversaire) {
		String gagnant;
		String type;
		if (joueur.getPv() < 1) {
			gagnant = joueur.getNom();
			type = "assassinat";
		} else if (adversaire.getPv() < 1) {
			gagnant = adversaire.getNom();
			type = "assassinat";
		} else if (joueur.getPopularite() < 1) {
			gagnant = joueur.getNom();
			type = "popularité";
		} else {
			gagnant = adversaire.getNom();
			type = "popularité";
		}
		System.out.println("Le gagnant est : " + gagnant + " victoire par " + type);
	}

	public static void afficherErreurSwitchcase() {
		System.out.println("Erreur vous ne pouvez trier que le banc ou la main");
	}

	public static void afficherCarteMalPoser() {
		System.out.println("erreur la carte s'est mise sur la mauvaise zone");
	}

	public static void affichageBlocageDefensif() {
		System.out.println("Je suis invincible !");
	}

	public static void affichageEchangeForce() {
		System.out.println("\nAbrakadabra vos mains sont inversé");
	}

	public static void afficherChiffreTropGrand() {
		System.out.println("****  Erreur vous devez entrer un entier positif entre 1 et 5 ****");
	}

	public static void afficherEffetEchangeForce() {
		System.out.println("Euhh.. En faite non la carte n'est pas jouer, il s'est protégé rappel toi. Ce fut un tour bien inutile");
	}

}
