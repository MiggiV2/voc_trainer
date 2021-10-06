package de.mymiggi.voc.trainer.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.Dictionary;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;
import de.mymiggi.voc.trainer.entity.db.SpecialWord;
import de.mymiggi.voc.trainer.entity.db.Words;

public class GetSpecialWordAction
{
	public Response run(DiscordUser user)
	{
		List<SpecialWord> ids = DictionaryResource.SPECIAL_WORD_MANAGER.getByUser(user);
		List<Dictionary> dictionarys = new ArrayList<Dictionary>();
		Map<DictionaryEntry, List<Words>> dictionaryMap = new HashMap<DictionaryEntry, List<Words>>();
		ids.forEach(item -> addItem(dictionaryMap, item));
		dictionaryMap.forEach((dictionaryEntry, wordList) -> {
			dictionarys.add(new Dictionary().setDictionaryEntry(dictionaryEntry).setWordList(wordList, true));
		});
		return Response.ok(dictionarys).build();
	}

	private void addItem(Map<DictionaryEntry, List<Words>> result, SpecialWord item)
	{
		Words words = DictionaryResource.WORDS_MANAGER.getWordsByID(item.getWordID());
		DictionaryEntry dictionary = (words != null)
			? dictionary = DictionaryResource.DICTIONARY_MANAGER.getByID(words.getDictionaryID())
			: null;
		if (dictionary != null)
		{
			if (!result.containsKey(dictionary))
			{
				result.put(dictionary, new ArrayList<Words>());
			}
			result.get(dictionary).add(words);
		}
	}
}
