package vodagone.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Login {

	TokenService tokenService;

	public Login (TokenService tokenService) {
		this.tokenService = tokenService;
	}

	public Response login (String body) {
		try {
			JSONParser parser = new JSONParser ();
			JSONObject obj = (JSONObject) parser.parse (body);

			if (obj.get("user") == null || obj.get("password") == null) {
				return ErrorMessenger.generate (400, "Bad Request");
			}

			String response = tokenService.login ((String) obj.get ("user"), (String) obj.get ("password"));
			if (response == null) {
				return ErrorMessenger.generate (401, "Unauthorized");
			}

			return Response.ok (response, MediaType.TEXT_PLAIN).build ();
		} catch (Exception e) {
			e.printStackTrace ();
			return ErrorMessenger.generate (400, "Bad Request");
		}
	}

}
