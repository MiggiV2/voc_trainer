package de.mymiggi.voc.trainer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.mymiggi.voc.trainer.auth.IntrospectionAction;

@Path("/oauth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource
{
	public static List<String> admins = new ArrayList<String>();
	public static final String introspectionURL = "https://discord.com/api/oauth2/@me";

	public AuthResource()
	{
		admins.add("309696934174785556");
	}

	@POST
	@Path("introspection")
	@PermitAll
	public Response search(String body)
	{
		return new IntrospectionAction().run(body, introspectionURL);
	}
}
