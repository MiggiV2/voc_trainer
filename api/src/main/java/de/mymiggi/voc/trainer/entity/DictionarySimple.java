package de.mymiggi.voc.trainer.entity;

import java.util.List;

import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;
import de.mymiggi.voc.trainer.entity.db.Words;

public class DictionarySimple
{
	protected String name;
	protected String id;
	protected DiscordUserSimple user;
	protected WordsSimple[] words;

	public String getName()
	{
		return name;
	}

	public DictionarySimple setName(String name)
	{
		this.name = name;
		return this;
	}

	public WordsSimple[] getWords()
	{
		return words;
	}

	public DictionarySimple setWordList(List<Words> words)
	{
		this.words = new WordsSimple[words.size()];
		for (int i = 0; i < words.size(); i++)
		{
			this.words[i] = new WordsSimple(words.get(i));
		}
		return this;
	}

	public DictionarySimple setWords(WordsSimple[] words)
	{
		this.words = words;
		return this;
	}

	public DictionarySimple setAdvancedWords(WordsAdvanced[] words)
	{
		this.words = new WordsSimple[words.length];
		for (int i = 0; i < words.length; i++)
		{
			this.words[i] = new WordsSimple(words[i]);
		}
		return this;
	}

	public DictionarySimple setDictionaryEntry(DictionaryEntry dictionaryEntry)
	{
		this.name = dictionaryEntry.getName();
		this.setId(dictionaryEntry.getID());
		this.setUser(new DiscordUserSimple(id)
			.setAvatar(dictionaryEntry.getUserAvater())
			.setId(dictionaryEntry.getUserID())
			.setName(dictionaryEntry.getUserName()));
		return this;
	}

	public String getId()
	{
		return id;
	}

	public DictionarySimple setId(String id)
	{
		this.id = id;
		return this;
	}

	public DiscordUserSimple getUser()
	{
		return user;
	}

	public DictionarySimple setUser(DiscordUserSimple user)
	{
		this.user = user;
		return this;
	}
}
