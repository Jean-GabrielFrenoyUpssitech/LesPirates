package jeu;

import java.util.Scanner;

import affichage.IAffichage;


public class Jeu implements IAffichage {
	private Pioche pioche;
	private ZoneAttaque zoneAttaque;
	private Banc banc;
	private Joueur[] joueurs = new Joueur[2];
	private static Scanner scaner = new Scanner(System.in);

	public Jeu(Joueur[] joueurs) {
		this.joueurs = joueurs;
	}

	public static String donnerJoueur(int numJoueur) {
		IAffichage.affichageDonnerJoueur(numJoueur);
		String joueur = scaner.next();
		IAffichage.affichageJoueur(numJoueur, joueur);
		return joueur;

	}
	public void initJeu() {
		
		//faire piocher les joueurs aussi
	}
	
	public static void main(String[] args) {
		
		String joueur1 = donnerJoueur(1);
		String joueur2 = donnerJoueur(2);
		
		IAffichage.donnerContexte();
		IAffichage.donnerRegles("regle");
		
		int i = 0;
		
		joueu = piocher(mainJoueur1);
		afficherMain(mainJoueur1);
		donnerStatusJoueur1(joueur1, 5, 0, mainJoueur1, bancJoueur1);
		donnerStatusJoueur2(joueur2, 5, 0, bancJoueur2);
		utiliserCarte(mainJoueur1);
	}

	
}
