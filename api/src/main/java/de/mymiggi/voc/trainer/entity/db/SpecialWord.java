package de.mymiggi.voc.trainer.entity.db;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SpecialWord
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;
	private LocalDateTime addedAt;
	private String userID;
	private int wordID;

	public int getID()
	{
		return ID;
	}

	public SpecialWord setID(int iD)
	{
		ID = iD;
		return this;
	}

	public LocalDateTime getAddedAt()
	{
		return addedAt;
	}

	public SpecialWord setAddedAt(LocalDateTime addedAt)
	{
		this.addedAt = addedAt;
		return this;
	}

	public String getUserID()
	{
		return userID;
	}

	public SpecialWord setUserID(String userID)
	{
		this.userID = userID;
		return this;
	}

	public int getWordID()
	{
		return wordID;
	}

	public SpecialWord setWordID(int wordID)
	{
		this.wordID = wordID;
		return this;
	}
}
