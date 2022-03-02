package de.mymiggi.voc.trainer.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;

public class DictionaryEntryManager extends BasicManager<DictionaryEntry>
{
	private static final Map<String, DictionaryEntry> idMap = new HashMap<String, DictionaryEntry>();

	public DictionaryEntryManager()
	{
		super(DictionaryEntry.class);
	}

	public DictionaryEntry getByID(String id)
	{
		return idMap.containsKey(id) ? idMap.get(id) : null;
	}

	public List<DictionaryEntry> getByUser(DiscordUser user)
	{
		List<DictionaryEntry> result = new ArrayList<DictionaryEntry>();
		for (DictionaryEntry temp : entrys)
		{
			if (temp.getUserID().equals(user.getId()))
			{
				result.add(temp);
			}
		}
		return result;
	}

	@Override
	protected void afterSyncList()
	{
		entrys.forEach(item -> idMap.put(item.getID(), item));
	}
}
