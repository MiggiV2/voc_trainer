package de.mymiggi.voc.trainer.entity;

import java.util.List;

import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;
import de.mymiggi.voc.trainer.entity.db.Words;

public class Dictionary
{
	private String name;
	private String id;
	private DiscordUser user;
	private WordsResponse[] words;

	public String getName()
	{
		return name;
	}

	public Dictionary setName(String name)
	{
		this.name = name;
		return this;
	}

	public Words[] getWords()
	{
		return words;
	}

	public Dictionary setWordList(List<Words> words, boolean AreSpecialWords)
	{
		this.words = new WordsResponse[words.size()];
		for (int i = 0; i < words.size(); i++)
		{
			this.words[i] = new WordsResponse(words.get(i)).setSpecialWord(AreSpecialWords);
		}
		return this;
	}

	public Dictionary setWordList(List<Words> words)
	{
		this.words = new WordsResponse[words.size()];
		for (int i = 0; i < words.size(); i++)
		{
			this.words[i] = new WordsResponse(words.get(i));
		}
		return this;
	}

	public Dictionary setWords(WordsResponse[] words)
	{
		this.words = words;
		return this;
	}

	public Dictionary setDictionaryEntry(DictionaryEntry dictionaryEntry)
	{
		this.name = dictionaryEntry.getName();
		this.setId(dictionaryEntry.getID());
		this.setUser(new DiscordUser()
			.setAvatar(dictionaryEntry.getUserAvater())
			.setId(dictionaryEntry.getUserID())
			.setName(dictionaryEntry.getUserName()));
		return this;
	}

	public String getId()
	{
		return id;
	}

	public Dictionary setId(String id)
	{
		this.id = id;
		return this;
	}

	public DiscordUser getUser()
	{
		return user;
	}

	public Dictionary setUser(DiscordUser user)
	{
		this.user = user;
		return this;
	}
}
