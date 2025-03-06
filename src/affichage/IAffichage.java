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

}
