package de.mymiggi.voc.trainer.manager;

import java.util.ArrayList;
import java.util.List;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;
import de.mymiggi.voc.trainer.entity.db.Words;

public class WordsManager
{
	private List<Words> dictionarys = new ArrayList<Words>();

	public WordsManager()
	{
		syncList();
		startUpdateThread();
	}

	public void syncList()
	{
		this.dictionarys = DictionaryResource.HIBERNATE_CLIENT.getList(Words.class);
	}

	public List<Words> getList()
	{
		return this.dictionarys;
	}

	public List<Words> getWordsByDictionary(DictionaryEntry entry)
	{
		List<Words> dictionary = new ArrayList<Words>();
		for (Words temp : getList())
		{
			if (entry.getID().equals(temp.getDictionaryID()))
			{
				dictionary.add(temp);
			}
		}
		return dictionary;
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
