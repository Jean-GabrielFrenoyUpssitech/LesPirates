package jeu;

public enum Effets {
MAINDEFER("Main de Fer","La main de fer fait...","Popularité"),REVOLTEORGANISEE("Revolte Organisé", "Descritpion..","Popularité"),DISCOURSINSPIRANT("Discours Inspirant","Descritpion..","Popularité"),ABORDAGEREUSSI("Abordage Réussi","Descrption..","Popularité"),COUPDESABRE("Coup de Sabre","Description","PV");


private String nom;
private String description;
private String type;

	
private Effets(String nom,String description, String type) {
	this.nom=nom;
	this.description=description;
	this.type=type;
}


public String getDescription() {
	return description;
}

public String getNom() {
	return nom;
}


public String getType() {
	return type;
}




}
