package de.mymiggi.voc.trainer.entity.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import de.mymiggi.voc.trainer.entity.WordsAdvanced;

@Entity
public class Words
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;
	private String ger;
	private String eng;
	private String op;
	private String dictionaryID;

	public Words()
	{

	}

	public Words(int iD, String ger, String eng, String op, String dictionaryID)
	{
		ID = iD;
		this.ger = ger;
		this.eng = eng;
		this.op = op;
		this.dictionaryID = dictionaryID;
	}

	public Words(WordsAdvanced words, String dictionaryID)
	{
		ID = words.getID();
		this.ger = words.getGer();
		this.eng = words.getEng();
		this.op = words.getOp();
		this.dictionaryID = dictionaryID;
	}

	public String getGer()
	{
		return ger;
	}

	public Words setGer(String ger)
	{
		this.ger = ger;
		return this;
	}

	public String getEng()
	{
		return eng;
	}

	public Words setEng(String eng)
	{
		this.eng = eng;
		return this;
	}

	public String getOp()
	{
		return op;
	}

	public Words setOp(String op)
	{
		this.op = op;
		return this;
	}

	public String getDictionaryID()
	{
		return dictionaryID;
	}

	public Words setDictionaryID(String dictionaryID)
	{
		this.dictionaryID = dictionaryID;
		return this;
	}

	public int getID()
	{
		return ID;
	}

	public Words setID(int iD)
	{
		ID = iD;
		return this;
	}
}
