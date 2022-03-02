package de.mymiggi.voc.trainer.entity;

public class DiscordUser extends DiscordUserSimple
{
	private boolean isAdmin;

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

	public DiscordUser setName(String name)
	{
		this.name = name;
		return this;
	}

	public DiscordUser setAvatar(String avater)
	{
		this.avatar = avater;
		return this;
	}

	public DiscordUser setId(String id)
	{
		this.id = id;
		return this;
	}

	public boolean isAdmin()
	{
		return isAdmin;
	}

	public DiscordUser setAdmin(boolean isAdmin)
	{
		this.isAdmin = isAdmin;
		return this;
	}
}
