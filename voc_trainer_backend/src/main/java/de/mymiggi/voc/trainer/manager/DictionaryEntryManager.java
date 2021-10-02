package de.mymiggi.voc.trainer.manager;

import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;

public class DictionaryEntryManager extends BasicManager<DictionaryEntry>
{

	public DictionaryEntryManager()
	{
		super(DictionaryEntry.class);
	}

	public DictionaryEntry getByID(String id)
	{
		for (DictionaryEntry temp : getEntrys())
		{
			if (temp.getID().equals(id))
			{
				return temp;
			}
		}
		return null;
	}
}
