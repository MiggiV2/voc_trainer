package de.mymiggi.voc.trainer.entity;

public class DiscordUser
{
	private String name;
	private String avater;
	private String id;

	public String getName()
	{
		return name;
	}

	public DiscordUser setName(String name)
	{
		this.name = name;
		return this;
	}

	public String getAvater()
	{
		return avater;
	}

	public DiscordUser setAvater(String avater)
	{
		this.avater = avater;
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
