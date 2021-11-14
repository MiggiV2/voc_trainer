package de.mymiggi.voc.trainer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import de.mymiggi.voc.trainer.actions.DeleteDictionaryAction;
import de.mymiggi.voc.trainer.actions.GetDictionaryByIDAction;
import de.mymiggi.voc.trainer.actions.SaveDictionaryAction;
import de.mymiggi.voc.trainer.actions.SearchDictionaryAction;
import de.mymiggi.voc.trainer.actions.UpdateDictionaryAction;
import de.mymiggi.voc.trainer.entity.DictionaryAdvanced;
import de.mymiggi.voc.trainer.entity.DictionarySimple;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.SearchRequest;
import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;
import de.mymiggi.voc.trainer.entity.db.Words;

class BasicTest
{
	private DiscordUser user = new DiscordUser("000000000000000001", "TestUSER#0000");
	private DiscordUser admin = new DiscordUser("000000000000000002", "TestAdmin#0000").setAdmin(true);
	private DiscordUser hacker = new DiscordUser("000000000000000003", "TestUSER#1111");

	@Test
	void test()
	{
		DictionaryAdvanced exampleDictionary = getExampleDictinary(user);
		Response response1 = new SaveDictionaryAction().run(exampleDictionary, user);
		assertEquals(response1.getStatus(), 200);
		System.out.println("Save test passed!");

		SearchRequest searchRequest = new SearchRequest(exampleDictionary.getName());
		Response response2 = new SearchDictionaryAction().run(searchRequest);
		assertEquals(response2.getStatus(), 200);
		System.out.println("Search test #1 passed!");

		searchRequest = new SearchRequest(exampleDictionary.getName() + "---");
		Response response3 = new SearchDictionaryAction().run(searchRequest);
		assertEquals(response3.getStatus(), 204);
		System.out.println("Search test #2 passed!");

		List<DictionaryEntry> entries = DictionaryResource.DICTIONARY_MANAGER.getByUser(user);
		DictionaryEntry dictionaryResult = entries.get(0);
		assertTrue(entries.size() > 0);
		System.out.println("Search test #3 passed!");

		Response response4 = new GetDictionaryByIDAction().run(dictionaryResult.getID(), admin);
		assertEquals(response4.getStatus(), 200);
		System.out.println("Get test #1 passed!");

		Response response5 = new GetDictionaryByIDAction().run(dictionaryResult.getID() + "1", admin);
		assertEquals(response5.getStatus(), 400);
		System.out.println("Get test #2 passed!");

		DictionaryAdvanced updatedDictionary = new DictionaryAdvanced().setDictionaryEntry(dictionaryResult);
		updatedDictionary.setWordList(generateListAdvanced());
		Response respnose6 = new UpdateDictionaryAction().run(dictionaryResult.getID(), admin, updatedDictionary);
		assertEquals(respnose6.getStatus(), 200);
		System.out.println("Update test #1 passed!");

		Response respnose7 = new UpdateDictionaryAction().run(dictionaryResult.getID(), hacker, updatedDictionary);
		assertEquals(respnose7.getStatus(), 403);
		System.out.println("Update test #2 passed!");

		updatedDictionary.setWordList(generateList());
		Response respnose8 = new UpdateDictionaryAction().run(dictionaryResult.getID(), user, updatedDictionary);
		assertEquals(respnose8.getStatus(), 200);
		System.out.println("Update test #3 passed!");

		DictionarySimple example1Simple = new DictionarySimple()
			.setId(dictionaryResult.getID())
			.setName(dictionaryResult.getName());
		Response respnose9 = new DeleteDictionaryAction().run(example1Simple, user);
		assertEquals(respnose9.getStatus(), 200);
		System.out.println("Delete test passed!");

	}

	private DictionaryAdvanced getExampleDictinary(DiscordUser user)
	{
		List<Words> words = generateList();
		return new DictionaryAdvanced()
			.setName("Example-" + System.currentTimeMillis())
			.setUser(user)
			.setWordList(words, false);
	}

	private List<Words> generateList()
	{
		List<Words> words = new ArrayList<Words>();
		words.add(new Words().setEng("Eng-1").setGer("Ger-1"));
		words.add(new Words().setEng("Eng-2").setGer("Ger-2"));
		words.add(new Words().setEng("Eng-3").setGer("Ger-3"));
		words.add(new Words().setEng("Eng-4").setGer("Ger-4").setOp("Op-4"));
		return words;
	}

	private List<Words> generateListAdvanced()
	{
		List<Words> words = new ArrayList<Words>();
		words.add(new Words().setEng("Eng-1").setGer("Ger-1").setOp("Op-1"));
		words.add(new Words().setEng("Eng-2").setGer("Ger-2"));
		words.add(new Words().setEng("Eng-3").setGer("Ger-3"));
		words.add(new Words().setEng("Eng-4").setGer("Ger-4").setOp("Op-4"));
		words.add(new Words().setEng("Eng-5").setGer("Ger-5"));
		words.add(new Words().setEng("Eng-6").setGer("Ger-6"));
		return words;
	}
}
