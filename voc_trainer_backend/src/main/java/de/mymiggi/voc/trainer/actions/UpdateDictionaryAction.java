package de.mymiggi.voc.trainer.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.actions.helper.CleanDataBaseAction;
import de.mymiggi.voc.trainer.entity.DictionaryAdvanced;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.ShortMessageResponse;
import de.mymiggi.voc.trainer.entity.WordsAdvanced;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;
import de.mymiggi.voc.trainer.entity.db.Words;

public class UpdateDictionaryAction
{
	private static final Logger LOGGER = Logger.getLogger(UpdateDictionaryAction.class.getName());

	public Response run(String id, DiscordUser user, DictionaryAdvanced updatedDictionary)
	{
		DictionaryEntry entry = DictionaryResource.DICTIONARY_MANAGER.getByID(id);
		if (entry == null)
		{
			ShortMessageResponse message = new ShortMessageResponse("Nothing found for id " + id);
			return Response.status(Status.NOT_FOUND).entity(message).build();
		}
		if (!entry.getUserID().equals(user.getId()) && !user.isAdmin())
		{
			ShortMessageResponse message = new ShortMessageResponse("You are not the owner!");
			return Response.status(Status.FORBIDDEN).entity(message).build();
		}
		if (!entry.getName().equals(updatedDictionary.getName()))
		{
			entry.setName(updatedDictionary.getName());
			DictionaryResource.DICTIONARY_MANAGER.save(entry);
		}
		List<Words> dictionaryFromDB = DictionaryResource.WORDS_MANAGER.getWordsByDictionary(entry);
		List<Words> listToSave = updateWords(updatedDictionary, dictionaryFromDB);
		boolean failed = !DictionaryResource.WORDS_MANAGER.saveList(listToSave);
		ShortMessageResponse messsage = new ShortMessageResponse("Failed to update! Problem in database!");
		if (failed)
		{
			System.err.println("Words:" + listToSave.size());
			return Response.status(Status.CONFLICT).entity(messsage).build();
		}
		else
		{
			cleanDB();
			return Response.ok().build();
		}
	}

	private void cleanDB()
	{
		try
		{
			new CleanDataBaseAction().run();
		}
		catch (Exception e)
		{
			LOGGER.error("Failed to clean database!", e);
		}
	}

	private List<Words> updateWords(DictionaryAdvanced updatedDictionary, List<Words> wordsFromDB)
	{
		Map<Integer, Words> knownWords = new HashMap<Integer, Words>();
		List<Words> listToSave = new ArrayList<Words>();
		for (Words temp : wordsFromDB)
		{
			knownWords.put(temp.getID(), temp);
		}
		for (WordsAdvanced temp : updatedDictionary.getWords())
		{
			if (temp != null)
			{
				listToSave.add(
					knownWords.containsKey(temp.getID())
						? update(knownWords.get(temp.getID()), temp, updatedDictionary.getId())
						: new Words(temp, updatedDictionary.getId()));

			}
		}
		return listToSave;
	}

	private Words update(Words oldWord, WordsAdvanced newWord, String dictionaryID)
	{
		return oldWord
			.setDictionaryID(dictionaryID)
			.setEng(newWord.getEng())
			.setGer(newWord.getGer())
			.setOp(newWord.getOp());
	}
}
