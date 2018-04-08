package vodagone.service;

import org.json.simple.JSONObject;

import javax.ws.rs.core.Response;

public class ErrorMessenger {

	public static Response generate (int statusCode, String message) {
		JSONObject response = new JSONObject ();
		response.put ("status", statusCode);
		response.put ("message", message);
		return Response.status (statusCode).entity (response.toJSONString ()).build ();
	}

}
