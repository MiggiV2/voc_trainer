package de.mymiggi.voc.trainer.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.db.SpecialWord;
import de.mymiggi.voc.trainer.entity.db.Words;

public class SpecialWordManager extends BasicManager<SpecialWord>
{
	private static final Map<Integer, SpecialWord> idMap = new HashMap<Integer, SpecialWord>();
	private static final Map<String, List<Integer>> userWordIDMap = new HashMap<String, List<Integer>>();

	public SpecialWordManager()
	{
		super(SpecialWord.class);
	}

	public SpecialWord getUserSavedWord(DiscordUser user, int wordID)
	{
		for (SpecialWord temp : this.entrys)
		{
			if (temp.getWordID() == wordID && temp.getUserID().equals(user.getId()))
			{
				return temp;
			}
		}
		return null;
	}

	public boolean isSpecialWord(DiscordUser user, Words words)
	{
		return isSpecialWord(user.getId(), words);
	}

	public boolean isSpecialWord(String userID, Words words)
	{
		return userWordIDMap.containsKey(userID) ? userWordIDMap.get(userID).contains(words.getID()) : false;
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
		for (SpecialWord temp : entrys)
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
		idMap.clear();
		userWordIDMap.clear();
		entrys.forEach(item -> {
			idMap.put(item.getID(), item);
			if (userWordIDMap.containsKey(item.getUserID()))
			{
				userWordIDMap.get(item.getUserID()).add(item.getWordID());
			}
			else
			{
				List<Integer> list = new ArrayList<Integer>();
				list.add(item.getWordID());
				userWordIDMap.put(item.getUserID(), list);
			}
		});
	}
}
