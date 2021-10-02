package de.mymiggi.voc.trainer.auth;

import java.io.IOException;

import org.jboss.logging.Logger;

import com.google.gson.Gson;

import de.mymiggi.voc.trainer.AuthResource;
import de.mymiggi.voc.trainer.entity.response.AuthResponse;
import de.mymiggi.voc.trainer.entity.response.DiscordResponse;
import de.mymiggi.voc.trainer.entity.response.UnauthorizedResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SendIntrospectionAction
{
	private static final Logger logger = Logger.getLogger(SendIntrospectionAction.class);

	public AuthResponse run(String token, String url)
	{
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
			.url(url)
			.addHeader("Authorization", "Bearer " + token)
			.build();
		try
		{
			okhttp3.Response response = client.newCall(request).execute();
			logger.info("Status:" + response.code() + " URL:" + url);
			return handleResponse(response);
		}
		catch (IOException e)
		{
			logger.error("Failed to send request!", e);
			return null;
		}
	}

	private AuthResponse handleResponse(okhttp3.Response response) throws IOException
	{
		if (response.code() == 401)
		{
			response.close();
			return new UnauthorizedResponse();
		}
		else
		{
			int code = response.code();
			String body = response.body().string();
			response.close();
			DiscordResponse discordResponse = new Gson().fromJson(body, DiscordResponse.class);
			String role = (AuthResource.admins.contains(discordResponse.getID())) ? "admin" : "user";
			logger.info("Role:" + role);
			discordResponse.setRole(role).setCode(code);
			return discordResponse;
		}
	}
}
