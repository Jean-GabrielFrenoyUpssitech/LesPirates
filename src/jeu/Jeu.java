package jeu;

import java.security.SecureRandom;
import java.util.Scanner;

import affichage.IAffichage;

public class Jeu implements IAffichage {
	private Pioche pioche;
	private Joueur[] joueurs = new Joueur[2];
	private static Scanner scaner = new Scanner(System.in);
	private static SecureRandom random;
	

	public Jeu(Joueur[] joueurs, Pioche pioche) {
		this.joueurs = joueurs;
		this.pioche = pioche;
		try {
			random = SecureRandom.getInstanceStrong();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Joueur donnerJoueur(int numJoueur) {
		IAffichage.affichageDonnerJoueur(numJoueur);
		String nom = scaner.next();
		return new Joueur(nom);

	}

	public static Pioche initPioche() {

		Pioche piocheObjet = new Pioche();
		Carte revolteOrganisee = new RevolteOrganisee(Description.REVOLTEORGANISEE);
		Carte mainDeFer = new MainDeFer(Description.MAINDEFER);
		Carte coupDeSabre = new CoupDeSabre(Description.COUPDESABRE);
		Carte abordageReussi = new AbordageReussi(Description.MAINDEFER);
		Carte discoursInspirant = new DiscoursInspirant(Description.DISCOURSINSPIRANT);
		piocheObjet.getPiocheTableau()[0] = mainDeFer;
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

	public static Pioche shuffle(Pioche objetPioche) {
		Carte tempCarte;
		int numRandom = random.nextInt(39);

		for (int i = 0; i < 40; i++) {
			tempCarte = objetPioche.getPiocheTableau()[i];
			objetPioche.getPiocheTableau()[i] = objetPioche.getPiocheTableau()[numRandom];
			objetPioche.getPiocheTableau()[numRandom] = tempCarte;
 
		}//dqsdqsdq

		return objetPioche;
	}

	private static Jeu initJeu() {
		Joueur joueur1 = donnerJoueur(1);
		Joueur joueur2 = donnerJoueur(2);
		Joueur[] joueurs = new Joueur[2];
		joueurs[0] = joueur1;
		joueurs[1] = joueur2;
		Pioche pioche = initPioche();
		Jeu jeu = new Jeu(joueurs, pioche);
		jeu.pioche = shuffle(pioche);
		return jeu;
	}

	public Joueur getJoueur(int numJoueur) {
		if (numJoueur == 3) {
			numJoueur = 1;
		}
		Joueur joueur = joueurs[numJoueur - 1];
		return joueur;
	}

	private Pioche getPioche() {
		return pioche;
	}

	private int getTourJoueur(int nbTour) {
		int joueur;
		if (nbTour % 2 == 0) {
			joueur = 2;
		} else {
			joueur = 1;
		}
		return (int) joueur;
	}

	public static void main(String[] args) {
		int nbTour = 0;
		int tourJoueur;
		Joueur joueur = null;
		Joueur adversaire;
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
			adversaire = jeu.getJoueur(tourJoueur + 1);
			joueur.piocher(jeu.getPioche());
			System.out.println("le joueur possede " + joueur.getNbCarte());
			IAffichage.donnerStatusJoueur(jeu.getJoueur(tourJoueur));

			joueur.jouerCarte(adversaire);
			
		}
		IAffichage.afficherVictoire(joueur);

	}

}
