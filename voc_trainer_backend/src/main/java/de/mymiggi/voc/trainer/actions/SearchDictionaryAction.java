package de.mymiggi.voc.trainer.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.mymiggi.voc.trainer.DictionaryResource;
import de.mymiggi.voc.trainer.entity.SearchRequest;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;

public class SearchDictionaryAction
{
	public Response run(SearchRequest searchRequest)
	{
		if (searchRequest == null || searchRequest.getQuery() == null)
		{
			return Response.status(Status.BAD_REQUEST).entity("Need \"query\": in body!").build();
		}
		List<DictionaryEntry> allEntrys = DictionaryResource.DICTIONARY_MANAGER.getList();
		Map<DictionaryEntry, Integer> hitMap = new HashMap<DictionaryEntry, Integer>();
		for (DictionaryEntry current : allEntrys)
		{
			int hits = 0;
			if (current.getName().equalsIgnoreCase(searchRequest.getQuery()))
			{
				hits = 10;
			}
			for (String words : current.getName().split(" "))
			{
				if (words.toLowerCase().contains(searchRequest.getQuery().toLowerCase()))
				{
					hits++;
				}
			}
			hitMap.put(current, hits);
		}
		return Response.ok(getSortedList(hitMap)).build();
	}

	private List<DictionaryEntry> getSortedList(Map<DictionaryEntry, Integer> hitMap)
	{
		List<Entry<DictionaryEntry, Integer>> linkedList = new LinkedList<Entry<DictionaryEntry, Integer>>(hitMap.entrySet());
		List<DictionaryEntry> response = new ArrayList<DictionaryEntry>();
		Collections.sort(linkedList, (l1, l2) -> l1.getValue().compareTo(l2.getValue()));
		linkedList.stream()
			.filter(current -> current.getValue() > 0)
			.forEach(current -> response.add(current.getKey()));
		return response;
	}
}
