package de.mymiggi.voc.trainer.entity;

import de.mymiggi.voc.trainer.entity.db.Words;

public class WordsAdvanced extends WordsSimple
{
	private int ID;
	private boolean isSpecialWord;

	public WordsAdvanced()
	{

	}

	public WordsAdvanced(Words words)
	{
		this.ID = words.getID();
		setEng(words.getEng());
		setGer(words.getGer());
		setOp(words.getOp());
	}

	public int getID()
	{
		return ID;
	}

	public WordsAdvanced setID(int id)
	{
		this.ID = id;
		return this;
	}

	public boolean isSpecialWord()
	{
		return isSpecialWord;
	}

	public WordsAdvanced setSpecialWord(boolean isSpecialWord)
	{
		this.isSpecialWord = isSpecialWord;
		return this;
	}

	public WordsAdvanced setGer(String ger)
	{
		this.ger = ger;
		return this;
	}

	public WordsAdvanced setEng(String eng)
	{
		this.eng = eng;
		return this;
	}

	public WordsAdvanced setOp(String op)
	{
		this.op = op;
		return this;
	}
}
