package de.mymiggi.voc.trainer.actions.helper;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import de.mymiggi.voc.trainer.entity.DiscordUser;

public class BuildUserFromContext
{
	public DiscordUser run(SecurityContext ctx)
	{
		String userStr = ctx.getUserPrincipal().getName();
		String[] parts = userStr.split(";");
		Map<String, String> userMap = new HashMap<String, String>();
		for (String temp : parts)
		{
			String[] keyValue = temp.split("=");
			userMap.put(keyValue[0], keyValue[1]);
		}
		return new DiscordUser()
			.setAvater(userMap.get("avater"))
			.setName(userMap.get("user"))
			.setId(userMap.get("id"));
	}
}
