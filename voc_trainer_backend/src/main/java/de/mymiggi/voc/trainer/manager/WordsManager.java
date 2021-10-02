package de.mymiggi.voc.trainer.manager;

import java.util.ArrayList;
import java.util.List;

import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;
import de.mymiggi.voc.trainer.entity.db.Words;

public class WordsManager extends BasicManager<Words>
{
	public WordsManager()
	{
		super(Words.class);
	}

	public Words getWordsByID(int id)
	{
		for (Words temp : getEntrys())
		{
			if (temp.getID() == id)
			{
				return temp;
			}
		}
		return null;
	}

	public List<Words> getWordsByDictionary(DictionaryEntry entry)
	{
		List<Words> dictionary = new ArrayList<Words>();
		for (Words temp : getEntrys())
		{
			if (temp.getDictionaryID() != null && entry.getID().equals(temp.getDictionaryID()))
			{
				dictionary.add(temp);
			}
		}
		return dictionary;
	}
}
