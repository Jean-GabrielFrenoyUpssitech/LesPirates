package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentLinkedQueue;

/* Connexion client vers le ServeurJeu. Un thread d'écoute dédié empile les lignes reçues dans
 * une queue thread-safe ; l'appelant (la boucle draw() d'InterfaceJeu, ou un harnais console)
 * doit dépiler avec prochainMessage() sur SON propre thread avant d'en lire le contenu. */
public class ClientReseau {

	private Socket socket;
	private BufferedReader lecteur;
	private PrintWriter ecrivain;
	private final ConcurrentLinkedQueue<String> messagesEntrants = new ConcurrentLinkedQueue<>();
	private volatile boolean connecte = false;

	public void connecter(String hote, int port) throws IOException {
		socket = new Socket(hote, port);
		lecteur = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
		ecrivain = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
		connecte = true;

		Thread threadLecture = new Thread(this::ecouter, "lecture-reseau");
		threadLecture.setDaemon(true);
		threadLecture.start();
	}

	private void ecouter() {
		try {
			String ligne;
			while ((ligne = lecteur.readLine()) != null) {
				messagesEntrants.add(ligne);
			}
		} catch (IOException ignored) {
			// connexion coupée, traité dans le finally
		} finally {
			connecte = false;
			messagesEntrants.add(Protocole.CONNEXION_PERDUE);
		}
	}

	public void envoyer(String message) {
		if (ecrivain != null) {
			ecrivain.println(message);
		}
	}

	/* Retire et retourne le plus ancien message reçu, ou null s'il n'y en a pas.
	 * À appeler depuis un seul thread "consommateur" (le thread de rendu) pour rester simple. */
	public String prochainMessage() {
		return messagesEntrants.poll();
	}

	public boolean estConnecte() {
		return connecte;
	}

	public void fermer() {
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (IOException ignored) {
		}
	}
}
