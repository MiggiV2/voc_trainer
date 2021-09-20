package de.mymiggi.voc.trainer.actions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.Dictionary;
import de.mymiggi.voc.trainer.entity.ShortMessageResponse;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;

public class DeleteDictionaryAction
{
	public Response run(Dictionary dictionary)
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
		ShortMessageResponse messageResponse = new ShortMessageResponse("Failed to delete dictionary!");
		boolean succes = DictionaryResource.HIBERNATE_CLIENT.delete(dictionaryFromDB);
		DictionaryResource.DICTIONARY_MANAGER.syncList();
		return (succes)
			? Response.ok().build()
			: Response.status(Status.CONFLICT).entity(messageResponse).build();
	}
}
