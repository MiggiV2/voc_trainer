package de.mymiggi.voc.trainer.actions.helper;

import java.util.ArrayList;
import java.util.List;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;
import de.mymiggi.voc.trainer.entity.db.Words;

public class CleanDataBaseAction
{
	public void run()
	{
		List<DictionaryEntry> dictionaryEntries = DictionaryResource.DICTIONARY_MANAGER.getEntrys();
		List<Words> wordsEntries = DictionaryResource.WORDS_MANAGER.getEntrys();
		List<Words> toDelete = new ArrayList<Words>();
		wordsEntries.stream()
			.filter(word -> !dictionaryExists(word, dictionaryEntries)
				|| word.getEng() == null || word.getEng().isBlank()
				|| word.getGer() == null || word.getGer().isBlank())
			.forEach(word -> toDelete.add(word));
		if (!toDelete.isEmpty())
		{
			DictionaryResource.HIBERNATE_CLIENT.deleteList(toDelete);
		}
	}

	private boolean dictionaryExists(Words word, List<DictionaryEntry> dictionaryEntries)
	{
		if (word.getDictionaryID() == null)
		{
			return false;
		}
		for (DictionaryEntry temp : dictionaryEntries)
		{
			if (temp.getID().equals(word.getDictionaryID()))
			{
				return true;
			}
		}
		return false;
	}
}
