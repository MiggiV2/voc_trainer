package de.mymiggi.voc.trainer.actions;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.Dictionary;
import de.mymiggi.voc.trainer.entity.DictionaryEntry;
import de.mymiggi.voc.trainer.entity.Words;

public class GetDictionaryByIDAction
{
	public Response run(String id)
	{
		List<DictionaryEntry> dictionaryEntries = DictionaryResource.HIBERNATE_CLIENT.getList(DictionaryEntry.class);
		List<Words> wordsEntries = DictionaryResource.HIBERNATE_CLIENT.getList(Words.class);

		DictionaryEntry dictionary = getDictionaryByID(id, dictionaryEntries);
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
