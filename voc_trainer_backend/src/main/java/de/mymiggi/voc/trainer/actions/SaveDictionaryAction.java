package de.mymiggi.voc.trainer.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.Dictionary;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.ShortMessageResponse;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;
import de.mymiggi.voc.trainer.entity.db.Words;

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
		List<Words> filtered = new ArrayList<Words>();
		DictionaryEntry dictionaryEntry = new DictionaryEntry(dictionary.getName());
		dictionaryEntry.setUser(discordUser);
		words.stream()
			.filter(word -> !word.getEng().isBlank() && !word.getGer().isBlank())
			.forEach(word -> {
				word.setDictionaryID(dictionaryEntry.getID());
				filtered.add(word);
			});
		if (filtered.isEmpty())
		{
			ShortMessageResponse message = new ShortMessageResponse("Your word objects needs \"eng\":STRING \"ger\":STRING");
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		}
		save(filtered, dictionaryEntry);
		return Response.ok().build();
	}

	private void save(List<Words> filtered, DictionaryEntry dictionaryEntry)
	{
		DictionaryResource.HIBERNATE_CLIENT.saveList(filtered);
		DictionaryResource.HIBERNATE_CLIENT.save(dictionaryEntry);
		DictionaryResource.DICTIONARY_MANAGER.syncList();
		DictionaryResource.WORDS_MANAGER.syncList();
	}
}
