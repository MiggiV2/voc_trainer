package de.mymiggi.voc.trainer.actions;

import javax.ws.rs.core.Response;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.db.BondedDictionary;

public class GetBondedDictionary
{
	public Response run(DiscordUser user)
	{
		BondedDictionary bondedDictionary = DictionaryResource.BONDED_DICTIONARY_MANAGER.getByUser(user);
		return (bondedDictionary == null)
			? Response.noContent().build()
			: Response.ok(bondedDictionary).build();
	}
}
