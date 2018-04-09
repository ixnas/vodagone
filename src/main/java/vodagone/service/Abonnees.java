package vodagone.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import vodagone.data.AbonneeAbonnementMapper;
import vodagone.domain.Abonnee;
import vodagone.data.AbonneeMapper;
import vodagone.domain.AbonneeAbonnement;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class Abonnees {

	private TokenService tokenService;
	private AbonneeMapper abonneeMapper;
	private AbonneeAbonnementMapper abonneeAbonnementMapper;

	private String genereerAbonneesJSON (ArrayList <Abonnee> abonnees) {
		JSONArray abonneesJSON = new JSONArray ();
		for (Abonnee abonnee : abonnees) {
			JSONObject abonneeJSON = new JSONObject ();
			abonneeJSON.put ("id", abonnee.getId ());
			abonneeJSON.put ("name", abonnee.getNaam ());
			abonneeJSON.put ("email", abonnee.getEmail ());
			abonneesJSON.add (abonneeJSON);
		}
		return abonneesJSON.toJSONString ();
	}

	private String genereerAbonneeJSON (Abonnee abonnee) {
		JSONObject abonneeJSON = new JSONObject ();
		abonneeJSON.put ("id", abonnee.getId ());
		abonneeJSON.put ("name", abonnee.getNaam ());
		abonneeJSON.put ("email", abonnee.getEmail ());
		return abonneeJSON.toJSONString ();
	}

	public Abonnees (AbonneeMapper abonneeMapper, AbonneeAbonnementMapper abonneeAbonnementMapper, TokenService tokenService) {
		this.abonneeMapper = abonneeMapper;
		this.abonneeAbonnementMapper = abonneeAbonnementMapper;
		this.tokenService = tokenService;
	}

	public Response getAll (String token) {
		if (tokenService.getAbonneeIdByToken (token) != null) {
			try {
				return Response.ok (genereerAbonneesJSON (abonneeMapper.readAll ()), MediaType.APPLICATION_JSON).build ();
			} catch (Exception e) {
				e.printStackTrace ();
				return ErrorMessenger.generate (404, "Not Found");
			}
		}
		return ErrorMessenger.generate (403, "Forbidden");
	}

	public Response get (int id) {
		try {
			return Response.ok (genereerAbonneeJSON (abonneeMapper.read (id)), MediaType.APPLICATION_JSON).build ();
		} catch (Exception e) {
			e.printStackTrace ();
			return ErrorMessenger.generate (404, "Not Found");
		}
	}

	public Response share (String body, String token, int id) {
		Integer eigenaar = tokenService.getAbonneeIdByToken (token);
		if (eigenaar != null) {
			try {
				JSONParser parser = new JSONParser ();
				JSONObject obj = (JSONObject) parser.parse (body);
				if (obj.get ("id") != null && eigenaar != id) {
					int abonnementId = Math.toIntExact ((long) obj.get ("id"));
					AbonneeAbonnement abonneeAbonnement = abonneeAbonnementMapper.read (abonnementId);
					if (abonneeAbonnement.getAbonnee ().getId () == eigenaar) {
						abonneeAbonnement.setGedeeldMet (id);
						if (abonneeAbonnementMapper.update (abonneeAbonnement)) {
							return ErrorMessenger.generate (200, "OK");
						}
						return ErrorMessenger.generate (400, "Bad Request");
					}
					return ErrorMessenger.generate (403, "Forbidden");
				}
				return ErrorMessenger.generate (400, "Bad Request");
			} catch (Exception e) {
				e.printStackTrace ();
			}
		}
		return ErrorMessenger.generate (403, "Forbidden");
	}
}
