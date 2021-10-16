package de.mymiggi.voc.trainer.actions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.Dictionary;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.ShortMessageResponse;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;

public class DeleteDictionaryAction
{
	public Response run(Dictionary dictionary, DiscordUser user)
	{
		if (dictionary == null || dictionary.getId() == null)
		{
			ShortMessageResponse messageResponse = new ShortMessageResponse("You need \"id\":STRING in your body!");
			return Response.status(Status.BAD_REQUEST).entity(messageResponse).build();
		}
		DictionaryEntry dictionaryFromDB = DictionaryResource.DICTIONARY_MANAGER.getByID(dictionary.getId());
		if (dictionaryFromDB == null)
		{
			ShortMessageResponse messageResponse = new ShortMessageResponse("Can't find dictionary!");
			return Response.status(Status.NOT_FOUND).entity(messageResponse).build();
		}
		if (!dictionaryFromDB.getUserID().equals(user.getId()))
		{
			return Response.status(Status.FORBIDDEN).build();
		}
		ShortMessageResponse messageResponse = new ShortMessageResponse("Failed to delete dictionary!");
		boolean succes = DictionaryResource.DICTIONARY_MANAGER.delete(dictionaryFromDB);
		return (succes)
			? Response.ok().build()
			: Response.status(Status.CONFLICT).entity(messageResponse).build();
	}
}
