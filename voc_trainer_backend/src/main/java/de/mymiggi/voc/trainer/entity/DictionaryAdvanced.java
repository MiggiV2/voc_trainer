package de.mymiggi.voc.trainer.entity;

import java.util.List;

import de.mymiggi.voc.trainer.entity.db.DictionaryEntry;
import de.mymiggi.voc.trainer.entity.db.Words;

public class DictionaryAdvanced extends DictionarySimple
{
	private WordsAdvanced[] words = new WordsAdvanced[0];

	public WordsAdvanced[] getWords()
	{
		return this.words;
	}

	public DictionaryAdvanced setDictionaryEntry(DictionaryEntry dictionaryEntry)
	{
		this.name = dictionaryEntry.getName();
		this.setId(dictionaryEntry.getID());
		this.setUser(new DiscordUserSimple(id)
			.setAvatar(dictionaryEntry.getUserAvater())
			.setId(dictionaryEntry.getUserID())
			.setName(dictionaryEntry.getUserName()));
		return this;
	}

	public DictionaryAdvanced setAdvancedWordList(List<WordsAdvanced> words)
	{
		this.words = words.toArray(WordsAdvanced[]::new);
		return this;
	}

	public DictionaryAdvanced setWordList(List<Words> words, boolean AreSpecialWords)
	{
		this.words = new WordsAdvanced[words.size()];
		for (int i = 0; i < words.size(); i++)
		{
			this.words[i] = new WordsAdvanced(words.get(i)).setSpecialWord(AreSpecialWords);
		}
		return this;
	}

	public DictionaryAdvanced setWords(WordsAdvanced[] words)
	{
		this.words = words;
		return this;
	}

	public DictionaryAdvanced setName(String name)
	{
		this.name = name;
		return this;
	}

	public DictionaryAdvanced setId(String id)
	{
		this.id = id;
		return this;
	}

	public DictionaryAdvanced setUser(DiscordUserSimple user)
	{
		this.user = user;
		return this;
	}
}
