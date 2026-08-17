package affichage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import jeu.Carte;
import jeu.Description;
import jeu.Jeu;
import processing.core.PApplet;
import processing.core.PImage;
import reseau.ClientReseau;
import reseau.Protocole;

public class InterfaceJeu extends PApplet {

	private static final int POINT_ACTION_MAX = 2; // doit rester synchro avec Joueur.pointDActionMax

	// --- Réseau ---
	ClientReseau reseau;
	Map<Description, Carte> cartesParDescription = new HashMap<>();
	int numJoueurMoi = -1;
	boolean enAttenteAdversaire = false;
	boolean partieCommencee = false;
	String erreurConnexion;

	// --- État de la partie, reçu du serveur (message ETAT) ---
	int pvMoi, popMoi, paMoi, paJouerCarteMoi;
	Carte[] mainMoi = new Carte[5];
	int nbCarteEnMainMoi = 0;
	Carte[] bancMoi = new Carte[5];
	int nbBancMoi = 0;
	int pvAdv, popAdv;
	int nbCarteEnMainAdv = 0;
	Carte[] bancAdv = new Carte[5];
	int nbBancAdv = 0;
	int piocheRestante;
	int tourDeQui = 1;
	int nbTour = 0;
	boolean enAttenteChoixBanc = false;

	boolean victoire = false;
	String gagnant;
	String typeVictoire;

	// --- UI ---
	int carteX1 = 12;
	int carteY = 680;
	int carteLargeur1 = 150;
	int carteHauteur1 = 185;
	int carteSelectionnee = -1;
	boolean enTrainDeDeplacer = false;
	String messageTemporaire;
	int messageTemporaireJusqua;

	int positionXadversairePop = 209;
	int positionYadversairePop = 178;
	int positionXjoueurPop = 1085;
	int positionYjoueurPop = 813;
	int ecartPopAffichage = 87;

	int bancAdvX = 380;
	int bancAdvY = 55;
	int bancAdvLargeur = 80;
	int bancAdvHauteur = 100;
	int mainAdvX = 1230;

	PImage backgroundActuel;
	PImage coeurplein;
	PImage coeurvide;
	PImage gouvernail;
	PImage morceauGouvernail;

	public static void main(String[] args) {
		PApplet.main("affichage.InterfaceJeu");
	}

	public void settings() {
		size(1387, 900);
	}

	public void setup() {
		backgroundActuel = loadImage("image/Background1.png");
		coeurplein = loadImage("image/coeurplein.png");
		coeurvide = loadImage("image/coeurvide.png");
		gouvernail = loadImage("image/gouvernail.png");
		morceauGouvernail = loadImage("image/morceaudepop.png");

		for (Carte carte : Jeu.initCartes()) {
			cartesParDescription.put(carte.getDescription(), carte);
		}

		textSize(24);

		Scanner console = new Scanner(System.in);
		System.out.print("Adresse du serveur (vide = localhost) : ");
		String hote = console.nextLine().trim();
		if (hote.isEmpty()) {
			hote = "localhost";
		}
		System.out.print("Port du serveur (vide = 5000) : ");
		String portTexte = console.nextLine().trim();
		int port = portTexte.isEmpty() ? 5000 : Integer.parseInt(portTexte);
		System.out.print("Votre nom : ");
		String nom = console.nextLine().trim();
		if (nom.isEmpty()) {
			nom = "Joueur";
		}

		reseau = new ClientReseau();
		try {
			reseau.connecter(hote, port);
			reseau.envoyer(Protocole.message(Protocole.JOIN, nom));
		} catch (IOException e) {
			erreurConnexion = "Connexion au serveur impossible : " + e.getMessage();
		}
	}

	/* drag */
	public void mousePressed() {
		if (victoire || !partieCommencee || tourDeQui != numJoueurMoi || enAttenteChoixBanc) {
			return;
		}
		for (int i = 0; i < nbCarteEnMainMoi; i++) {
			int x = carteX1 + i * 160;
			int y = carteY;

			if (mouseX >= x && mouseX <= x + carteLargeur1 && mouseY >= y && mouseY <= y + carteHauteur1) {
				carteSelectionnee = i;
				enTrainDeDeplacer = true;
			}
		}
	}

	/* drop */
	public void mouseReleased() {
		if (victoire || !partieCommencee) {
			return;
		}

		if (enAttenteChoixBanc) {
			traiterClicChoixBanc();
			return;
		}

		if (carteSelectionnee != -1 && enTrainDeDeplacer) {
			boolean dansLaZone = mouseX >= 0 && mouseX <= 830 && mouseY >= 320 && mouseY <= 577;
			boolean peutJouerUneCarte = tourDeQui == numJoueurMoi && paMoi > 0 && paJouerCarteMoi > 0;

			if (dansLaZone && peutJouerUneCarte) {
				reseau.envoyer(Protocole.message(Protocole.JOUER_CARTE, String.valueOf(carteSelectionnee)));
			} else if (dansLaZone) {
				afficherMessageTemporaire(
						tourDeQui != numJoueurMoi ? "Ce n'est pas votre tour." : "Vous ne pouvez jouer qu'une carte à la fois.");
			}

			// Réinitialiser le déplacement (la carte revient dans la main tant que le serveur n'a pas confirmé)
			carteSelectionnee = -1;
			enTrainDeDeplacer = false;
		}

		if (tourDeQui == numJoueurMoi && mouseX >= 819 && mouseX <= 960 && mouseY >= 709 && mouseY <= 755) {
			reseau.envoyer(Protocole.message(Protocole.FIN_TOUR));
		}
	}

	private void traiterClicChoixBanc() {
		for (int i = 0; i < 5; i++) {
			int x = carteX1 + i * 160;
			int y = 400;
			if (mouseX >= x && mouseX <= x + carteLargeur1 && mouseY >= y && mouseY <= y + carteHauteur1) {
				reseau.envoyer(Protocole.message(Protocole.CHOIX_BANC, String.valueOf(i + 1)));
				enAttenteChoixBanc = false;
				return;
			}
		}
	}

	// --- Traitement des messages reçus du serveur ---

	public void traiterMessagesReseau() {
		if (reseau == null) {
			return;
		}
		String ligne;
		while ((ligne = reseau.prochainMessage()) != null) {
			appliquerMessage(ligne);
		}
	}

	private void appliquerMessage(String ligne) {
		String[] champs = Protocole.decouper(ligne);
		if (champs.length == 0) {
			return;
		}
		switch (champs[0]) {
		case Protocole.BIENVENUE:
			numJoueurMoi = Integer.parseInt(champs[1]);
			break;
		case Protocole.ATTENTE_ADVERSAIRE:
			enAttenteAdversaire = true;
			break;
		case Protocole.ETAT:
			appliquerEtat(champs);
			partieCommencee = true;
			enAttenteAdversaire = false;
			enAttenteChoixBanc = false;
			break;
		case Protocole.BANC_PLEIN_CHOIX:
			enAttenteChoixBanc = true;
			break;
		case Protocole.ERREUR:
			afficherMessageTemporaire(champs.length > 2 ? champs[2] : "Action refusée");
			break;
		case Protocole.VICTOIRE:
			victoire = true;
			gagnant = champs[1];
			typeVictoire = champs.length > 2 ? champs[2] : "";
			break;
		case Protocole.ADVERSAIRE_DECONNECTE:
			erreurConnexion = "L'adversaire s'est déconnecté.";
			break;
		case Protocole.CONNEXION_PERDUE:
			if (!victoire) {
				erreurConnexion = "Connexion au serveur perdue.";
			}
			break;
		default:
			break;
		}
	}

	private void appliquerEtat(String[] champs) {
		pvMoi = Integer.parseInt(champs[1]);
		popMoi = Integer.parseInt(champs[2]);
		paMoi = Integer.parseInt(champs[3]);
		paJouerCarteMoi = Integer.parseInt(champs[4]);
		nbCarteEnMainMoi = remplirCartes(mainMoi, Protocole.decouperListe(champs[5]));
		nbBancMoi = remplirCartes(bancMoi, Protocole.decouperListe(champs[6]));
		pvAdv = Integer.parseInt(champs[7]);
		popAdv = Integer.parseInt(champs[8]);
		nbCarteEnMainAdv = Integer.parseInt(champs[9]);
		nbBancAdv = remplirCartes(bancAdv, Protocole.decouperListe(champs[10]));
		piocheRestante = Integer.parseInt(champs[11]);
		tourDeQui = Integer.parseInt(champs[12]);
		nbTour = Integer.parseInt(champs[13]);

		carteSelectionnee = -1;
		enTrainDeDeplacer = false;
	}

	private int remplirCartes(Carte[] cible, String[] noms) {
		for (int i = 0; i < cible.length; i++) {
			cible[i] = i < noms.length ? cartesParDescription.get(Description.valueOf(noms[i])) : null;
		}
		return noms.length;
	}

	// --- Affichage ---

	public void afficherMessageTemporaire(String message) {
		messageTemporaire = message;
		messageTemporaireJusqua = millis() + 1500;
	}

	public void afficherMessageTemporaireSiPresent() {
		if (messageTemporaire != null) {
			if (millis() < messageTemporaireJusqua) {
				fill(255, 60, 60);
				textSize(28);
				textAlign(CENTER, CENTER);
				text(messageTemporaire, 415, 450);
				textAlign(LEFT, BASELINE);
			} else {
				messageTemporaire = null;
			}
		}
	}

	public void affichageMainMoi() {
		for (int i = 0; i < nbCarteEnMainMoi; i++) {
			if (i == carteSelectionnee && enTrainDeDeplacer) {
				continue; // on la dessine ailleurs
			}
			Carte carte = mainMoi[i];
			if (carte == null) {
				continue;
			}
			PImage carteImage = loadImage("image/" + carte.getDescription().getNomFichier() + ".png");
			image(carteImage, carteX1 + i * 160, carteY, carteLargeur1, carteHauteur1);
		}

		// Dessine la carte suivie par la souris
		if (carteSelectionnee != -1 && enTrainDeDeplacer) {
			Carte carte = mainMoi[carteSelectionnee];
			if (carte != null) {
				PImage carteImage = loadImage("image/" + carte.getDescription().getNomFichier() + ".png");
				image(carteImage, mouseX - carteLargeur1 / 2, mouseY - carteHauteur1 / 2, carteLargeur1, carteHauteur1);
			}
			stroke(255);
			noFill();
			// zone où placer la carte
			rect(8, 350, 802, 277);
		}
		surligneMain();
	}

	public void affichageBancMoi() {
		for (int i = 0; i < nbBancMoi; i++) {
			Carte carte = bancMoi[i];
			if (carte == null) {
				continue;
			}
			PImage carteImage = loadImage("image/" + carte.getDescription().getNomFichier() + ".png");
			image(carteImage, carteX1 + i * 160, 400, carteLargeur1, carteHauteur1);
		}
	}

	public void afficherChoixBanc() {
		fill(255, 200, 60);
		textSize(26);
		textAlign(CENTER, CENTER);
		text("Votre banc est plein : cliquez sur la carte à remplacer", 415, 360);
		textAlign(LEFT, BASELINE);

		noFill();
		stroke(255, 200, 60);
		strokeWeight(3);
		for (int i = 0; i < 5; i++) {
			rect(carteX1 + i * 160, 400, carteLargeur1, carteHauteur1);
		}
		strokeWeight(1);
	}

	public void affichageAdversaire() {
		// Banc adverse (posé face visible, information publique)
		for (int i = 0; i < nbBancAdv; i++) {
			Carte carte = bancAdv[i];
			if (carte == null) {
				continue;
			}
			PImage carteImage = loadImage("image/" + carte.getDescription().getNomFichier() + ".png");
			image(carteImage, bancAdvX + i * (bancAdvLargeur + 10), bancAdvY, bancAdvLargeur, bancAdvHauteur);
		}

		// Main adverse : contenu caché, seule la quantité est connue
		fill(255);
		textSize(16);
		text("Main adverse (" + nbCarteEnMainAdv + ")", mainAdvX - 20, 20);
		for (int i = 0; i < nbCarteEnMainAdv; i++) {
			int x = mainAdvX;
			int y = 40 + i * 95;
			fill(40, 40, 70);
			stroke(255);
			rect(x, y, 90, 85);
			fill(255);
			textAlign(CENTER, CENTER);
			textSize(28);
			text("?", x + 45, y + 42);
			textAlign(LEFT, BASELINE);
		}
	}

	public void affichageVie() {
		int vieAdversaire = pvAdv;
		int vieMoi = pvMoi;

		for (int i = 0; i < 5; i++) {
			image(vieAdversaire < 1 ? coeurvide : coeurplein, 2 + i * 61, 265, 55, 53);
			vieAdversaire--;
			image(vieMoi < 1 ? coeurvide : coeurplein, 1080 + i * 61, 585, 55, 53);
			vieMoi--;
		}
	}

	public void affichagePop() {
		int popAdversaire = popAdv;
		int popJoueur = popMoi;

		// cas adversaire
		if (popAdversaire < 5) {
			afficherGouvernailPartiel(popAdversaire, positionXadversairePop, positionYadversairePop);

			for (int i = 1; i < 3; i++) {
				image(gouvernail, positionXadversairePop, positionYadversairePop - i * ecartPopAffichage, 86, 85);
			}

		} else if (popAdversaire < 9) {
			popAdversaire = popAdversaire - 4;
			afficherGouvernailPartiel(popAdversaire, positionXadversairePop, positionYadversairePop);
			affichageGouvernailEntier(positionXadversairePop, positionYadversairePop - ecartPopAffichage);
			image(gouvernail, positionXadversairePop, positionYadversairePop - 2 * ecartPopAffichage, 86, 85);

		} else {
			popAdversaire = popAdversaire - 8;
			for (int i = 0; i < 2; i++) {
				affichageGouvernailEntier(positionXadversairePop, positionYadversairePop - i * ecartPopAffichage);
			}
			afficherGouvernailPartiel(popAdversaire, positionXadversairePop,
					positionYadversairePop - ecartPopAffichage * 2);
		}

		// cas joueur
		if (popJoueur < 5) {
			afficherGouvernailPartiel(popJoueur, positionXjoueurPop, positionYjoueurPop);

			for (int i = 1; i < 3; i++) {
				image(gouvernail, positionXjoueurPop, positionYjoueurPop - i * ecartPopAffichage, 86, 85);
			}

		} else if (popJoueur < 9) {
			popJoueur = popJoueur - 4;
			affichageGouvernailEntier(positionXjoueurPop, positionYjoueurPop);
			afficherGouvernailPartiel(popJoueur, positionXjoueurPop, positionYjoueurPop - ecartPopAffichage);
			image(gouvernail, positionXjoueurPop, positionYjoueurPop - 2 * ecartPopAffichage, 86, 85);

		} else {
			popJoueur = popJoueur - 8;
			for (int i = 0; i < 2; i++) {
				affichageGouvernailEntier(positionXjoueurPop, positionYjoueurPop - i * ecartPopAffichage);
			}
			afficherGouvernailPartiel(popJoueur, positionXjoueurPop, positionYjoueurPop - ecartPopAffichage * 2);
		}
	}
	/*
	 * permet d'afficher le gouvernail de popularité avec déjà tous les cadrant d'allumé
	 */

	public void affichageGouvernailEntier(int positionpopX, int positionPopY) {
		image(gouvernail, positionpopX, positionPopY, 86, 85);
		afficherCadrantUn(positionpopX, positionPopY);
		afficherCadrantDeux(positionpopX, positionPopY);
		afficherCadrantTrois(positionpopX, positionPopY);
		afficherCadrantQuatre(positionpopX, positionPopY);
	}

	public void afficherGouvernailPartiel(int valCadran, int positionpopX, int positionPopY) {
		image(gouvernail, positionpopX, positionPopY, 86, 85);
		if (valCadran > 0) {
			afficherCadrantUn(positionpopX, positionPopY);
		}
		if (valCadran > 1) {
			afficherCadrantDeux(positionpopX, positionPopY);
		}
		if (valCadran > 2) {
			afficherCadrantTrois(positionpopX, positionPopY);
		}
		if (valCadran > 3) {
			afficherCadrantQuatre(positionpopX, positionPopY);
		}
	}

	/* surligne quand on passe la souris dessus sur les cartes/boutons */
	public void surligneMain() {
		if (victoire || !partieCommencee || tourDeQui != numJoueurMoi || enAttenteChoixBanc) {
			return;
		}
		for (int i = 0; i < nbCarteEnMainMoi; i++) {
			int x = carteX1 + i * 160;
			int y = carteY;

			if (mouseX >= x && mouseX <= x + carteLargeur1 && mouseY >= y && mouseY <= y + carteHauteur1) {
				stroke(255);
				noFill();
				rect(x, y, carteLargeur1, carteHauteur1);
			}
		}
		if (mouseX >= 819 && mouseX <= 960 && mouseY >= 709 && mouseY <= 755) {
			stroke(255);
			noFill();
			rect(819, 709, 141, 46);
		}
	}

	public void boutonFinDeTour() {
		noStroke();
		fill(50);
		rect(820, 710, 140, 45);

		fill(255);
		textSize(24);
		textAlign(CENTER, CENTER);
		text("Fin de Tour", 890, 732);
		textAlign(LEFT, BASELINE);
	}

	public void afficherLabels() {
		fill(255);
		textSize(20);
		text("Banc adverse", bancAdvX, bancAdvY - 15);
		text("Votre banc", carteX1, 390);
		text("Votre main", carteX1, 670);

		if (paMoi < 1 && tourDeQui == numJoueurMoi) {
			fill(255, 150, 60);
			textSize(36);
			textAlign(CENTER, CENTER);
			text("Vous n'avez plus de point d'action", 600, 300);
			textAlign(LEFT, BASELINE);
		}
		if (tourDeQui != numJoueurMoi) {
			fill(255, 150, 60);
			textSize(36);
			textAlign(CENTER, CENTER);
			text("Tour de l'adversaire...", 600, 250);
			textAlign(LEFT, BASELINE);
		}
	}

	public void afficherEcranAttente() {
		fill(255);
		textSize(32);
		textAlign(CENTER, CENTER);
		text(enAttenteAdversaire ? "En attente d'un second joueur..." : "Connexion au serveur...", 693, 450);
		textAlign(LEFT, BASELINE);
	}

	public void afficherErreurConnexion() {
		fill(255, 80, 80);
		textSize(28);
		textAlign(CENTER, CENTER);
		text(erreurConnexion, 693, 450);
		textAlign(LEFT, BASELINE);
	}

	public void afficherVictoireSiPresent() {
		if (victoire) {
			fill(255, 150, 60);
			textSize(40);
			textAlign(CENTER, CENTER);
			text("GAME OVER\nLe gagnant est : " + gagnant + " victoire par " + typeVictoire, 600, 400);
			textAlign(LEFT, BASELINE);
		}
	}

	public void afficherCadrantUn(int positionX, int positionY) {
		pushMatrix(); // Sauvegarde l'état actuel
		translate(24 + 19 / 2, 24 + 18 / 2); // Déplace l'origine au centre de l'image
		rotate(radians(90)); // Rotation en degrés convertis en radians
		imageMode(CENTER); // Pour dessiner l'image centrée
		image(morceauGouvernail, 0 + positionY, -20 - positionX, 19, 18); // Dessine l'image tournée
		imageMode(CORNER); // (optionnel) pour rétablir le mode par défaut
		popMatrix(); // Restaure l'état initial
	}

	public void afficherCadrantDeux(int positionX, int positionY) {
		pushMatrix();
		translate(24 + 19 / 2, 24 + 18 / 2);
		rotate(radians(180));
		imageMode(CENTER);
		image(morceauGouvernail, -20 - positionX, -20 - positionY, 17, 18);
		imageMode(CORNER);
		popMatrix();
	}

	public void afficherCadrantTrois(int positionX, int positionY) {
		pushMatrix();
		translate(24 + 19 / 2, 24 + 18 / 2);
		rotate(radians(-90));
		imageMode(CENTER);
		image(morceauGouvernail, -20 - positionY, 0 + positionX, 19, 18); // x et y inversé
		imageMode(CORNER);
		popMatrix();
	}

	public void afficherCadrantQuatre(int positionX, int positionY) {
		image(morceauGouvernail, 24 + positionX, 24 + positionY, 19, 18);
	}

	public void afficherNbActionRestante() {
		fill(255, 150, 60);
		textSize(40);
		text(paMoi + " / " + POINT_ACTION_MAX, 890, 800);
	}

	public void afficherDeroulementTour() {
		int hauteur = 700;
		int largeur = 1045;
		textSize(18);

		fill(paJouerCarteMoi < 1 ? color(255, 150, 60) : color(0)); // ?: equivaut au if/else
		text("Jouer ", largeur, hauteur);

		int couleurAction = (paMoi < 1) ? color(255, 150, 60) : color(0);
		fill(couleurAction);
		text("Fusioner ", largeur, hauteur + 30);
		text("Défausser ", largeur, hauteur + 60);
	}

	public void draw() {
		traiterMessagesReseau();

		image(backgroundActuel, 0, 0, 1387, 900);

		if (erreurConnexion != null) {
			afficherErreurConnexion();
			return;
		}
		if (!partieCommencee) {
			afficherEcranAttente();
			return;
		}

		IAffichage.afficherNbTour(nbTour);
		afficherLabels();

		affichageVie();
		affichagePop();
		affichageMainMoi();
		affichageBancMoi();
		affichageAdversaire();
		afficherMessageTemporaireSiPresent();
		afficherNbActionRestante();
		afficherDeroulementTour();
		afficherVictoireSiPresent();
		if (enAttenteChoixBanc) {
			afficherChoixBanc();
		}
		boutonFinDeTour();
	}
}
