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
	private static boolean runningClearThread;

	public IntrospectionAction()
	{
		if (!runningClearThread)
		{
			startClearThread();
		}
	}

	public Response run(String body, String url)
	{
		String token = new GetTokenAction().run(body.split("&"));
		return CACHE.containsKey(token) ? sendCachedResponse(token) : sendRequest(url, token);
	}

	private Response sendCachedResponse(String token)
	{
		AuthResponse authResponse = CACHE.get(token);
		LOGGER.info("Send cached Request");
		return Response.status(200).entity(authResponse).build();
	}

	private Response sendRequest(String url, String token)
	{
		AuthResponse authResponse = new SendIntrospectionAction().run(token, url);
		CACHE.put(token, authResponse);
		return Response.status(200).entity(authResponse).build();
	}

	private void startClearThread()
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				runningClearThread = true;
				while (runningClearThread)
				{
					try
					{
						Thread.sleep(5 * 60 * 1000);
					}
					catch (InterruptedException e)
					{
						LOGGER.error("Stopped Thread!", e);
						runningClearThread = false;
					}
					CACHE.clear();
					LOGGER.info("Cleared cache!");
				}
			}
		};
		thread.start();
	}
}
