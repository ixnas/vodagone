package vodagone;

import vodagone.data.*;
import vodagone.domain.AbonneeAbonnement;
import vodagone.domain.TokenService;
import vodagone.service.Abonnees;
import vodagone.service.Abonnementen;
import vodagone.service.Login;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path ("vodagone")
@Singleton
public class Main {

	private DataAccess dataAccess;
	private AbonneeMapper abonneeMapper;
	private AbonnementMapper abonnementMapper;
	private AbonneeAbonnementMapper abonneeAbonnementMapper;
	private Login login;
	private Abonnees abonnees;
	private Abonnementen abonnementen;
	private TokenService tokenService;

	private DataAccess getDataAccess () {
		if (dataAccess == null) {
			dataAccess = new DataAccess ();
		}
		return dataAccess;
	}

	private AbonnementMapper getAbonnementMapper () {
		if (abonnementMapper == null) {
			abonnementMapper = new AbonnementMapper (getDataAccess (), new IdentityMapper ());
		}
		return abonnementMapper;
	}

	private AbonneeMapper getAbonneeMapper () {
		if (abonneeMapper == null) {
			abonneeMapper = new AbonneeMapper (getDataAccess (), new IdentityMapper ());
		}
		return abonneeMapper;
	}

	private AbonneeAbonnementMapper getAbonneeAbonnementMapper () {
		if (abonneeAbonnementMapper == null) {
			abonneeAbonnementMapper = new AbonneeAbonnementMapper (getDataAccess (), getAbonneeMapper (), getAbonnementMapper (), new IdentityMapper ());
		}
		return abonneeAbonnementMapper;
	}

	private Login getLogin () {
		if (login == null) {
			login = new Login (getTokenService ());
		}
		return login;
	}

	private Abonnees getAbonnees () {
		if (abonnees == null) {
			abonnees = new Abonnees (getAbonneeMapper (), getTokenService ());
		}
		return abonnees;
	}

	private Abonnementen getAbonnementen () {
		if (abonnementen == null) {
			abonnementen = new Abonnementen (getAbonnementMapper (), getAbonneeAbonnementMapper (), getTokenService ());
		}
		return abonnementen;
	}

	private TokenService getTokenService () {
		if (tokenService == null) {
			tokenService = new TokenService (getAbonneeMapper ());
		}
		return tokenService;
	}

	@Path ("abonnementen/all")
	@GET
	@Produces (MediaType.APPLICATION_JSON)
	public Response getAbonnementenAll (@QueryParam ("token") String token, @QueryParam ("filter") String filter) {
		return getAbonnementen ().getAll (token, filter);
	}

	@Path ("abonnementen")
	@GET
	@Produces (MediaType.APPLICATION_JSON)
	public Response getAbonnementenAllFromUser (@QueryParam ("token") String token) {
		return getAbonnementen ().getAllFromUser (token);
	}

	@Path ("abonnementen")
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public Response postAbonnementenToUser (String body, @QueryParam ("token") String token) {
		return getAbonnementen ().newAbonnement (body, token);
	}

	@Path ("abonnementen/{id}")
	@GET
	@Produces (MediaType.APPLICATION_JSON)
	public Response getAbonnementById (@QueryParam ("token") String token, @PathParam ("id") int id) {
		return getAbonnementen ().get (token, id);
	}

	@Path ("abonnementen/{id}")
	@DELETE
	@Produces (MediaType.APPLICATION_JSON)
	public Response terminateAbonnementById (@QueryParam ("token") String token, @PathParam ("id") int id) {
		return getAbonnementen ().terminate (token, id);
	}

	@Path ("abonnees")
	@GET
	@Produces (MediaType.APPLICATION_JSON)
	public Response getAbonneesAll (@QueryParam ("token") String token) {
		return getAbonnees ().getAll (token);
	}

	@Path ("abonnees/{id}")
	@GET
	@Produces (MediaType.APPLICATION_JSON)
	public Response getAbonneeById (@PathParam ("id") int id) {
		return getAbonnees ().get (id);
	}

	@Path ("login")
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public Response postLogin (String body) {
		return getLogin ().login (body);
	}

}
