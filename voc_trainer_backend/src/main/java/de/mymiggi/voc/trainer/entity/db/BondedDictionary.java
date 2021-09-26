package de.mymiggi.voc.trainer.entity.db;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BondedDictionary
{
	@Id
	private String userID;
	private String dictionaryID;

	public BondedDictionary(String userID, String dictionaryID)
	{
		this.userID = userID;
		this.dictionaryID = dictionaryID;
	}

	public BondedDictionary()
	{
	}

	public String getUserID()
	{
		return userID;
	}

	public void setUserID(String userID)
	{
		this.userID = userID;
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
