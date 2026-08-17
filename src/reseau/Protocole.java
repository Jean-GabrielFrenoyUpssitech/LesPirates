package reseau;

import java.util.List;

/* Définit le format des messages échangés entre le client (InterfaceJeu) et le serveur (ServeurJeu).
 * Une ligne = un message, champs séparés par '|', sous-listes séparées par ','. */
public final class Protocole {

	private Protocole() {
	}

	private static final String SEP = "|";
	private static final String SEP_REGEX = "\\|";
	private static final String SEP_LISTE = ",";

	// Client vers Serveur
	public static final String JOIN = "JOIN";
	public static final String JOUER_CARTE = "JOUER_CARTE";
	public static final String FIN_TOUR = "FIN_TOUR";
	public static final String CHOIX_BANC = "CHOIX_BANC";

	// Serveur vers Client
	public static final String BIENVENUE = "BIENVENUE";
	public static final String ATTENTE_ADVERSAIRE = "ATTENTE_ADVERSAIRE";
	public static final String ETAT = "ETAT";
	public static final String BANC_PLEIN_CHOIX = "BANC_PLEIN_CHOIX";
	public static final String ERREUR = "ERREUR";
	public static final String VICTOIRE = "VICTOIRE";
	public static final String ADVERSAIRE_DECONNECTE = "ADVERSAIRE_DECONNECTE";

	// Signal purement local au client : sa propre connexion socket est tombée
	// (à ne jamais envoyer sur le réseau, juste utilisé pour faire remonter l'info à l'UI)
	public static final String CONNEXION_PERDUE = "CONNEXION_PERDUE";

	public static String message(String... champs) {
		return String.join(SEP, champs);
	}

	public static String[] decouper(String ligne) {
		if (ligne == null) {
			return new String[0];
		}
		return ligne.split(SEP_REGEX, -1);
	}

	public static String joindreListe(List<String> valeurs) {
		return String.join(SEP_LISTE, valeurs);
	}

	public static String[] decouperListe(String valeur) {
		if (valeur == null || valeur.isEmpty()) {
			return new String[0];
		}
		return valeur.split(SEP_LISTE, -1);
	}
}
