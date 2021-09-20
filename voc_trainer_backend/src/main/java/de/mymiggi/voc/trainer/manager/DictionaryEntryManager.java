package de.mymiggi.voc.trainer.manager;

import java.util.ArrayList;
import java.util.List;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;

public class DictionaryEntryManager
{
	private List<DictionaryEntry> dictionarys = new ArrayList<DictionaryEntry>();

	public DictionaryEntryManager()
	{
		syncList();
		startUpdateThread();
	}

	public void syncList()
	{
		this.dictionarys = DictionaryResource.HIBERNATE_CLIENT.getList(DictionaryEntry.class);
	}

	public List<DictionaryEntry> getList()
	{
		return this.dictionarys;
	}

	public DictionaryEntry getByID(String id)
	{
		for (DictionaryEntry temp : dictionarys)
		{
			if (temp.getID().equals(id))
			{
				return temp;
			}
		}
		return null;
	}

	private void startUpdateThread()
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				boolean running = true;
				while (running)
				{
					try
					{
						Thread.sleep(30 * 60 * 1000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					syncList();
				}
			}
		};
		thread.start();
	}
}
