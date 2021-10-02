package de.mymiggi.voc.trainer.auth.helper;

/**
 * Get Token from authBody
 */
public class GetTokenAction
{
	public String run(String[] params)
	{
		String token = "";
		for (String param : params)
		{
			if (param.startsWith("token"))
			{
				token = param.split("=")[1];
			}
		}
		return token;
	}
}
