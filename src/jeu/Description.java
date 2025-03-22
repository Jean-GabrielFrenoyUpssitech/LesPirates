package jeu;

public enum Description {
MAINDEFER("Main de Fer","La main de fer fait...","Popularité"),
REVOLTEORGANISEE("Revolte Organisé", "Descritpion..","Popularité"),
DISCOURSINSPIRANT("Discours Inspirant","Descritpion..","Popularité"),
ABORDAGEREUSSI("Abordage R�ussi","Descrption..","Popularité"),
COUPDESABRE("Coup de Sabre","Description","PV"),
BLOCAGEDEFENSIF("Blocage Défensif","Descrption","Stratégie"),
PLANMACHIAVELIQUE("Plan Machiavélique","Réduit les PV de l'adversaire de 3. Cette carte ne peut être jouée que si l'adversaire a plus de 2 PV - coute 1 de popularité","PV"),
ECHANGEFORCE("Echange Forcé"," Le et l'adversaire doivent échanger leur main entre eux - coute 2 de popularité","Stratégie");


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
