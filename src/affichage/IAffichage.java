package affichage;


import jeu.Banc;
import jeu.Carte;
import jeu.Joueur;

public interface IAffichage {

	public default void afficherMain(Joueur joueur) {
		Carte[] main = joueur.getMain();
		System.out.println("Vos cartes sont : ");
		for (int i = 0; i < joueur.getNbCarte()+1; i++) {
			System.out.println(main[i].getNom()+"\n");
		}
	}
	public default void afficherBanc(Joueur joueur) {
		Banc objetBanc = joueur.getBanc();
		Carte[] tableauBanc= objetBanc.getBanc();
		System.out.println("Vos cartes de banc sont : ");
		for (int i = 0; i < joueur.getCarteBancRestante()+1; i++) {
			System.out.println(tableauBanc[i].getNom()+"\n");
		}
	}
	public default void afficherEffetCarte(Carte carte) {
		afficherEffetCartePop(carte);
		afficherEffetCartePv(carte);
	}

	public default void afficherEffetCartePop(Carte carte) {
		int pop = carte.getEffetCarte().getPop();

		System.out.println("La carte posséde les effet");
		String cible;
		String modulation;
		if (pop != 0) {
			cible = carte.getEffetCarte().getCibleEffetPop();
			if (pop < 0) {
				modulation = "diminue";
			} else {
				modulation = "augmente";
			}
			System.out.println("La popularité " + cible + " " + modulation + " de " + pop);

		}

	}
	public default void afficherMsgCartePoseeSurBanc(Carte carte) {
		System.out.println("La carte " + carte.getNom()+" est ajouté à votre banc");
	}
	public default void afficherCarteRemplacerSurBanc() {
		System.out.println("Il n'y a plus de place sur votre banc veuillez choisir quel carte remplacer : ");
	}
	public default void afficherCartePoseeSurZoneAttaque(Carte carte) {
		System.out.println("La dernière carte jouer sur la zone d'attaque est :  " + carte.getNom());
	}
	public default void afficherEffetCartePv(Carte carte) {
		int pv = carte.getEffetCarte().getPv();
		String cible;
		String modulation;
		if (pv != 0) {
			cible = carte.getEffetCarte().getCibleEffetPv();

			if (pv < 0) {
				modulation = "diminue";
			} else {
				modulation = "augmente";
			}
			System.out.println("La vie " + cible + " " + modulation + " de " + pv);

		}

	}
	public default void afficherPiocheVide(){
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

	public static void affichageJoueur(int numJoueur, String joueur) {
		System.out.println(
				"le joueur " + numJoueur + " se nomme : " + joueur + " et commence avec : \nVie =5 \npopularite = 0\n");
	}
	public static void affichageDonnerJoueur(int num) {
		System.out.println("Joueur "+num+", donner votre nom"+num);
	}
	

	public default void afficherNbTour(int nbTour) {
		System.out.println("nb tour : " + nbTour + "\n\n");
	}
	

}
