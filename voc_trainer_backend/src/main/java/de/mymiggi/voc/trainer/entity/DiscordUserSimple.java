package de.mymiggi.voc.trainer.entity;

public class DiscordUserSimple
{
	protected String name;
	protected String avatar;
	protected String id;

	public DiscordUserSimple()
	{
	}

	public DiscordUserSimple(String id)
	{
		this.id = id;
	}

	public DiscordUserSimple(String id, String name)
	{
		this.name = name;
		this.id = id;
	}

	public DiscordUserSimple(DiscordUser user)
	{
		this.name = user.getName();
		this.avatar = user.getAvatar();
		this.id = user.getId();
	}

	public DiscordUserSimple(String name, String avater, String id)
	{
		this.name = name;
		this.avatar = avater;
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public DiscordUserSimple setName(String name)
	{
		this.name = name;
		return this;
	}

	public String getAvatar()
	{
		return avatar;
	}

	public DiscordUserSimple setAvatar(String avater)
	{
		this.avatar = avater;
		return this;
	}

	public String getId()
	{
		return id;
	}

	public DiscordUserSimple setId(String id)
	{
		this.id = id;
		return this;
	}
}
