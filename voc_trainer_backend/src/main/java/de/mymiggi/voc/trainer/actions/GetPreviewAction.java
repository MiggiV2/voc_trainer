package de.mymiggi.voc.trainer.actions;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;

public class GetPreviewAction
{
	public Response run()
	{
		List<DictionaryEntry> allEntrys = DictionaryResource.DICTIONARY_MANAGER.getList();
		List<DictionaryEntry> entrys = new ArrayList<DictionaryEntry>();
		if (allEntrys.size() < 5)
		{
			entrys = allEntrys;
		}
		else
		{
			for (int i = 0; i < 5; i++)
			{
				entrys.add(allEntrys.get(i));
			}
		}
		return Response.ok(entrys).build();
	}
}
