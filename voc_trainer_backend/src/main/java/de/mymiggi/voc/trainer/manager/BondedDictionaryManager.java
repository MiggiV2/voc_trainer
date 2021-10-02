package de.mymiggi.voc.trainer.manager;

import java.util.HashMap;
import java.util.Map;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.db.BondedDictionary;

public class BondedDictionaryManager extends BasicManager<BondedDictionary>
{
	private Map<String, BondedDictionary> userMap = new HashMap<String, BondedDictionary>();

	public BondedDictionaryManager()
	{
		super(BondedDictionary.class);
	}

	public boolean bind(String dictionaryID, DiscordUser user)
	{
		BondedDictionary oldEntity = getByUser(user);
		boolean isSaved;
		if (oldEntity == null)
		{
			oldEntity = new BondedDictionary(user.getId(), dictionaryID);
			isSaved = DictionaryResource.HIBERNATE_CLIENT.save(oldEntity);
			if (isSaved)
			{
				this.entrys.add(oldEntity);
			}
		}
		else
		{
			oldEntity.setDictionaryID(dictionaryID);
			isSaved = DictionaryResource.HIBERNATE_CLIENT.update(oldEntity);
		}
		if (isSaved)
		{
			this.userMap.put(user.getId(), oldEntity);
		}
		return isSaved;
	}

	public BondedDictionary getByUser(DiscordUser user)
	{
		return getByUserID(user.getId());
	}

	public BondedDictionary getByUserID(String userID)
	{
		return userMap.containsKey(userID) ? userMap.get(userID) : null;
	}

	@Override
	protected void afterSyncList()
	{
		userMap = new HashMap<String, BondedDictionary>();
		entrys.forEach(item -> userMap.put(item.getUserID(), item));
	}
}
