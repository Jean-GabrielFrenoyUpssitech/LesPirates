package jeu;

import java.util.Scanner;

import affichage.IAffichage;

public class Jeu implements IAffichage {
	private Pioche pioche;
	private ZoneAttaque zoneAttaque;
	private Joueur[] joueurs = new Joueur[2];
	private static Scanner scaner = new Scanner(System.in);

	public Jeu(Joueur[] joueurs, Pioche pioche, ZoneAttaque zoneAttaque, Banc banc) {
		this.joueurs = joueurs;
		this.pioche = pioche;
		this.zoneAttaque = zoneAttaque;
	}

	public static Joueur donnerJoueur(int numJoueur) {
		IAffichage.affichageDonnerJoueur(numJoueur);
		String nom = scaner.next();
		Joueur joueur = new Joueur(nom);
		return joueur;

	}

	public void initJoueur(Joueur joueur1) {

	}

	public static Pioche initPioche() {

		Pioche piocheObjet = new Pioche();
		Carte revolteOrganisee = new Carte("Popularité", "Révolte Organisée", Effets.REVOLTEORGANISEE);
		Carte mainDeFer = new Carte("Popularité", "Révolte Organisée", Effets.MAINDEFER);
		Carte coupDeSabre = new Carte("PV", "Coup de Sabre", Effets.COUPDESABRE);
		Carte abordageReussi = new Carte("popularité", "Abordage Réussi", Effets.MAINDEFER);
		Carte discoursInspirant = new Carte("popularité", "Discours Inspirant", Effets.MAINDEFER);

		piocheObjet.getPiocheTableau()[1] = mainDeFer;
		piocheObjet.getPiocheTableau()[2] = mainDeFer;
		piocheObjet.getPiocheTableau()[3] = mainDeFer;
		piocheObjet.getPiocheTableau()[4] = mainDeFer;
		piocheObjet.getPiocheTableau()[5] = mainDeFer;
		piocheObjet.getPiocheTableau()[6] = mainDeFer;
		piocheObjet.getPiocheTableau()[7] = mainDeFer;
		piocheObjet.getPiocheTableau()[8] = mainDeFer;
		piocheObjet.getPiocheTableau()[9] = revolteOrganisee;
		piocheObjet.getPiocheTableau()[10] = revolteOrganisee;
		piocheObjet.getPiocheTableau()[11] = revolteOrganisee;
		piocheObjet.getPiocheTableau()[12] = revolteOrganisee;
		piocheObjet.getPiocheTableau()[13] = revolteOrganisee;
		piocheObjet.getPiocheTableau()[14] = revolteOrganisee;
		piocheObjet.getPiocheTableau()[15] = revolteOrganisee;
		piocheObjet.getPiocheTableau()[16] = revolteOrganisee;
		piocheObjet.getPiocheTableau()[17] = revolteOrganisee;
		piocheObjet.getPiocheTableau()[18] = revolteOrganisee;
		piocheObjet.getPiocheTableau()[19] = revolteOrganisee;
		piocheObjet.getPiocheTableau()[20] = revolteOrganisee;
		piocheObjet.getPiocheTableau()[21] = revolteOrganisee;
		piocheObjet.getPiocheTableau()[22] = coupDeSabre;
		piocheObjet.getPiocheTableau()[23] = coupDeSabre;
		piocheObjet.getPiocheTableau()[24] = coupDeSabre;
		piocheObjet.getPiocheTableau()[25] = coupDeSabre;
		piocheObjet.getPiocheTableau()[26] = abordageReussi;
		piocheObjet.getPiocheTableau()[27] = abordageReussi;
		piocheObjet.getPiocheTableau()[28] = abordageReussi;
		piocheObjet.getPiocheTableau()[29] = abordageReussi;
		piocheObjet.getPiocheTableau()[30] = abordageReussi;
		piocheObjet.getPiocheTableau()[31] = abordageReussi;
		piocheObjet.getPiocheTableau()[32] = discoursInspirant;
		piocheObjet.getPiocheTableau()[33] = discoursInspirant;
		piocheObjet.getPiocheTableau()[34] = discoursInspirant;
		piocheObjet.getPiocheTableau()[35] = discoursInspirant;
		piocheObjet.getPiocheTableau()[36] = discoursInspirant;
		piocheObjet.getPiocheTableau()[37] = discoursInspirant;
		piocheObjet.getPiocheTableau()[38] = coupDeSabre;
		piocheObjet.getPiocheTableau()[39] = coupDeSabre;

		return piocheObjet;
	}

	private static Jeu initJeu() {
		Joueur joueur1 = donnerJoueur(1);
		Joueur joueur2 = donnerJoueur(2);
		Joueur[] joueurs = new Joueur[2];
		joueurs[0] = joueur1;
		joueurs[1] = joueur2;
		Pioche pioche = initPioche();
		ZoneAttaque zoneAttaque = new ZoneAttaque();
		Banc banc = null;

		Jeu jeu = new Jeu(joueurs, pioche, zoneAttaque, banc);
		return jeu;
	}

	private Joueur getJoueur(int numJoueur) {
		Joueur joueur = joueurs[numJoueur - 1];
		return joueur;
	}

	private Pioche getPioche() {
		return pioche;
	}

	private int getTourJoueur(int nbTour) {
		int joueur = nbTour / 2;
		if (Math.floor(joueur) == joueur) {
			joueur = 2;
		} else {
			joueur = 1;
		}
		return joueur;
	}

	private ZoneAttaque getZoneAttaque() {
		return zoneAttaque;
	}
	private static void afficherAttaque(Jeu jeu) {
		if (jeu.getZoneAttaque() != null) {
		    IAffichage.afficherCartePoseeSurZoneAttaque(jeu.getZoneAttaque().getZoneAttaque());
		} else {
		    System.out.println("La zone d'attaque n'est pas encore initialisée.");
		}
	}

	public static void main(String[] args) {
		int nbTour = 0;
		int tourJoueur;
		Joueur joueur;
		IAffichage.afficherNbTour(nbTour);
		Jeu jeu = initJeu();

		IAffichage.donnerContexte();
		IAffichage.donnerRegles("regle");

		jeu.getJoueur(1).initJoueur(jeu.getPioche());
		jeu.getJoueur(2).initJoueur(jeu.getPioche());
		while (jeu.getJoueur(1).getPv() > 0 && jeu.getJoueur(2).getPv() > 0 && jeu.getJoueur(1).getPopularite() < 5
				&& jeu.getJoueur(2).getPopularite() < 5) {
			nbTour++;
			IAffichage.afficherNbTour(nbTour);
			tourJoueur = jeu.getTourJoueur(nbTour);
			joueur = jeu.getJoueur(tourJoueur);

			joueur.piocher(jeu.getPioche());
			IAffichage.donnerStatusJoueur(jeu.getJoueur(tourJoueur));
			afficherAttaque(jeu);
			System.out.println("La zone d'attaque n'est pas encore initialisée.");
			
			joueur.jouerCarte();
		}

	}

}
