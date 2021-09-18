package de.mymiggi.voc.trainer.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DictionaryEntry
{
	@Id
	private String ID;
	private String name;
	private String userID;
	private String userName;
	private String userAvater;

	public DictionaryEntry()
	{
	}

	public DictionaryEntry(String name)
	{
		this.name = name;
		this.ID = UUID.randomUUID().toString();
	}

	public DictionaryEntry(String iD, String name)
	{
		ID = iD;
		this.name = name;
	}

	public String getID()
	{
		return ID;
	}

	public void setID(String iD)
	{
		ID = iD;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUserID()
	{
		return userID;
	}

	public void setUserID(String userID)
	{
		this.userID = userID;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getUserAvater()
	{
		return userAvater;
	}

	public void setUserAvater(String userAvater)
	{
		this.userAvater = userAvater;
	}

	public void setUser(DiscordUser user)
	{
		this.userAvater = user.getAvater();
		this.userID = user.getId();
		this.userName = user.getName();
	}
}
