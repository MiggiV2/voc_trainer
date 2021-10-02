package de.mymiggi.voc.trainer.auth;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import de.mymiggi.voc.trainer.auth.helper.GetTokenAction;
import de.mymiggi.voc.trainer.entity.response.AuthResponse;

public class IntrospectionAction
{
	private static final Map<String, AuthResponse> CACHE = new HashMap<String, AuthResponse>();
	private static final Logger LOGGER = Logger.getLogger(IntrospectionAction.class);

	public IntrospectionAction()
	{
		startClearThread();
	}

	public Response run(String body, String url)
	{
		String token = new GetTokenAction().run(body.split("&"));
		return CACHE.containsKey(token) ? sendCachedResponse(token) : sendRequest(url, token);
	}

	private Response sendCachedResponse(String token)
	{
		AuthResponse authResponse = CACHE.get(token);
		LOGGER.info("Send cached Request for token:" + token);
		return Response.status(authResponse.getCode()).entity(authResponse).build();
	}

	private Response sendRequest(String url, String token)
	{
		AuthResponse authResponse = new SendIntrospectionAction().run(token, url);
		CACHE.put(token, authResponse);
		return Response.status(authResponse.getCode()).entity(authResponse).build();
	}

	private void startClearThread()
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				boolean running = true;
				while (running)
				{
					try
					{
						Thread.sleep(1 * 60 * 1000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					CACHE.clear();
					LOGGER.info("Cleared cache!");
				}
			}
		};
		thread.start();
	}
}
