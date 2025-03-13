package jeu;

import affichage.IAffichage;
import java.util.Scanner;

public class Joueur implements IAffichage {
	private String nom;
	private int pv = 5;
	private int popularite = 0;
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
		Carte carte = ObjetPioche.donnerCarte();

		if (ObjetPioche.getCarteRestante() < 1) {
			afficherPiocheVide();

		} else {
			if (this.nbCarteEnMain == 0) {
				this.main[this.nbCarteEnMain] = carte;
				this.nbCarteEnMain++;

			} else {
				this.main[this.nbCarteEnMain] = carte;
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
		return banc;
	}

	public int getCarteBancRestante() {
		int carteBancRestante = banc.getCartePosee();
		return carteBancRestante;
	}

	public void jouerCarteSurBanc(Carte carte, Joueur adversaire) {
		if (banc.getCartePosee() < 6) {
			this.banc.ajouterCarte(carte);

		}

		else {
			afficherCarteRemplacerSurBanc();
			Carte ancienneCarte = banc.modifierCarte(carte);
			if (ancienneCarte.getEffetCarte().getCibleEffetPop().equals("du joueur")) {
				this.popularite = this.popularite - ancienneCarte.getEffetCarte().getPop();
			} else {
				adversaire.popularite = adversaire.popularite - ancienneCarte.getEffetCarte().getPop();

			}if (ancienneCarte.getEffetCarte().getCibleEffetPv().equals("du joueur")) {
				this.pv = this.pv - ancienneCarte.getEffetCarte().getPv();
			} else {
				adversaire.pv = adversaire.pv + carte.getEffetCarte().getPv();
			}


		}
		if (carte.getEffetCarte().getCibleEffetPop().equals("du joueur")) {
			this.popularite = this.popularite + carte.getEffetCarte().getPop();
		} else {
			adversaire.popularite = adversaire.popularite + carte.getEffetCarte().getPop();

		}if (carte.getEffetCarte().getCibleEffetPv().equals("du joueur")) {
			this.pv = this.pv + carte.getEffetCarte().getPv();
		} else {
			adversaire.pv = adversaire.pv + carte.getEffetCarte().getPv();
		}

	}

	public void jouerCarteSurZoneAttaque(Carte carte, Joueur adversaire) {
		zoneAttaque.ajouterCarte(carte);
		adversaire.pv = adversaire.pv + carte.getEffetCarte().getPv();

	}

	public void jouerCarte(Joueur adversaire) {
		IAffichage.afficherChoisirCarte();
		int nbCarte = scaner.nextInt();
		Carte carte = this.getMain()[nbCarte - 1];
		this.main[nbCarte - 1] = null;
		this.nbCarteEnMain--;
		trierCarte(this, nbCarte);
		IAffichage.afficherCarteJouer(carte.getNom());

		if (carte.getType() == "Popularité") {
			if (this.banc == null) {
				banc = new Banc(carte);
				jouerCarteSurBanc(carte, adversaire);
			} else {
				jouerCarteSurBanc(carte, adversaire);

			}
		} else {
			if (this.zoneAttaque == null) {
				zoneAttaque = new ZoneAttaque(carte);
				jouerCarteSurZoneAttaque(carte, adversaire);
			} else {
				jouerCarteSurZoneAttaque(carte, adversaire);

			}
		}
	}

	public Joueur initJoueur(Pioche pioche) {
		this.creeMain(pioche);
		return this;
	}

	public void trierCarte(Joueur joueur, int num) {
		if (num != 5) {
			int test = 5 - num;
			for (int i = 0; i < test; i++) {
				joueur.main[num - 1 + i] = joueur.main[num + i];

			}
		}
	}

	public ZoneAttaque getZoneAttaque() {
		return zoneAttaque;
	}
}
