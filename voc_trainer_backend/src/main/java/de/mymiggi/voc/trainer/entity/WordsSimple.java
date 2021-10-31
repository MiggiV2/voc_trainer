package de.mymiggi.voc.trainer.entity;

import de.mymiggi.voc.trainer.entity.db.Words;

public class WordsSimple
{
	protected String ger;
	protected String eng;
	protected String op;

	public WordsSimple()
	{

	}

	public WordsSimple(Words words)
	{
		this.ger = words.getGer();
		this.eng = words.getEng();
		this.op = words.getOp();
	}

	public WordsSimple(WordsAdvanced words)
	{
		this.ger = words.getGer();
		this.eng = words.getEng();
		this.op = words.getOp();
	}

	public String getGer()
	{
		return ger;
	}

	public WordsSimple setGer(String ger)
	{
		this.ger = ger;
		return this;
	}

	public String getEng()
	{
		return eng;
	}

	public WordsSimple setEng(String eng)
	{
		this.eng = eng;
		return this;
	}

	public String getOp()
	{
		return op;
	}

	public WordsSimple setOp(String op)
	{
		this.op = op;
		return this;
	}
}
