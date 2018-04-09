package vodagone.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import vodagone.data.IAbonneeAbonnementMapper;
import vodagone.domain.AbonneeAbonnement;
import vodagone.domain.Abonnement;
import vodagone.data.IAbonnementMapper;

public class Abonnementen {

	TokenService tokenService;
	IAbonnementMapper abonnementMapper;
	IAbonneeAbonnementMapper abonneeAbonnementMapper;

	private String genereerError (String error) {
		JSONObject errorMessage = new JSONObject ();
		errorMessage.put ("error", error);
		return errorMessage.toJSONString ();
	}

	private float berekenTotalPrice (ArrayList <AbonneeAbonnement> abonneeAbonnementen) {
		float totalPrice = 0;
		for (AbonneeAbonnement abonneeAbonnement : abonneeAbonnementen)
			if (!abonneeAbonnement.getStatus ().equals ("opgezegd")) {
				float prijs = abonneeAbonnement.getAbonnement ().getPrijsPerMaand ();
				if (abonneeAbonnement.getVerdubbeling ().equals ("verdubbeld")) {
					prijs = prijs * 2;
				}
				totalPrice += prijs;
			}
		return totalPrice;
	}

	private JSONArray vulAbonnementenArray (ArrayList <AbonneeAbonnement> abonneeAbonnementen) {
		JSONArray abonnementenArray = new JSONArray ();
		for (AbonneeAbonnement abonneeAbonnement : abonneeAbonnementen) {
			if (!abonneeAbonnement.getStatus ().equals ("opgezegd")) {
				JSONObject abonnementObject = new JSONObject ();
				abonnementObject.put ("id", abonneeAbonnement.getId ());
				abonnementObject.put ("aanbieder", abonneeAbonnement.getAbonnement ().getAanbieder ());
				abonnementObject.put ("dienst", abonneeAbonnement.getAbonnement ().getNaam ());
				abonnementenArray.add (abonnementObject);
			}
		}
		return abonnementenArray;
	}

	private String genereerAbonnementenJSON (ArrayList <AbonneeAbonnement> abonneeAbonnementen) {
		JSONObject abonnementenJSON = new JSONObject ();
		abonnementenJSON.put ("abonnementen", vulAbonnementenArray (abonneeAbonnementen));
		abonnementenJSON.put ("totalPrice", berekenTotalPrice (abonneeAbonnementen));
		return abonnementenJSON.toJSONString ();
	}

	private String genereerAbonnementJSON (AbonneeAbonnement abonneeAbonnement) {
		JSONObject abonnementenJSON = new JSONObject ();
		float prijs = abonneeAbonnement.getAbonnement ().getPrijsPerMaand ();
		if (abonneeAbonnement.getVerdubbeling ().equals ("verdubbeld")) {
			prijs = prijs * 2;
		}
		DateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
		abonnementenJSON.put ("id", abonneeAbonnement.getId ());
		abonnementenJSON.put ("aanbieder", abonneeAbonnement.getAbonnement ().getAanbieder ());
		abonnementenJSON.put ("dienst", abonneeAbonnement.getAbonnement ().getNaam ());
		abonnementenJSON.put ("prijs", "€" + String.format ("%.2f", prijs) + " per maand");
		abonnementenJSON.put ("deelbaar", abonneeAbonnement.getAbonnement ().isDeelbaar ());
		abonnementenJSON.put ("startDatum", df.format (abonneeAbonnement.getStartDatum ()));
		abonnementenJSON.put ("verdubbeling", abonneeAbonnement.getVerdubbeling ());
		abonnementenJSON.put ("status", abonneeAbonnement.getStatus ());
		return abonnementenJSON.toJSONString ();
	}

	private String genereerAlleAbonnementenJSON (ArrayList <Abonnement> abonnementen) {
		JSONArray abonnementenJSON = new JSONArray ();
		for (Abonnement abonnement : abonnementen) {
			JSONObject abonnementJSON = new JSONObject ();
			abonnementJSON.put ("id", abonnement.getId ());
			abonnementJSON.put ("aanbieder", abonnement.getAanbieder ());
			abonnementJSON.put ("dienst", abonnement.getNaam ());
			abonnementenJSON.add (abonnementJSON);
		}
		return abonnementenJSON.toJSONString ();
	}

	public Abonnementen (IAbonnementMapper abonnementMapper, IAbonneeAbonnementMapper abonneeAbonnementMapper, TokenService tokenService) {
		this.abonnementMapper = abonnementMapper;
		this.abonneeAbonnementMapper = abonneeAbonnementMapper;
		this.tokenService = tokenService;
	}

	/**
	 * Method handling HTTP GET requests. The returned object will be sent
	 * to the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */

	public Response getAll (String token, String filter) {
		if (tokenService.getAbonneeIdByToken (token) != null) {
			try {
				return Response.ok (genereerAlleAbonnementenJSON (abonnementMapper.readFilter (filter)), MediaType.APPLICATION_JSON).build ();
			} catch (Exception e) {
				e.printStackTrace ();
				return ErrorMessenger.generate (404, "Not Found");
			}
		}
		return ErrorMessenger.generate (403, "Forbidden");
	}

	public Response getAllFromUser (String token, boolean created) {
		Integer abonneeId = tokenService.getAbonneeIdByToken (token);
		if (abonneeId != null) {
			try {
				ArrayList <AbonneeAbonnement> abonneeAbonnementen = abonneeAbonnementMapper.readAllFromAbonnee (abonneeId);
				if (created) {
					return Response.status (201).entity (genereerAbonnementenJSON (abonneeAbonnementen)).build ();
				}
				return Response.ok (genereerAbonnementenJSON (abonneeAbonnementen), MediaType.APPLICATION_JSON).build ();
			} catch (Exception e) {
				e.printStackTrace ();
				return ErrorMessenger.generate (404, "Not Found");
			}
		} else {
			return ErrorMessenger.generate (403, "Forbidden");
		}
	}

	public Response get (String token, int id) {
		Integer abonneeId = tokenService.getAbonneeIdByToken (token);
		AbonneeAbonnement abonneeAbonnement = abonneeAbonnementMapper.read (id);
		if (abonneeId != null && abonneeAbonnement != null) {
			if (abonneeId == abonneeAbonnement.getAbonnee ().getId ()) {
				try {
					return Response.ok (genereerAbonnementJSON (abonneeAbonnement), MediaType.APPLICATION_JSON).build ();
				} catch (Exception e) {
					e.printStackTrace ();
					return ErrorMessenger.generate (404, "Not Found");
				}
			}
		}
		return ErrorMessenger.generate (403, "Forbidden");
	}

	public Response newAbonnement (String body, String token) {
		Integer abonneeId = tokenService.getAbonneeIdByToken (token);
		if (abonneeId != null) {
			try {
				JSONParser parser = new JSONParser ();
				JSONObject obj = (JSONObject) parser.parse (body);
				if (obj.get ("id") == null || obj.get ("aanbieder") == null) {
					return ErrorMessenger.generate (400, "Bad Request");
				}
				int abonnementId = Math.toIntExact ((long) obj.get ("id"));
				if (abonneeAbonnementMapper.create (abonneeId, abonnementId)) {
					return getAllFromUser (token, true);
				}
				return ErrorMessenger.generate (400, "Bad Request");
			} catch (Exception e) {
				e.printStackTrace ();
				return ErrorMessenger.generate (404, "Not Found");
			}
		}
		return ErrorMessenger.generate (400, "Bad Request");
	}

	public Response terminate (String token, int id) {
		Integer abonneeId = tokenService.getAbonneeIdByToken (token);
		AbonneeAbonnement abonneeAbonnement = abonneeAbonnementMapper.read (id);
		if (abonneeId != null && abonneeAbonnement != null) {
			if (abonneeAbonnement.getStatus ().equals ("opgezegd")) {
				return ErrorMessenger.generate (400, "Bad Request");
			}
			if (abonneeAbonnement.getAbonnee ().getId () == abonneeId) {
				try {
					if (abonneeAbonnementMapper.delete (abonneeAbonnement)) {
						return get (token, id);
					}
					return ErrorMessenger.generate (400, "Bad Request");
				} catch (Exception e) {
					e.printStackTrace ();
					return ErrorMessenger.generate (400, "Bad Request");
				}
			}
		}
		return ErrorMessenger.generate (400, "Bad Request");
	}

	public Response upgrade (String body, String token, int id) {
		Integer abonneeId = tokenService.getAbonneeIdByToken (token);
		AbonneeAbonnement abonneeAbonnement = abonneeAbonnementMapper.read (id);
		if (abonneeId != null && abonneeAbonnement != null) {
			if (abonneeAbonnement.getVerdubbeling ().equals ("opgezegd")) {
				return ErrorMessenger.generate (400, "Bad Request");
			}
			if (abonneeAbonnement.getAbonnee ().getId () != abonneeId) {
				return ErrorMessenger.generate (403, "Forbidden");
			}
			try {
				JSONParser parser = new JSONParser ();
				JSONObject obj = (JSONObject) parser.parse (body);
				if (!obj.get ("verdubbeling").equals ("verdubbeld")) {
					return ErrorMessenger.generate (400, "Bad Request");
				}
				abonneeAbonnement.setVerdubbeling ("verdubbeld");
				if (abonneeAbonnementMapper.update (abonneeAbonnement)) {
					return get (token, id);
				} else {
					return ErrorMessenger.generate (400, "Bad Request");
				}
			} catch (Exception e) {
				return ErrorMessenger.generate (400, "Bad Request");
			}
		}
		return null;
	}
}
