package de.mymiggi.voc.trainer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DictionaryResource
{
	@POST
	@Path("search")
	public Response search()
	{
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@GET
	@Path("get")
	public Response get()
	{
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@POST
	@Path("save")
	public Response save()
	{
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}
}
