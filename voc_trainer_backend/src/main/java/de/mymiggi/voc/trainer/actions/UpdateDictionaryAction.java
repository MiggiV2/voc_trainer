package de.mymiggi.voc.trainer.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.ShortMessageResponse;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;
import de.mymiggi.voc.trainer.entity.db.Words;

public class UpdateDictionaryAction
{
	public Response run(String id, DiscordUser user, List<Words> newWords)
	{
		DictionaryEntry entry = DictionaryResource.DICTIONARY_MANAGER.getByID(id);
		if (entry == null)
		{
			ShortMessageResponse message = new ShortMessageResponse("Nothing found for id " + id);
			return Response.status(Status.NOT_FOUND).entity(message).build();
		}
		if (!entry.getUserID().equals(user.getId()) &&  !user.isAdmin())
		{
			ShortMessageResponse message = new ShortMessageResponse("You are not the owner!");
			return Response.status(Status.FORBIDDEN).entity(message).build();
		}
		List<Words> listToSave = new ArrayList<Words>();
		List<Words> dictionary = DictionaryResource.WORDS_MANAGER.getWordsByDictionary(entry);
		Map<Integer, Words> idMap = new HashMap<Integer, Words>();
		for (Words temp : dictionary)
		{
			idMap.put(temp.getID(), temp);
		}
		for (Words temp : newWords)
		{
			if (temp != null)
			{
				listToSave.add(
					idMap.containsKey(temp.getID())
						? update(idMap.get(temp.getID()), temp)
						: temp);

			}
		}
		boolean failed = !DictionaryResource.WORDS_MANAGER.saveList(listToSave);
		ShortMessageResponse messsage = new ShortMessageResponse("Failed to update!");
		return failed ? Response.status(Status.CONFLICT).entity(messsage).build() : Response.ok().build();
	}

	private Words update(Words oldWord, Words newWord)
	{
		return oldWord
			.setDictionaryID(newWord.getDictionaryID())
			.setEng(newWord.getEng())
			.setGer(newWord.getGer())
			.setOp(newWord.getOp());
	}
}
