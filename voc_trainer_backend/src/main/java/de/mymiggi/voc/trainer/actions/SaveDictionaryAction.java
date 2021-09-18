package de.mymiggi.voc.trainer.actions;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.Dictionary;
import de.mymiggi.voc.trainer.entity.DictionaryEntry;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.ShortMessageResponse;
import de.mymiggi.voc.trainer.entity.Words;

public class SaveDictionaryAction
{
	public Response run(Dictionary dictionary, DiscordUser discordUser)
	{
		if (dictionary == null || dictionary.getName() == null || dictionary.getWords() == null)
		{
			ShortMessageResponse message = new ShortMessageResponse("You need to set \"name\":STRING & \"words\":ARRAY");
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		}
		List<Words> words = Arrays.asList(dictionary.getWords());
		DictionaryEntry dictionaryEntry = new DictionaryEntry(dictionary.getName());
		dictionaryEntry.setUser(discordUser);
		words.forEach(word -> word.setDictionaryID(dictionaryEntry.getID()));
		DictionaryResource.HIBERNATE_CLIENT.saveList(words);
		DictionaryResource.HIBERNATE_CLIENT.save(dictionaryEntry);
		return Response.ok().build();
	}
}
