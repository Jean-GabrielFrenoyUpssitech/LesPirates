package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/* Enveloppe une connexion socket vers un des deux joueurs côté serveur. */
public class ConnexionClient {

	private final Socket socket;
	private final BufferedReader lecteur;
	private final PrintWriter ecrivain;
	private final int numJoueur;
	private final ServeurJeu serveur;

	public ConnexionClient(Socket socket, int numJoueur, ServeurJeu serveur) throws IOException {
		this.socket = socket;
		this.numJoueur = numJoueur;
		this.serveur = serveur;
		this.lecteur = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
		this.ecrivain = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
	}

	public int getNumJoueur() {
		return numJoueur;
	}

	public void envoyer(String message) {
		ecrivain.println(message);
	}

	/* Lecture bloquante d'une ligne, utilisée uniquement pour la poignée de main initiale (JOIN) */
	public String lireLigne() throws IOException {
		return lecteur.readLine();
	}

	/* Démarre le thread d'écoute permanent, une fois la partie initialisée */
	public void demarrerEcoute() {
		Thread threadLecture = new Thread(this::ecouter, "lecture-joueur-" + numJoueur);
		threadLecture.setDaemon(true);
		threadLecture.start();
	}

	private void ecouter() {
		try {
			String ligne;
			while ((ligne = lecteur.readLine()) != null) {
				serveur.traiterMessage(numJoueur, ligne);
			}
		} catch (IOException ignored) {
		} finally {
			serveur.signalerDeconnexion(numJoueur);
		}
	}

	public void fermer() {
		try {
			socket.close();
		} catch (IOException ignored) {
		}
	}
}
