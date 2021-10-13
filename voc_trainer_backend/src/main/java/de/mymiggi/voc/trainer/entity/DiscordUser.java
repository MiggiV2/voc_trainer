package de.mymiggi.voc.trainer.entity;

public class DiscordUser
{
	private String name;
	private String avatar;
	private String id;

	public DiscordUser()
	{
	}

	public DiscordUser(String id)
	{
		this.id = id;
	}

	public DiscordUser(String id, String name)
	{
		this.name = name;
		this.id = id;
	}

	public DiscordUser(String name, String avater, String id)
	{
		this.name = name;
		this.avatar = avater;
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public DiscordUser setName(String name)
	{
		this.name = name;
		return this;
	}

	public String getAvatar()
	{
		return avatar;
	}

	public DiscordUser setAvatar(String avater)
	{
		this.avatar = avater;
		return this;
	}

	public String getId()
	{
		return id;
	}

	public DiscordUser setId(String id)
	{
		this.id = id;
		return this;
	}
}
