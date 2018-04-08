package vodagone.domain;

import java.util.UUID;

public class Token {

	private String token;
	private int abonneeId;

	public Token (int abonneeId) {
		this.abonneeId = abonneeId;
		this.token = UUID.randomUUID().toString();
	}

	public String getToken () {
		return token;
	}

	public int getAbonneeId () {
		return abonneeId;
	}

}
