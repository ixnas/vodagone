package vodagone.domain;

public class Abonnee implements MappableObject {

	private int id;
	private String naam;
	private String email;
	private String wachtwoord;

	public Abonnee (int id, String naam, String email, String wachtwoord) {
		this.id = id;
		this.naam = naam;
		this.email = email;
		this.wachtwoord = wachtwoord;
	}

	public int getId () {
		return id;
	}

	public String getNaam () {
		return naam;
	}

	public void setNaam (String naam) {
		this.naam = naam;
	}

	public String getEmail () {
		return email;
	}

	public void setEmail (String email) {
		this.email = email;
	}

	public String getWachtwoord () {
		return wachtwoord;
	}

	public void setWachtwoord (String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}
}
