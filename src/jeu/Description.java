package jeu;

public enum Description {
MAINDEFER("Main de Fer","La main de fer fait...","Popularité"),REVOLTEORGANISEE("Revolte Organisé", "Descritpion..","Popularité"),DISCOURSINSPIRANT("Discours Inspirant","Descritpion..","Popularité"),ABORDAGEREUSSI("Abordage R�ussi","Descrption..","Popularité"),COUPDESABRE("Coup de Sabre","Description","PV");


private String nom;
private String descriptionCarte;
private String type;

	
private Description(String nom,String description, String type) {
	this.nom=nom;
	this.descriptionCarte=description;
	this.type=type;
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




}
