package de.mymiggi.voc.trainer.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

	public String getGer()
	{
		return ger;
	}

	public void setGer(String ger)
	{
		this.ger = ger;
	}

	public String getEng()
	{
		return eng;
	}

	public void setEng(String eng)
	{
		this.eng = eng;
	}

	public String getOp()
	{
		return op;
	}

	public void setOp(String op)
	{
		this.op = op;
	}

	public String getDictionaryID()
	{
		return dictionaryID;
	}

	public void setDictionaryID(String dictionaryID)
	{
		this.dictionaryID = dictionaryID;
	}
}
