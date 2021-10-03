package de.mymiggi.voc.trainer.actions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.ShortMessageResponse;
import de.mymiggi.voc.trainer.entity.db.SpecialWord;

public class RemoveSpecialWordAction
{
	private static final Logger LOGGER = Logger.getLogger(RemoveSpecialWordAction.class.getName());

	public Response run(DiscordUser user, SpecialWord specialWord)
	{
		if (specialWord == null)
		{
			ShortMessageResponse message = new ShortMessageResponse("You need in your body request \"ID:\"");
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		}
		SpecialWord fromDB = DictionaryResource.SPECIAL_WORD_MANAGER.getUserSavedWord(user, specialWord.getWordID());
		if (fromDB == null)
		{
			ShortMessageResponse message = new ShortMessageResponse("We can't find your word!");
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		}
		LOGGER.info("fromDB:" + fromDB);
		ShortMessageResponse failedMessage = new ShortMessageResponse("Failed to delete your word!");
		boolean failed = !DictionaryResource.HIBERNATE_CLIENT.delete(fromDB);
		DictionaryResource.SPECIAL_WORD_MANAGER.syncList();
		return failed ? Response.status(Status.CONFLICT).entity(failedMessage).build() : Response.ok().build();
	}
}
