package de.mymiggi.voc.trainer.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.Response;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;

public class GetPreviewAction
{
	private static final int minItems = 8;

	public Response run()
	{
		List<DictionaryEntry> allEntrys = sortList(DictionaryResource.DICTIONARY_MANAGER.getEntrys());
		List<DictionaryEntry> entrys = new ArrayList<DictionaryEntry>();
		if (allEntrys.size() < minItems)
		{
			entrys = allEntrys;
		}
		else
		{
			for (int i = 0; i < minItems; i++)
			{
				entrys.add(allEntrys.get(i));
			}
		}
		return Response.ok(entrys).build();
	}

	private List<DictionaryEntry> sortList(List<DictionaryEntry> entrys)
	{
		List<DictionaryEntry> response = new ArrayList<DictionaryEntry>();
		Collections.sort(entrys, (l1, l2) -> l2.getCreatedAd().compareTo(l1.getCreatedAd()));
		entrys.stream()
			.forEach(current -> response.add(current));
		return response;
	}
}
