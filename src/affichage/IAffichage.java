package affichage;

import jeu.Banc;
import jeu.Carte;
import jeu.Joueur;

public interface IAffichage {

	public static void afficherMain(Joueur joueur) {
		Carte[] main = joueur.getMain();
		Carte carte;
		System.out.println("\nVos cartes sont : \n\n");
		for (int i = 0; i < joueur.getNbCarte(); i++) {
			carte=main[i];
			System.out.println(carte.getDescription().getNom() + " - effet : "+carte.getDescription().getDescription()+"\n");
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
		System.out
				.println("Il n'y a plus de place sur votre banc veuillez choisir quel carte remplacer entre 1 et 5: ");
	}

	public static void afficherCartePoseeSurZoneAttaque(Carte carteZoneAttaque) {
		if (carteZoneAttaque != null) {
			System.out.println("La dernière carte jouer sur la zone d'attaque est :  "
					+ carteZoneAttaque.getDescription().getNom());
		} else {
			afficherZonneAttaqueVide();
		}
	}

	public default void afficherPiocheVide() {
		System.out.println("la pioche est vide");
	}

	public static void donnerContexte() {
		System.out.println(
				"Bienvenue dans le monde merveilleux de ONE PIECE... \nGold roger : Mon trésors ? Je vous le laisse Je l'ai laisser quelque pars dans ce monde.Trouver le !\n\n");
	}

	public static void donnerRegles(String regles) {
		System.out.println("Pour gagner suivre les règles : \n\n" + regles + "\n\n");

		System.out.println("Les Vaillant pirates sont : \n");
	}

	public static void donnerStatusJoueur(Joueur joueur, Joueur adversaire) {
		int vieJ = joueur.getPv();
		int popJ = joueur.getPopularite();
		int vieA = adversaire.getPv();
		int popA = adversaire.getPopularite();
		afficherSeparation("");
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
		afficherSeparation("\n");

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

	public static void afficherSeparation(String string) {
		System.out.println("--------------"+string);
	}

	public static void affichageDonnerJoueur(int num) {
		System.out.println("Joueur " + num + ", donner votre nom");
	}

	public static void afficherNbTour(int nbTour) {
		System.out.println("\n\n______________\nnb tour : " + nbTour + "\n\n");
	}

	public static void afficherChoisirCarte() {
		System.out.println("\nPour choisir la carte choisissez un nombre entre 1 et 5");
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

	public static void afficherVictoire(Joueur joueur) {
		System.out.println("Le gagnant est : " + joueur.getNom());
	}

	public static void afficherErreurSwitchcase() {
		System.out.println("Erreur vous ne pouvez trier que le banc ou la main");
	}

	public static void afficherCarteMalPoser() {
		System.out.println("erreur la carte s'est mise sur la mauvaise zone");
	}

	public static void affichageBlocageDefensif() {
		System.out.println("");
	}

}
