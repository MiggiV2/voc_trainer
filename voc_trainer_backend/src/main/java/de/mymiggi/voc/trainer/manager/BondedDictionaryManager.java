package de.mymiggi.voc.trainer.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.actions.helper.CleanDataBaseAction;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.db.BondedDictionary;

public class BondedDictionaryManager
{
	private List<BondedDictionary> bondedDictionaries = new ArrayList<BondedDictionary>();
	private Map<String, BondedDictionary> userMap = new HashMap<String, BondedDictionary>();

	public BondedDictionaryManager()
	{
		syncList();
		startUpdateThread();
	}

	public void syncList()
	{
		this.bondedDictionaries = DictionaryResource.HIBERNATE_CLIENT.getList(BondedDictionary.class);
		bondedDictionaries.forEach(item -> userMap.put(item.getUserID(), item));
	}

	public boolean bind(String dictionaryID, DiscordUser user)
	{
		BondedDictionary toSave = getByUser(user);
		boolean saved;
		if (toSave == null)
		{
			toSave = new BondedDictionary(user.getId(), dictionaryID);
			saved = DictionaryResource.HIBERNATE_CLIENT.save(toSave);
			if (saved)
			{
				this.bondedDictionaries.add(toSave);
			}
		}
		else
		{
			toSave.setDictionaryID(dictionaryID);
			saved = DictionaryResource.HIBERNATE_CLIENT.update(toSave);
		}
		if (saved)
		{
			this.userMap.put(user.getId(), toSave);
		}
		return saved;
	}

	public BondedDictionary getByUser(DiscordUser user)
	{
		return getByUserID(user.getId());
	}

	public BondedDictionary getByUserID(String userID)
	{
		if (!userMap.containsKey(userID))
		{
			return null;
		}
		return userMap.get(userID);
	}

	public List<BondedDictionary> getBondedDictionaries()
	{
		return bondedDictionaries;
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
					new CleanDataBaseAction().run();
					syncList();
				}
			}
		};
		thread.start();
	}
}
