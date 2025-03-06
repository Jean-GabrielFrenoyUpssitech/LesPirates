package jeu;

import java.security.SecureRandom;

import affichage.IAffichage;

public class Joueur implements IAffichage {
	private String nom;
	private int pv;
	private int popularite;
	private Carte[] main = new Carte[5];
	private int nbCarteEnMain = 0;
	private SecureRandom random;
	private Banc banc;

	public Joueur(String nom) {
		this.nom = nom;
		try {
			random = SecureRandom.getInstanceStrong();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

		Carte[] tableauPioche = ObjetPioche.getPioche();
		if (this.nbCarteEnMain == 0) {

			this.main[this.nbCarteEnMain] = tableauPioche[ObjetPioche.getCarteRestante()];
		} else {
			if (ObjetPioche.getCarteRestante() > 0) {

				this.main[this.nbCarteEnMain + 1] = ObjetPioche.donnerCarte();
				ObjetPioche.modifCarteRestante();
				if (this.nbCarteEnMain < 5) {
					this.nbCarteEnMain++;
				}
			} else {
				afficherPiocheVide();
			}

		}
	}

	public void creeMain(Pioche ObjetPioche) {

		for (int i = 0; i < 6; i++) {
			this.piocher(ObjetPioche);
		}
	}

	
	public Carte[] getMain() {
		return this.main;
	}
	public void getAfficherMain(Joueur joueur) {
		getAfficherMain(joueur);
			
		}
	}
public Banc getBanc() {
	return this.banc;
}
public int getCarteBancRestante() {
	int nbCarteBancRestante = banc.getCarteRestante();
	return nbCarteBancRestante;
}
}
