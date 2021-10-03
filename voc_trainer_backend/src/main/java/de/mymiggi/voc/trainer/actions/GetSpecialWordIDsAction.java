package de.mymiggi.voc.trainer.actions;

import java.util.List;

import javax.ws.rs.core.Response;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.db.SpecialWord;

public class GetSpecialWordIDsAction
{
	public Response run(DiscordUser user)
	{
		List<SpecialWord> result = DictionaryResource.SPECIAL_WORD_MANAGER.getByUser(user);
		return Response.ok(result).build();
	}
}
