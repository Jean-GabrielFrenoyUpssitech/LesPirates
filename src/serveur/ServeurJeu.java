package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import jeu.Carte;
import jeu.Jeu;
import jeu.Joueur;
import reseau.Protocole;

/* Serveur faisant autorité pour UNE partie à deux joueurs.
 * Démarre, attend deux connexions, joue la partie jusqu'à la victoire, puis s'arrête. */
public class ServeurJeu {

	private static final int PORT_PAR_DEFAUT = 5000;
	private static final int POPULARITE_VICTOIRE = 5;

	private Jeu jeu;
	private final ConnexionClient[] connexions = new ConnexionClient[2];
	private int nbTour = 0;
	private int joueurCourant = 0; // index 0 ou 1 dans Jeu.joueurs / connexions
	private boolean partieTerminee = false;

	// index (0-4) de la carte en cours de jeu par le joueur courant, en attente d'un choix de slot de banc
	private Integer indexCarteEnAttenteDeSlot = null;

	public static void main(String[] args) throws IOException {
		int port = args.length > 0 ? Integer.parseInt(args[0]) : PORT_PAR_DEFAUT;
		new ServeurJeu().demarrer(port);
	}

	public void demarrer(int port) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Serveur LesPirates en écoute sur le port " + port + ", en attente de 2 joueurs...");

			String[] noms = new String[2];
			for (int i = 0; i < 2; i++) {
				Socket socket = serverSocket.accept();
				ConnexionClient connexion = new ConnexionClient(socket, i + 1, this);
				connexions[i] = connexion;

				String[] champsJoin = Protocole.decouper(connexion.lireLigne());
				noms[i] = champsJoin.length > 1 && !champsJoin[1].isEmpty() ? champsJoin[1] : ("Joueur" + (i + 1));

				connexion.envoyer(Protocole.message(Protocole.BIENVENUE, String.valueOf(i + 1)));
				System.out.println("Joueur " + (i + 1) + " connecté : " + noms[i]);
				if (i == 0) {
					connexion.envoyer(Protocole.message(Protocole.ATTENTE_ADVERSAIRE));
				}
			}

			jeu = Jeu.initJeu(noms[0], noms[1]);

			connexions[0].demarrerEcoute();
			connexions[1].demarrerEcoute();

			diffuserEtat();

			// Garde le process vivant tant que la partie n'est pas finie ; les threads d'écoute
			// des deux ConnexionClient font tout le travail via traiterMessage().
			while (!partieTerminee) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
			connexions[0].fermer();
			connexions[1].fermer();
			System.out.println("Partie terminée, arrêt du serveur.");
		}
	}

	public synchronized void traiterMessage(int numJoueur, String ligne) {
		if (partieTerminee || ligne == null || ligne.isEmpty()) {
			return;
		}
		String[] champs = Protocole.decouper(ligne);
		String type = champs[0];

		try {
			switch (type) {
			case Protocole.JOUER_CARTE:
				traiterJouerCarte(numJoueur, Integer.parseInt(champs[1]));
				break;
			case Protocole.FIN_TOUR:
				traiterFinTour(numJoueur);
				break;
			case Protocole.CHOIX_BANC:
				traiterChoixBanc(numJoueur, Integer.parseInt(champs[1]));
				break;
			default:
				envoyerErreur(numJoueur, "message inconnu : " + type);
			}
		} catch (RuntimeException e) {
			envoyerErreur(numJoueur, "requête invalide");
		}
	}

	private void traiterJouerCarte(int numJoueur, int indexCarte) {
		int index = numJoueur - 1;
		if (!verifierEstSonTour(numJoueur) || indexCarteEnAttenteDeSlot != null) {
			envoyerErreur(numJoueur, "ce n'est pas le moment de jouer une carte");
			return;
		}

		Joueur joueur = Jeu.joueurs[index];
		Joueur adversaire = Jeu.joueurs[1 - index];

		if (indexCarte < 0 || indexCarte >= joueur.getNbCarteEnMain()) {
			envoyerErreur(numJoueur, "index de carte invalide");
			return;
		}
		if (!(joueur.getPointDAction() > 0 && joueur.getPointDActionJouerCarte() > 0)) {
			envoyerErreur(numJoueur, "vous ne pouvez jouer qu'une carte à la fois");
			return;
		}

		Carte carte = joueur.getMain()[indexCarte];

		if (!joueur.effetBloqueParAdversaire(adversaire) && joueur.banIPleinPourCarte(carte)) {
			indexCarteEnAttenteDeSlot = indexCarte;
			connexions[index].envoyer(Protocole.message(Protocole.BANC_PLEIN_CHOIX));
			return;
		}

		joueur.jouerCarte(adversaire, indexCarte);
		finaliserCoup(joueur, indexCarte);
	}

	private void traiterChoixBanc(int numJoueur, int slot) {
		int index = numJoueur - 1;
		if (!verifierEstSonTour(numJoueur) || indexCarteEnAttenteDeSlot == null) {
			envoyerErreur(numJoueur, "aucun choix de banc en attente");
			return;
		}
		if (slot < 1 || slot > 5) {
			envoyerErreur(numJoueur, "le slot doit être entre 1 et 5");
			return;
		}

		Joueur joueur = Jeu.joueurs[index];
		Joueur adversaire = Jeu.joueurs[1 - index];
		int indexCarte = indexCarteEnAttenteDeSlot;
		indexCarteEnAttenteDeSlot = null;

		joueur.jouerCarteAvecSlotBanc(adversaire, indexCarte, slot);
		finaliserCoup(joueur, indexCarte);
	}

	private void finaliserCoup(Joueur joueur, int indexCarte) {
		joueur.retirerCarte(indexCarte);
		joueur.setRetirerNbCarteEnMain();
		joueur.trierCarte(indexCarte);
		joueur.modifierPointDAction(-1);
		joueur.setPointDActionJouerCarte(-1);
		verifierVictoireEtDiffuser();
	}

	private void traiterFinTour(int numJoueur) {
		if (!verifierEstSonTour(numJoueur) || indexCarteEnAttenteDeSlot != null) {
			envoyerErreur(numJoueur, "vous ne pouvez pas finir votre tour maintenant");
			return;
		}

		nbTour++;
		Joueur joueur = Jeu.joueurs[joueurCourant];
		joueur.modifierPointDAction(1);
		joueur.reinitialiserPointDActionJouerCarte();

		joueurCourant = nbTour % 2;
		Jeu.piocher(jeu.getPioche(), Jeu.joueurs[joueurCourant]);

		diffuserEtat();
	}

	private boolean verifierEstSonTour(int numJoueur) {
		return numJoueur - 1 == joueurCourant;
	}

	private void envoyerErreur(int numJoueur, String message) {
		connexions[numJoueur - 1].envoyer(Protocole.message(Protocole.ERREUR, "REFUS", message));
	}

	private void verifierVictoireEtDiffuser() {
		Joueur j1 = Jeu.joueurs[0];
		Joueur j2 = Jeu.joueurs[1];

		String gagnant = null;
		String type = null;
		if (j1.getPv() < 1) {
			gagnant = j2.getNom();
			type = "assassinat";
		} else if (j2.getPv() < 1) {
			gagnant = j1.getNom();
			type = "assassinat";
		} else if (j1.getPopularite() >= POPULARITE_VICTOIRE) {
			gagnant = j1.getNom();
			type = "popularite";
		} else if (j2.getPopularite() >= POPULARITE_VICTOIRE) {
			gagnant = j2.getNom();
			type = "popularite";
		}

		if (gagnant != null) {
			partieTerminee = true;
			String messageVictoire = Protocole.message(Protocole.VICTOIRE, gagnant, type);
			connexions[0].envoyer(messageVictoire);
			connexions[1].envoyer(messageVictoire);
		} else {
			diffuserEtat();
		}
	}

	private void diffuserEtat() {
		connexions[0].envoyer(construireEtat(0));
		connexions[1].envoyer(construireEtat(1));
	}

	private String construireEtat(int indexDestinataire) {
		Joueur vous = Jeu.joueurs[indexDestinataire];
		Joueur adv = Jeu.joueurs[1 - indexDestinataire];

		List<String> mainVousNoms = new ArrayList<>();
		for (int i = 0; i < vous.getNbCarteEnMain(); i++) {
			mainVousNoms.add(vous.getMain()[i].getDescription().name());
		}
		List<String> bancVousNoms = new ArrayList<>();
		for (int i = 0; i < vous.getCarteBancRestante(); i++) {
			bancVousNoms.add(vous.getBanc().getBanc()[i].getDescription().name());
		}
		List<String> bancAdvNoms = new ArrayList<>();
		for (int i = 0; i < adv.getCarteBancRestante(); i++) {
			bancAdvNoms.add(adv.getBanc().getBanc()[i].getDescription().name());
		}

		return Protocole.message(
				Protocole.ETAT,
				String.valueOf(vous.getPv()),
				String.valueOf(vous.getPopularite()),
				String.valueOf(vous.getPointDAction()),
				String.valueOf(vous.getPointDActionJouerCarte()),
				Protocole.joindreListe(mainVousNoms),
				Protocole.joindreListe(bancVousNoms),
				String.valueOf(adv.getPv()),
				String.valueOf(adv.getPopularite()),
				String.valueOf(adv.getNbCarteEnMain()),
				Protocole.joindreListe(bancAdvNoms),
				String.valueOf(jeu.getPioche().getCarteRestantePioche()),
				String.valueOf(joueurCourant + 1),
				String.valueOf(nbTour));
	}

	public synchronized void signalerDeconnexion(int numJoueur) {
		if (partieTerminee) {
			return;
		}
		partieTerminee = true;
		int autre = numJoueur == 1 ? 2 : 1;
		if (connexions[autre - 1] != null) {
			connexions[autre - 1].envoyer(Protocole.message(Protocole.ADVERSAIRE_DECONNECTE));
		}
	}
}
