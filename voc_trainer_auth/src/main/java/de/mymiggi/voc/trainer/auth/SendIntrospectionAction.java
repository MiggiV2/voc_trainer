package de.mymiggi.voc.trainer.auth;

import java.io.IOException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import com.google.gson.Gson;

import de.mymiggi.voc.trainer.entity.DiscordResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SendIntrospectionAction
{
	private static final Logger logger = Logger.getLogger(SendIntrospectionAction.class);

	public Response run(String body, String url)
	{
		String[] params = body.split("&");
		String token = "";
		for (String param : params)
		{
			if (param.startsWith("token"))
			{
				token = param.split("=")[1];
			}
		}
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
			.url(url)
			.addHeader("Authorization", "Bearer " + token)
			.build();
		try
		{
			okhttp3.Response response = client.newCall(request).execute();
			return handleResponse(url, response);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getCause()).build();
		}
	}

	private Response handleResponse(String url, okhttp3.Response response) throws IOException
	{
		logger.info("Status:" + response.code() + " URL:" + url);
		if (response.code() == 401)
		{
			response.close();
			return Response.ok("{\"active\":false}").build();
		}
		else
		{
			int code = response.code();
			DiscordResponse discordResponse = new Gson().fromJson(response.body().string(), DiscordResponse.class);
			response.close();
			return Response.status(code).entity(discordResponse).build();
		}
	}
}
