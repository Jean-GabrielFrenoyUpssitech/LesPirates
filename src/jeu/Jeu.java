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

		Carte[] cartes = initCartes();
		Pioche piocheObjet = new Pioche(cartes[0]);

		int[] quantites = { 5, 5, 5, 5, 5, 5, 5, 5 };
		int numTableauPiocheActuel = 0;
		int nbCarteDifferentes = 8;
		for (int i = 0; i < nbCarteDifferentes; i++) {
			for (int j = 0; j < quantites[i]; j++) {
				piocheObjet.getPiocheTableau()[numTableauPiocheActuel] = cartes[i];
				numTableauPiocheActuel++;

			}

		}

		return piocheObjet;
	}

	private static Carte[] initCartes() {
		Carte revolteOrganisee = new RevolteOrganisee(Description.REVOLTEORGANISEE);
		Carte mainDeFer = new MainDeFer(Description.MAINDEFER);
		Carte coupDeSabre = new CoupDeSabre(Description.COUPDESABRE);
		Carte abordageReussi = new AbordageReussi(Description.ABORDAGEREUSSI);
		Carte discoursInspirant = new DiscoursInspirant(Description.DISCOURSINSPIRANT);
		Carte blocageDefensif = new BlocageDéfensif(Description.BLOCAGEDEFENSIF);
		Carte echangeForcee = new EchangeForce(Description.ECHANGEFORCE);
		Carte planMachiavelique = new PlanMachiavelique(Description.PLANMACHIAVELIQUE);
		Carte[] cartes = { blocageDefensif, mainDeFer, revolteOrganisee, coupDeSabre, abordageReussi, discoursInspirant,
				echangeForcee, planMachiavelique };
		return cartes;
	}

	public static Pioche shuffle(Pioche objetPioche) {
		Carte tempCarte;
		int numRandom = random.nextInt(39);
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 40; i++) {
				tempCarte = objetPioche.getPiocheTableau()[i];
				objetPioche.getPiocheTableau()[i] = objetPioche.getPiocheTableau()[numRandom];
				objetPioche.getPiocheTableau()[numRandom] = tempCarte;
				numRandom = random.nextInt(39);
			}
		}
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
		return joueurs[numJoueur - 1];
	}

	private Pioche getPioche() {
		return pioche;
	}

	private int getTourJoueur(int nbTour) {
		int numJoueur;
		if (nbTour % 2 == 0) {
			numJoueur = 2;
		} else {
			numJoueur = 1;
		}
		return numJoueur;
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
		while (jeu.getJoueur(1).getPv() > 0 && jeu.getJoueur(2).getPv() > 0 && jeu.getJoueur(1).getPopularite() < 6
				&& jeu.getJoueur(2).getPopularite() < 6) {
			nbTour++;
			IAffichage.afficherNbTour(nbTour);
			tourJoueur = jeu.getTourJoueur(nbTour);
			joueur = jeu.getJoueur(tourJoueur);
			adversaire = jeu.getJoueur(tourJoueur + 1);
			joueur.piocher(jeu.getPioche());
			IAffichage.donnerStatusJoueur(joueur, adversaire);

			joueur.jouerCarte(adversaire);

		}
		IAffichage.afficherVictoire(joueur);

	}

}
