package jeu;

public enum Description {
MAINDEFER("Main de Fer","Tu as perdu ta main main lors du dernier abordage.. On l'a remplacé par une main de fer. Tous le monde à trouver ça cool. Tu gagnes un point en popularité mais perd un PV - coute 0 de popularité","Popularité","maindefer"),
REVOLTEORGANISEE("Revolte Organisé", "Tu prévois un révolte avec quelques autre membre de l'équipage. Tu gagnes 3 point de popularité - coute 0 de popularité","Popularité","revolteorganise"),
DISCOURSINSPIRANT("Discours Inspirant","Tu sors ton meilleur discours à l'équipage, il a faillit s'endormir mais tu gagnes quand même 1 point de popularité - coute 0 de popularité","Popularité","discoursinspirant"),
ABORDAGEREUSSI("Abordage R�ussi","Tu réussi à aborder le bateau ennemi ! Gagne 2 en popularité - coute 0 de popularité","Popularité","abordagereussi"),
COUPDESABRE("Coup de Sabre","Tout est dans le nom, l'adversaire perd 2 PV - coute 0 de popularité","PV","coupdesabre"),
BLOCAGEDEFENSIF("Blocage Défensif","Annule l'effet de la prochaine carte de l'adversaire - coute 0 de popularité","Stratégie","blocagedefensif"),
PLANMACHIAVELIQUE("Plan Machiavélique","Réduit les PV de l'adversaire de 3. Cette carte ne peut être jouée que si l'adversaire a plus de 3 PV Sinon fait passé ton tour - coute 1 de popularité","PV","planmachiavelique"),
ECHANGEFORCE("Echange Forcé"," Le joueur et l'adversaire doivent échanger leur main entre eux - coute 2 de popularité","Stratégie","echangeforce");


private String nom;
private String descriptionCarte;
private String type;
private String nomFichier;

	
private Description(String nom,String description, String type, String nomFichier) {
	this.nom=nom;
	this.descriptionCarte=description;
	this.type=type;
	this.nomFichier=nomFichier;
}


public String getDescription() {
	return descriptionCarte;
}

public String getNom() {
	return nom;
}


public String getType() {
	return type;
}

public String getNomFichier() {
	return nomFichier;
}



}
