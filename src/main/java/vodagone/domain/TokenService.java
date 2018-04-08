package vodagone.domain;

import org.json.simple.JSONObject;
import vodagone.data.AbonneeMapper;

import java.util.ArrayList;

public class TokenService {

	AbonneeMapper abonneeMapper;
	ArrayList<Token> tokens;

	public TokenService (AbonneeMapper abonneeMapper) {
		this.abonneeMapper = abonneeMapper;
		this.tokens = new ArrayList<Token> ();
	}

	private boolean tokenAvailable (int id) {
		for (Token token : tokens) {
			if (token.getAbonneeId () == id) {
				return true;
			}
		}
		return false;
	}

	private Token getTokenById (int id) {
		for (Token token : tokens) {
			if (token.getAbonneeId () == id) {
				return token;
			}
		}
		return null;
	}

	private String getLoginResponse (Abonnee abonnee) {
		JSONObject result = new JSONObject ();
		result.put ("token", getTokenById (abonnee.getId ()).getToken ());
		result.put ("user", abonnee.getNaam ());
		return result.toJSONString ();
	}

	public String login (String email, String wachtwoord) {
		Abonnee abonnee = abonneeMapper.read (email);
		if (abonnee != null) {
			if (abonnee.getWachtwoord ().equals (wachtwoord)) {
				if (!tokenAvailable (abonnee.getId ())) {
					tokens.add (new Token (abonnee.getId ()));
				}
				return getLoginResponse (abonnee);
			}
		}
		return null;
	}

	public Integer getAbonneeIdByToken (String tokenString) {
		for (Token token : tokens) {
			if (token.getToken ().equals (tokenString)) {
				return token.getAbonneeId ();
			}
		}
		return null;
	}

}
