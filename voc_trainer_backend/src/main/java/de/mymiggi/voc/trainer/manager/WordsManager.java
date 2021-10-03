package de.mymiggi.voc.trainer.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;
import de.mymiggi.voc.trainer.entity.db.Words;

public class WordsManager extends BasicManager<Words>
{
	private static final Map<Integer, Words> idMap = new HashMap<Integer, Words>();
	private static final Map<String, List<Words>> dictionaryMap = new HashMap<String, List<Words>>();

	public WordsManager()
	{
		super(Words.class);
	}

	public Words getWordsByID(int id)
	{
		return idMap.containsKey(id) ? idMap.get(id) : null;
	}

	public List<Words> getWordsByDictionary(DictionaryEntry entry)
	{
		return dictionaryMap.containsKey(entry.getID()) ? dictionaryMap.get(entry.getID()) : new ArrayList<Words>();
	}

	@Override
	protected void afterSyncList()
	{
		entrys.forEach(item -> {
			if (!dictionaryMap.containsKey(item.getDictionaryID()))
			{
				dictionaryMap.put(item.getDictionaryID(), new ArrayList<Words>());
			}
			dictionaryMap.get(item.getDictionaryID()).add(item);
			idMap.put(item.getID(), item);
		});
	}
}
