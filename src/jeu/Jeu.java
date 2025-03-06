package jeu;

import affichage.IAffichage;

public class Jeu implements IAffichage{
	private Carte[] deck = new Carte[40];
	private Joueur[] joueurs = new Joueur[2];
	public Jeu(Joueur[] joueurs) {
		this.joueurs = joueurs;
	}

}
//reste à faire deck + pioche +regle +contexte + getTourJoueur+ modifiler afficher main(adv+joueur)+ donnerStatusJoeurs
