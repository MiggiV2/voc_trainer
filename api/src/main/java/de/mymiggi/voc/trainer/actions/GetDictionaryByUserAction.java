package de.mymiggi.voc.trainer.actions;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.ShortMessageResponse;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;

public class GetDictionaryByUserAction
{
	public Response run(DiscordUser user)
	{
		if (user.getId() == null || user.getId().length() < 18)
		{
			ShortMessageResponse message = new ShortMessageResponse("You need an id parameter in your querry! Min. length: 18!");
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		}
		List<DictionaryEntry> result = DictionaryResource.DICTIONARY_MANAGER.getByUser(user);
		return result.isEmpty()
			? Response.status(Status.NO_CONTENT).entity(result).build()
			: Response.ok(result).build();
	}
}
