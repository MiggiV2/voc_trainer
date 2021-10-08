package de.mymiggi.voc.trainer.actions;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.Dictionary;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.ShortMessageResponse;
import de.mymiggi.voc.trainer.entity.WordsResponse;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;
import de.mymiggi.voc.trainer.entity.db.Words;

public class GetDictionaryByIDAction
{
	public Response run(String id, DiscordUser user)
	{
		List<DictionaryEntry> dictionaryEntries = DictionaryResource.DICTIONARY_MANAGER.getEntrys();
		List<Words> wordsEntries = DictionaryResource.WORDS_MANAGER.getEntrys();
		DictionaryEntry dictionary = getDictionaryByID(id, dictionaryEntries);
		if (dictionary == null)
		{
			ShortMessageResponse messageResponse = new ShortMessageResponse("Can't finde dictionary");
			return Response.status(Status.BAD_REQUEST).entity(messageResponse).build();
		}
		List<Words> words = getWordsByDictionar(dictionary, wordsEntries);
		List<WordsResponse> wordsResponses = new ArrayList<WordsResponse>();
		words.forEach(item -> createWordResponse(user.getId(), wordsResponses, item));
		Dictionary response = new Dictionary()
			.setDictionaryEntry(dictionary)
			.setWords(wordsResponses.stream().toArray(WordsResponse[]::new));
		return Response.ok(response).build();
	}

	private void createWordResponse(String id, List<WordsResponse> wordsResponses, Words item)
	{
		WordsResponse toAdd = new WordsResponse(item);
		boolean isSpecialWord = DictionaryResource.SPECIAL_WORD_MANAGER.isSpecialWord(id, item);
		toAdd.setSpecialWord(isSpecialWord);
		wordsResponses.add(toAdd);
	}

	private List<Words> getWordsByDictionar(DictionaryEntry dictionary, List<Words> wordsEntries)
	{
		List<Words> words = new ArrayList<Words>();
		for (Words temp : wordsEntries)
		{
			if (temp.getDictionaryID() != null &&
				temp.getDictionaryID().equals(dictionary.getID()) &&
				!temp.getEng().isBlank() &&
				!temp.getGer().isBlank())
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
