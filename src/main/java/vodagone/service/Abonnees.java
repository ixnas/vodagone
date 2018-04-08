package vodagone.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import vodagone.domain.Abonnee;
import vodagone.data.AbonneeMapper;
import vodagone.domain.TokenService;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class Abonnees {

	TokenService tokenService;
	AbonneeMapper abonneeMapper;

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

	public Abonnees (AbonneeMapper abonneeMapper, TokenService tokenService) {
		this.abonneeMapper = abonneeMapper;
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
}
