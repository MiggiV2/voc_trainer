package de.mymiggi.voc.trainer.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.db.SpecialWord;

public class SpecialWordManager extends BasicManager<SpecialWord>
{
	/**
	 * Used for faster getByID()
	 */
	private Map<Integer, SpecialWord> idMap;

	public SpecialWordManager()
	{
		super(SpecialWord.class);
	}

	public boolean userSavedWord(DiscordUser user, int wordID)
	{
		for (SpecialWord temp : this.entrys)
		{
			if (temp.getWordID() == wordID)
			{
				return true;
			}
		}
		return false;
	}

	public SpecialWord getByID(int specialWordID)
	{
		return idMap.containsKey(specialWordID) ? idMap.get(specialWordID) : null;
	}

	public List<SpecialWord> getByUser(DiscordUser user)
	{
		return getByUserID(user.getId());
	}

	public List<SpecialWord> getByUserID(String userID)
	{
		List<SpecialWord> result = new ArrayList<SpecialWord>();
		for (SpecialWord temp : this.entrys)
		{
			if (temp.getUserID().equals(userID))
			{
				result.add(temp);
			}
		}
		return result;
	}

	@Override
	protected void afterSyncList()
	{
		idMap = new HashMap<Integer, SpecialWord>();
		this.entrys.forEach(item -> this.idMap.put(item.getID(), item));
	}
}
