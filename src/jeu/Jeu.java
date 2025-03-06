package jeu;

import affichage.Affichage;

public class Jeu implements Affichage{
	private Carte[] deck = new Carte[40];
	private Joueur[] joueurs = new Joueur[2];
	public Jeu(Joueur[] joueurs) {
		this.joueurs = joueurs;
	}

}
//reste à faire deck + pioche +regle +contexte + getTourJoueur+ modifiler afficher main(adv+joueur)+ donnerStatusJoeurs
