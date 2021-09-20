package de.mymiggi.voc.trainer.actions;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.Dictionary;
import de.mymiggi.voc.trainer.entity.ShortMessageResponse;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;
import de.mymiggi.voc.trainer.entity.db.Words;

public class GetDictionaryByIDAction
{
	public Response run(String id)
	{
		List<DictionaryEntry> dictionaryEntries = DictionaryResource.DICTIONARY_MANAGER.getList();
		List<Words> wordsEntries = DictionaryResource.WORDS_MANAGER.getList();

		DictionaryEntry dictionary = getDictionaryByID(id, dictionaryEntries);

		if (dictionary == null)
		{
			ShortMessageResponse messageResponse = new ShortMessageResponse("Can't finde dictionary");
			return Response.status(Status.BAD_REQUEST).entity(messageResponse).build();
		}

		List<Words> words = getWordsByDictionar(dictionary, wordsEntries);

		Dictionary response = new Dictionary()
			.setDictionaryEntry(dictionary)
			.setWords(words.stream().toArray(Words[]::new));

		return Response.ok(response).build();
	}

	private List<Words> getWordsByDictionar(DictionaryEntry dictionary, List<Words> wordsEntries)
	{
		List<Words> words = new ArrayList<Words>();
		for (Words temp : wordsEntries)
		{
			if (temp.getDictionaryID().equals(dictionary.getID()))
			{
				words.add(temp);
			}
		}
		return words;
	}

	private DictionaryEntry getDictionaryByID(String id, List<DictionaryEntry> dictionaryEntries)
	{
		for (DictionaryEntry temp : dictionaryEntries)
		{
			if (temp.getID().equals(id))
			{
				return temp;
			}
		}
		return null;
	}
}
