package jeu;

import java.security.SecureRandom;

public class Joueur {
	private String nom;
	private int pv;
	private int popularite;
	private Carte[] main = new Carte[5];
	private int nbCarteEnMain;

	public Joueur(String nom, int pv, int popularite) {
		this.nom = nom;
		this.pv = pv;
		this.popularite = popularite;
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

	public int perdreVie(Joueur joueur, int degat, Boolean moi) {
		int vie;
		if (moi) {

			this.pv = this.getPv() - degat;
			vie = joueur.getPv();

		} else {
			joueur = new Joueur(joueur.getNom(), joueur.getPv() - degat, joueur.getPopularite());
			vie = joueur.getPv();
		}
		return vie;

	}

	public int perdrePopadv(Joueur joueur, int populariteEnMoins, Boolean moi) {

		int pop;
		if (moi) {

			this.pv = this.getPv() - populariteEnMoins;
			pop = joueur.getPv();

		} else {
			joueur = new Joueur(joueur.getNom(), joueur.getPv(), joueur.getPopularite() - populariteEnMoins);
			pop = joueur.getPv();
		}
		return pop;

	}

	public int getNbCarte() {

		return nbCarteEnMain;
	}

	public boolean getGagnant() {
		boolean gagnant = false;
		if (this.getPopularite() > 4 || this.getPv() < 1) {
			gagnant = true;
		}
		return gagnant;
	}

	public Carte[] getMain(Joueur joueur) {
		return joueur.getMain(joueur);

	}

	/*public void getPiocher() {
		if (nbCarteEnMain > 5) {

		} else {

		}
	}*/
}
