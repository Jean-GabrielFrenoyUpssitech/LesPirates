package jeu;

public enum Effets {
MAINDEFER("Main de Fer","La main de fer fait...","Popularit�"),REVOLTEORGANISEE("Revolte Organis�", "Descritpion..","Popularit�"),DISCOURSINSPIRANT("Discours Inspirant","Descritpion..","Popularit�"),ABORDAGEREUSSI("Abordage R�ussi","Descrption..","Popularit�"),COUPDESABRE("Coup de Sabre","Description","PV");


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
