package vodagone.domain;

import java.util.Date;

public class AbonneeAbonnement implements MappableObject {

	private int id;
	private Abonnee abonnee;
	private Abonnement abonnement;
	private Date startDatum;
	private String status;
	private String verdubbeling;

	public AbonneeAbonnement (int id, Abonnee abonnee, Abonnement abonnement, Date startDatum, String status, String verdubbeling) {
		this.id = id;
		this.abonnee = abonnee;
		this.abonnement = abonnement;
		this.startDatum = startDatum;
		this.status = status;
		this.verdubbeling = verdubbeling;
	}

	public int getId () {
		return id;
	}

	public void setId (int id) {
		this.id = id;
	}

	public Abonnee getAbonnee () {
		return abonnee;
	}

	public void setAbonnee (Abonnee abonnee) {
		this.abonnee = abonnee;
	}

	public Abonnement getAbonnement () {
		return abonnement;
	}

	public void setAbonnement (Abonnement abonnement) {
		this.abonnement = abonnement;
	}

	public Date getStartDatum () {
		return startDatum;
	}

	public void setStartDatum (Date startDatum) {
		this.startDatum = startDatum;
	}

	public String getStatus () {
		return status;
	}

	public void setStatus (String status) {
		if (status.equals ("proef") || status.equals ("standaard") || status.equals ("opgezegd")) {
			this.status = status;
		}
	}

	public String getVerdubbeling () {
		return verdubbeling;
	}

	public void setVerdubbeling (String verdubbeling) {
		this.verdubbeling = verdubbeling;
	}

	public String getEmail () {
		return null;
	}
}
