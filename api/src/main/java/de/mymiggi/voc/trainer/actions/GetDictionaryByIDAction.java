package de.mymiggi.voc.trainer.actions;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.DictionaryAdvanced;
import de.mymiggi.voc.trainer.entity.DictionarySimple;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.ShortMessageResponse;
import de.mymiggi.voc.trainer.entity.WordsAdvanced;
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
		List<WordsAdvanced> wordsResponses = new ArrayList<WordsAdvanced>();
		words.forEach(item -> createWordResponse(user.getId(), wordsResponses, item));
		DictionarySimple response = (user.isAdmin() || dictionary.getUserID().equals(user.getId()))
			? getAdvancedDictionary(dictionary, wordsResponses)
			: getSimpleDictionary(dictionary, wordsResponses);
		return Response.ok(response).build();
	}

	private DictionarySimple getSimpleDictionary(DictionaryEntry dictionary, List<WordsAdvanced> wordsResponses)
	{
		return new DictionarySimple()
			.setDictionaryEntry(dictionary)
			.setAdvancedWords(wordsResponses.stream().toArray(WordsAdvanced[]::new));
	}

	private DictionaryAdvanced getAdvancedDictionary(DictionaryEntry dictionary, List<WordsAdvanced> wordsResponses)
	{
		return new DictionaryAdvanced()
			.setDictionaryEntry(dictionary)
			.setWords(wordsResponses.stream().toArray(WordsAdvanced[]::new));
	}

	private void createWordResponse(String id, List<WordsAdvanced> wordsResponses, Words item)
	{
		WordsAdvanced toAdd = new WordsAdvanced(item);
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
				temp.getEng() != null &&
				!temp.getEng().isBlank() &&
				temp.getGer() != null &&
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
