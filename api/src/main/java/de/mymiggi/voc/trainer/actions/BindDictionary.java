package de.mymiggi.voc.trainer.actions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.ShortMessageResponse;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;

public class BindDictionary
{
	public Response run(String id, DiscordUser user)
	{
		if (id == null)
		{
			ShortMessageResponse message = new ShortMessageResponse("You need the ?id=dictionaryID parameter!");
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		}
		DictionaryEntry dictionaryEntry = DictionaryResource.DICTIONARY_MANAGER.getByID(id);
		if (dictionaryEntry == null)
		{
			ShortMessageResponse message = new ShortMessageResponse("Can't find your dictionary!");
			return Response.status(Status.NOT_FOUND).entity(message).build();
		}
		boolean saved = DictionaryResource.BONDED_DICTIONARY_MANAGER.bind(id, user);
		ShortMessageResponse message = new ShortMessageResponse("Failed to save!");
		return saved ? Response.ok().build() : Response.status(Status.CONFLICT).entity(message).build();
	}
}
