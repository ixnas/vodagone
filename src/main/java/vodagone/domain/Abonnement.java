package vodagone.domain;

public class Abonnement implements MappableObject {

	private int id;
	private String aanbieder;
	private String naam;

	private float prijsPerMaand;

	private boolean deelbaar;
	private boolean verdubbeling;

	public Abonnement (int id, String aanbieder, String naam, float prijsPerMaand, boolean deelbaar, boolean verdubbeling) {
		this.id = id;
		this.aanbieder = aanbieder;
		this.naam = naam;
		this.prijsPerMaand = prijsPerMaand;
		this.deelbaar = deelbaar;
		this.verdubbeling = verdubbeling;
	}

	public int getId () {
		return id;
	}

	public void setId (int id) {
		this.id = id;
	}

	public String getAanbieder () {
		return aanbieder;
	}

	public void setAanbieder (String aanbieder) {
		this.aanbieder = aanbieder;
	}

	public String getNaam () {
		return naam;
	}

	public void setNaam (String naam) {
		this.naam = naam;
	}

	public float getPrijsPerMaand () {
		return prijsPerMaand;
	}

	public void setPrijsPerMaand (float prijsPerMaand) {
		this.prijsPerMaand = prijsPerMaand;
	}

	public boolean isDeelbaar () {
		return deelbaar;
	}

	public void setDeelbaar (boolean deelbaar) {
		this.deelbaar = deelbaar;
	}

	public boolean isVerdubbeling () {
		return verdubbeling;
	}

	public void setVerdubbeling (boolean verdubbeling) {
		this.verdubbeling = verdubbeling;
	}

	// Dit moet je fixen
	public String getEmail () { return null; }
}
