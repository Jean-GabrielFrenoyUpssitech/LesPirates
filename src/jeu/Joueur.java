package jeu;
import affichage.IAffichage;
import java.util.Scanner;

public class Joueur implements IAffichage {
	private String nom;
	private int pv = 5;
	private int popularite = 0;
	private Carte[] main = new Carte[5];
	private int nbCarteEnMain = 0;
	private Banc banc=new Banc();
	private Carte derniereCarteJouer;
	private int pointDActionMax=2;
	private int pointDAction=pointDActionMax;
	private int pointDActionJouerCarte=1;
	private static Scanner scaner = new Scanner(System.in);

	public String getNom() {
		return nom;
	}

	public int getPv() {
		return pv;
	}
	public int getPopularite() {
		return popularite;
	}
	public int getPointDAction() {
		return pointDAction;
	}
	
	public void modifierPointDAction(int valDePoint) {
		pointDAction+=valDePoint;
	}
	public int getPointDActionMax() {
		return pointDActionMax;
	}
	public int getPointDActionJouerCarte() {
		return pointDActionJouerCarte;
	}
	/*Si tu veux retirer il faut mettre un nb négatif*/
	public void setPointDActionJouerCarte(int val){
		pointDActionJouerCarte=pointDActionJouerCarte+val;
	}
	/*Redonne le droit de jouer une carte au début d'un nouveau tour*/
	public void reinitialiserPointDActionJouerCarte(){
		pointDActionJouerCarte=1;
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

			remplacerCarteBanc(carte, nbCarteRemplacer);
		}

	}

	/* Remplace la carte du banc au slot donné (1 à 5). Utilisé par jouerCarteSurBanc (mode console)
	 * et par le serveur réseau une fois que le client a répondu à une demande de choix de slot. */
	public void remplacerCarteBanc(Carte carte, int slot) {
		Carte ancienneCarte = this.banc.modifierCarte(carte, slot - 1);
		ancienneCarte.retirerEffet(this);
	}

	/* Vrai si la dernière carte jouée par l'adversaire (Blocage Défensif) annule l'effet qu'on s'apprête à lui infliger */
	public boolean effetBloqueParAdversaire(Joueur adversaire) {
		String nomDerniereCarteAdversaire = adversaire.derniereCarteJouer != null
				? adversaire.derniereCarteJouer.getDescription().getNom() : null;
		return "Blocage Défensif".equals(nomDerniereCarteAdversaire);
	}

	/* Vrai si jouer cette carte nécessitera de choisir quelle carte du banc remplacer */
	public boolean banIPleinPourCarte(Carte carte) {
		return "Popularité".equals(carte.getDescription().getType()) && getCarteBancRestante() == 5;
	}

	public void jouerCarte(Joueur adversaire, int numCarte) {
		Carte carte = this.getMain()[numCarte];

		IAffichage.afficherCarteJouer(carte.getDescription().getNom());
		if (effetBloqueParAdversaire(adversaire)) {
			IAffichage.afficherEffetEchangeForce();
		} else {
			placerCarteSurZone(carte, adversaire, carte.getDescription().getType());
			carte.appliquerEffet(this, adversaire);
		}
		this.derniereCarteJouer = carte;
	}

	/* Variante utilisée par le serveur réseau quand le banc était plein : le slot a déjà
	 * été choisi par le client, donc on remplace directement au lieu de repasser par placerCarteSurZone. */
	public void jouerCarteAvecSlotBanc(Joueur adversaire, int numCarte, int slot) {
		Carte carte = this.getMain()[numCarte];

		IAffichage.afficherCarteJouer(carte.getDescription().getNom());
		if (effetBloqueParAdversaire(adversaire)) {
			IAffichage.afficherEffetEchangeForce();
		} else {
			remplacerCarteBanc(carte, slot);
			carte.appliquerEffet(this, adversaire);
		}
		this.derniereCarteJouer = carte;
	}

	private void placerCarteSurZone(Carte carte, Joueur adversaire, String typeCarte) {
		switch (typeCarte) {
		case "Popularité":

			jouerCarteSurBanc(carte, adversaire);
			break;

		default:
			if (!"Stratégie".equals(carte.getDescription().getType())) {
				IAffichage.afficherCarteMalPoser();

			}
			break;
		}
	}

	

	public Joueur initJoueur(Jeu jeu, int numJoueur) {
		IAffichage.affichageDonnerJoueur(numJoueur);
		return initJoueur(jeu, numJoueur, scaner.next());
	}

	/* Variante réseau : le nom arrive du client (message JOIN) au lieu d'être lu au clavier */
	public Joueur initJoueur(Jeu jeu, int numJoueur, String nomFourni) {
		this.nom = nomFourni;

		for (int i = 0; i < 4; i++) {

			Jeu.piocher(jeu.getPioche(), this);
		}
		return this;
	}

	public void trierCarte(int index) {
		for (int i = index; i < main.length - 1; i++) {
			main[i] = main[i + 1]; // décale tout vers la gauche
		}
		main[main.length - 1] = null; // on vide la dernière case qui a été décalée
	}



	public void setMain(Carte[] mainAdv) {
		main = mainAdv;
	}

	/* Échange la main ET le nombre de cartes en main avec un autre joueur (utilisé par Echange Forcé).
	 * setMain(Carte[]) seul ne suffit pas : il ne touche pas nbCarteEnMain, ce qui désynchronise
	 * le tableau et son compteur dès que les deux joueurs n'ont pas le même nombre de cartes. */
	public void echangerMainAvec(Joueur autre) {
		Carte[] mainTemp = this.main;
		int nbCarteTemp = this.nbCarteEnMain;
		this.main = autre.main;
		this.nbCarteEnMain = autre.nbCarteEnMain;
		autre.main = mainTemp;
		autre.nbCarteEnMain = nbCarteTemp;
	}

	public Joueur getTourJoueur(int nbTour, Joueur[] joueurs) {
		int numJoueur;
		if (nbTour % 2 == 0) {
			numJoueur = 2;
		} else {
			numJoueur = 1;
		}
		if (numJoueur == 3) {
			numJoueur = 1;
		}
		return joueurs[numJoueur - 1];
	}

	public void setMain(Carte carte) {
		this.main[this.getNbCarteEnMain()] = carte;
	}

	public void setAjouterNbCarteEnMain() {
		nbCarteEnMain++;
	}
	public void setRetirerNbCarteEnMain() {
		nbCarteEnMain--;
	}
	public void retirerCarte(int indexCarte) {
		this.main[indexCarte] = null;

	}
}