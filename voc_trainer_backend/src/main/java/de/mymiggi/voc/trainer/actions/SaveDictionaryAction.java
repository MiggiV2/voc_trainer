package de.mymiggi.voc.trainer.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.DictionaryAdvanced;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.ShortMessageResponse;
import de.mymiggi.voc.trainer.entity.WordsAdvanced;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;
import de.mymiggi.voc.trainer.entity.db.Words;

public class SaveDictionaryAction
{
	public Response run(DictionaryAdvanced dictionary, DiscordUser discordUser)
	{
		if (dictionary == null || dictionary.getName() == null || dictionary.getWords() == null)
		{
			ShortMessageResponse message = new ShortMessageResponse("You need to set \"name\":STRING & \"words\":ARRAY");
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		}
		List<DictionaryEntry> usersDictionarys = DictionaryResource.DICTIONARY_MANAGER.getByUser(discordUser);
		if (usersDictionarys.size() > 50)
		{
			ShortMessageResponse message = new ShortMessageResponse("Your limit is reached! Max dictionarys is 50!");
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		}
		List<WordsAdvanced> words = Arrays.asList(dictionary.getWords());
		List<WordsAdvanced> filtered = new ArrayList<WordsAdvanced>();
		DictionaryEntry dictionaryEntry = new DictionaryEntry(dictionary.getName());
		dictionaryEntry.setUser(discordUser);
		words.stream()
			.filter(word -> !word.getEng().isBlank() && !word.getGer().isBlank())
			.forEach(word -> {
				WordsAdvanced toAdd = new WordsAdvanced()
					.setEng(word.getEng())
					.setGer(word.getGer())
					.setID(word.getID())
					.setOp(word.getOp());
				filtered.add(toAdd);
			});
		if (filtered.isEmpty())
		{
			ShortMessageResponse message = new ShortMessageResponse("Your word objects needs \"eng\":STRING \"ger\":STRING");
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		}
		save(filtered, dictionaryEntry);
		return Response.ok().build();
	}

	private void save(List<WordsAdvanced> filtered, DictionaryEntry dictionaryEntry)
	{
		List<Words> listToSave = new ArrayList<Words>();
		filtered.forEach(temp -> listToSave.add(new Words(temp, dictionaryEntry.getID())));
		DictionaryResource.WORDS_MANAGER.saveList(listToSave);
		DictionaryResource.DICTIONARY_MANAGER.save(dictionaryEntry);
	}
}
