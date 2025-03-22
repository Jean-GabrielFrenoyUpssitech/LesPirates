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
	private Carte derniereCarteJouer;
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

	public int modifierVie(int valDeVie) {
		int vie;

		this.pv += valDeVie;
		vie = this.getPv();

		return vie;

	}

	public int modifierPop(int valDePop) {
		int pop;

		this.popularite += +valDePop;
		pop = this.getPopularite();

		return pop;

	}

	public int getNbCarteEnMain() {

		return nbCarteEnMain;
	}

	public void piocher(Pioche objetPioche) {
		while (this.nbCarteEnMain < 5) {
			Carte carte = objetPioche.donnerCarte(objetPioche);
			if (objetPioche.getCarteRestantePioche() < 1) {
				afficherPiocheVide();
			} else {
				if (this.nbCarteEnMain == 0) {
					this.main[this.nbCarteEnMain] = carte;

				} else {
					this.main[this.nbCarteEnMain] = carte;
					if (this.nbCarteEnMain < 5) {
					}
				}
				this.nbCarteEnMain++;

			}
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
		return banc.getCartePoseeBanc();
	}

	public void jouerCarteSurBanc(Carte carte, Joueur adversaire) {
		if (this.banc.getCartePoseeBanc() < 5) {
			this.banc.ajouterCarte(carte);
		} else {
			int nbCarteRemplacer;
			do {
				afficherCarteRemplacerSurBanc();
				nbCarteRemplacer = scaner.nextInt();
				if (nbCarteRemplacer < 1 || nbCarteRemplacer > 5) {
					IAffichage.afficherChiffreTropGrand();
				}
			} while (nbCarteRemplacer < 1 || nbCarteRemplacer > 5);

			Carte ancienneCarte = this.banc.modifierCarte(carte, nbCarteRemplacer - 1);
			ancienneCarte.retirerEffet(this);
		}
	}

	public void jouerCarteSurZoneAttaque(Carte carte, Joueur adversaire) {
		zoneAttaque.ajouterCarte(carte);
	}

	public void jouerCarte(Joueur adversaire) {
		int nbCarteAJouer = getNbCarteAJouer();
		Carte carte = this.getMain()[nbCarteAJouer - 1];
		this.main[nbCarteAJouer - 1] = null;
		this.nbCarteEnMain--;
		this.trierCarte(this, nbCarteAJouer);
		IAffichage.afficherCarteJouer(carte.getDescription().getNom());
		String typeCarte = carte.getDescription().getType();
		placerCarteSurZone(carte,adversaire,typeCarte);
		String nomDerniereCarte = null;
		if (adversaire.derniereCarteJouer != null) {
			nomDerniereCarte = adversaire.derniereCarteJouer.getDescription().getNom();
		}
		if ("Blocage Défensif".equals(nomDerniereCarte)) {
			IAffichage.affichageBlocageDefensif();
		} else {
			carte.appliquerEffet(this, adversaire);
		}
		this.derniereCarteJouer = carte;
	}

	private void placerCarteSurZone(Carte carte, Joueur adversaire,String typeCarte) {
		switch (typeCarte) {
		case "Popularité":
			if (this.banc == null) {
				this.banc = new Banc(carte);
			}
			jouerCarteSurBanc(carte, adversaire);
			break;
		case "PV":
			if (this.zoneAttaque == null) {
				zoneAttaque = new ZoneAttaque(carte);
			}
			jouerCarteSurZoneAttaque(carte, adversaire);
			break;
		default:
			if (!"Echange Forcé".equals(carte.getDescription().getNom())) {
				IAffichage.afficherCarteMalPoser();
			}else {
				IAffichage.afficherEffetEchangeForce();

			}
			break;
		}		
	}

	private int getNbCarteAJouer() {
		int nbCarte;
		do {
			IAffichage.afficherChoisirCarte();
			nbCarte = scaner.nextInt();
			if (nbCarte < 1 || nbCarte > 5) {
				IAffichage.afficherChiffreTropGrand();
			}
		} while (nbCarte < 1 || nbCarte > 5);
		return nbCarte;
	}

	public Joueur initJoueur(Pioche objetPioche) {
		for (int i = 0; i < 4; i++) {

			this.piocher(objetPioche);
		}
		return this;
	}

	public void trierCarte(Joueur joueur, int numCarte) {
		if (numCarte != 5) {
			int test = 5 - numCarte;

			for (int i = 0; i < test; i++) {
				joueur.main[numCarte - 1 + i] = joueur.main[numCarte + i];

			}

		}
	}

	public ZoneAttaque getZoneAttaque() {
		return zoneAttaque;
	}

	public void setMain(Carte[] mainAdv) {
		main = mainAdv;
	}

}
