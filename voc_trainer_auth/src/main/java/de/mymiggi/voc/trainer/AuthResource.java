package de.mymiggi.voc.trainer;

import javax.annotation.security.PermitAll;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.mymiggi.voc.trainer.auth.SendIntrospectionAction;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource
{
	@POST
	@Path("introspection/{url}")
	@PermitAll
	public Response search(@HeaderParam("Authorization") String token, @PathParam("url") String url, String body)
	{
		return new SendIntrospectionAction().run(body, url);
	}
}
