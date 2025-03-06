package jeu;

public class Banc {
	Carte[] banc = new Carte[5];
	private int carteRestante = 0;

	public void modifCarteRestante() {
		this.carteRestante--;
	}

	public int getCarteRestante() {
		return carteRestante;
	}

	public Carte[] getBanc() {
		return banc;
	}

}
