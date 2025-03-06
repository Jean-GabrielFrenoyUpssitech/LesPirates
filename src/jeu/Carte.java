package jeu;

public class Carte {
	private String type;
	private String nom;
	private Effets effetCarte;

	public Carte(String type, String nom, Effets effet) {
		this.type = type;
		this.nom = nom;
		this.effetCarte = effet;
	}

	public String getType() {
		return type;
	}

	public String getNom() {
		return nom;
	}

	public Effets getEffetCarte() {
		return effetCarte;
	}


	public Carte getCarteRevolteOrganisee() {
		Carte revolteOrganisee = new Carte("popularité", "Révolte Organisée", Effets.REVOLTEORGANISEE);
		return revolteOrganisee;
	}
	public Carte getCarteMainDeFer() {
		Carte mainDeFer = new Carte("popularité", "Révolte Organisée", Effets.MAINDEFER);
		return mainDeFer;
	}
}
