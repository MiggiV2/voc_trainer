package de.mymiggi.voc.trainer;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import de.mymiggi.voc.trainer.actions.GetDictionaryByIDAction;
import de.mymiggi.voc.trainer.actions.GetPreviewAction;
import de.mymiggi.voc.trainer.actions.SaveDictionaryAction;
import de.mymiggi.voc.trainer.actions.helper.BuildUserFromContext;
import de.mymiggi.voc.trainer.entity.Dictionary;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DictionaryResource
{
	public static final UniversalHibernateClient HIBERNATE_CLIENT = new UniversalHibernateClient();

	@POST
	@Path("search")
	public Response search()
	{
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@GET
	@Path("get/preview")
	public Response getpreview()
	{
		return new GetPreviewAction().run();
	}

	@GET
	@Path("get/dictionary/{id}")
	public Response getDictionary(@PathParam("id") String id)
	{
		return new GetDictionaryByIDAction().run(id);
	}

	@PUT
	@Path("save")
	@PermitAll
	public Response save(@Context SecurityContext ctx, Dictionary dictionary)
	{
		return new SaveDictionaryAction().run(dictionary, new BuildUserFromContext().run(ctx));
	}

	@GET
	@Path("admin")
	@RolesAllowed({ "309696934174785556" })
	@Produces(MediaType.TEXT_PLAIN)
	public Response admin(@Context SecurityContext ctx)
	{
		return Response.ok().build();
	}

	@GET
	@Path("test")
	@RolesAllowed({ "309696934174785556" })
	@Produces(MediaType.TEXT_PLAIN)
	public Response test(@Context SecurityContext ctx)
	{
		return Response.ok(ctx.getUserPrincipal().getName()).build();
	}
}
