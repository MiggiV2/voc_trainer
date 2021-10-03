package de.mymiggi.voc.trainer.actions;

import java.time.LocalDateTime;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.ShortMessageResponse;
import de.mymiggi.voc.trainer.entity.db.SpecialWord;

public class AddSpecialWordAction
{
	public Response run(DiscordUser user, SpecialWord specialWord)
	{
		if (DictionaryResource.WORDS_MANAGER.getWordsByID(specialWord.getWordID()) == null)
		{
			ShortMessageResponse message = new ShortMessageResponse("We can't find your word!");
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		}
		if (DictionaryResource.SPECIAL_WORD_MANAGER.getUserSavedWord(user, specialWord.getWordID()) != null)
		{
			ShortMessageResponse message = new ShortMessageResponse("You already added this word!");
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		}
		specialWord
			.setUserID(user.getId())
			.setAddedAt(LocalDateTime.now());
		boolean failed = !DictionaryResource.HIBERNATE_CLIENT.save(specialWord);
		DictionaryResource.SPECIAL_WORD_MANAGER.syncList();
		ShortMessageResponse message = new ShortMessageResponse("Failed to add your word!");
		return failed ? Response.status(Status.CONFLICT).entity(message).build() : Response.ok(specialWord).build();
	}
}
