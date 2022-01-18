package de.mymiggi.voc.trainer;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import io.vertx.core.http.HttpServerRequest;

@Provider
public class NotFoundManager implements ExceptionMapper<NotFoundException>
{
	private static final Logger LOGGER = Logger.getLogger(NotFoundManager.class.getName());

	@Context
	UriInfo info;

	@Context
	HttpServerRequest request;

	@Override
	public Response toResponse(NotFoundException exception)
	{
		LOGGER.warn(String.format("Request 404 %s from IP %s", info.getPath(), request.remoteAddress()));
		return Response.status(Status.NOT_FOUND).build();
	}
}
