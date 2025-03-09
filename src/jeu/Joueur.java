package jeu;

import affichage.IAffichage;
import java.util.Scanner;

public class Joueur implements IAffichage {
	private String nom;
	private int pv=5;
	private int popularite=0;
	private Carte[] main = new Carte[5];
	private int nbCarteEnMain = 0;
	private Banc banc;
	private ZoneAttaque zoneAttaque;
	private static Scanner scaner = new Scanner(System.in);

	public Joueur(String nom) {
		this.nom = nom;

	}

	public String getNom() {
		return nom;
	}

	public int getPv() {
		return pv;
	}

	public int getPopularite() {
		return popularite;
	}

	public int modifierVie(Joueur joueur, int degat) {
		int vie;

		joueur.pv = joueur.getPv() - degat;
		vie = joueur.getPv();

		return vie;

	}

	public int modifierPop(Joueur joueur, int populariteEnMoins) {
		int pop;

		joueur.popularite = joueur.getPopularite() - populariteEnMoins;
		pop = joueur.getPopularite();

		return pop;

	}

	public int getNbCarte() {

		return nbCarteEnMain;
	}

	public void piocher(Pioche ObjetPioche) {
		// piocher la premier carte de la pioche donc dernier element et c'est ok car
		// t'as déjà melanger

		if (ObjetPioche.getCarteRestante() < 1) {
			afficherPiocheVide();

		} else {
			if (this.nbCarteEnMain == 0) {
				this.main[this.nbCarteEnMain] = ObjetPioche.donnerCarte();
				this.nbCarteEnMain++;

			} else {
				this.main[this.nbCarteEnMain] = ObjetPioche.donnerCarte();
				if (this.nbCarteEnMain < 5) {
					this.nbCarteEnMain++;
				}
			}

		}
	}

	public void creeMain(Pioche ObjetPioche) {

		for (int i = 0; i < 4; i++) {
			this.piocher(ObjetPioche);
		}
	}

	public Carte[] getMain() {
		return this.main;
	}

	public void getAfficherMain(Joueur joueur) {
		getAfficherMain(joueur);

	}

	public Banc getBanc() {
		return this.banc;
	}

	public int getCarteBancRestante() {
		int carteBancRestante = banc.getCartePosee();
		return carteBancRestante;
	}

	public void jouerCarteSurBanc(Carte carte, Joueur joueur) {
		if (banc.getCartePosee() < 6) {
			afficherMsgCartePoseeSurBanc(carte);
			banc.ajouterCarte(carte);
			
		}

		else {
			afficherCarteRemplacerSurBanc();
			IAffichage.afficherBanc(joueur);
			Carte ancienneCarte = banc.modifierCarte(carte);
			joueur.popularite = joueur.popularite - ancienneCarte.getEffetCarte().getPop();
			joueur.pv = joueur.pv - ancienneCarte.getEffetCarte().getPv();


		}

		joueur.popularite = joueur.popularite + carte.getEffetCarte().getPop();
		joueur.pv = joueur.pv + carte.getEffetCarte().getPv();

	}

	public void jouerCarteSurZoneAttaque(Carte carte,Joueur joueur) {
		zoneAttaque.ajouterCarte(carte);
		joueur.pv = joueur.pv + carte.getEffetCarte().getPv();

	}

	public void jouerCarte() {
		IAffichage.afficherChoisirCarte();
		int nbCarte = scaner.nextInt();
		Carte carte = this.getMain()[nbCarte-1];
		if (carte.getType() == "Popularité") {
			jouerCarteSurBanc(carte, this);
		} else {
			jouerCarteSurZoneAttaque(carte,this);
		}
	}

	public Joueur initJoueur(Pioche pioche) {
		this.creeMain(pioche);
		return this;
	}
}
