package jeu;

public enum Effets {
MAINDEFER(2,"du joueur",-1,"du joueur"),REVOLTEORGANISEE(1,"du joueur",0,"du joueur"),DISCOURSINSPIRANT(1,"du joueur",0,"du joueur"),ABORDAGEREUSSI(1,"du joueur",0,"du joueur"),COUPDESABRE(0,"du joueur",-2,"de l'adversaire");

private int pop;
private int pv;
private String cibleEffetPop;
private String cibleEffetPv;

	
private Effets(int pop,String cibleEffetPop, int pv,String cibleEffetPv) {
	this.pop = pop;
	this.cibleEffetPop=cibleEffetPop;
	this.pv = pv;
	this.cibleEffetPv=cibleEffetPv;
}


public int getPop() {
	return pop;
}

public int getPv() {
	return pv;
}


public String getCibleEffetPop() {
	return cibleEffetPop;
}


public String getCibleEffetPv() {
	return cibleEffetPv;
}


}
