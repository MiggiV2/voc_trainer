package de.mymiggi.voc.trainer.entity;

import de.mymiggi.voc.trainer.entity.db.Words;

public class WordsResponse extends Words
{
	private boolean isSpecialWord;

	public WordsResponse()
	{

	}

	public WordsResponse(Words words)
	{
		setID(words.getID());
		setEng(words.getEng());
		setGer(words.getGer());
		setOp(words.getOp());
		setDictionaryID(words.getDictionaryID());
	}

	public boolean isSpecialWord()
	{
		return isSpecialWord;
	}

	public void setSpecialWord(boolean isSpecialWord)
	{
		this.isSpecialWord = isSpecialWord;
	}
}
