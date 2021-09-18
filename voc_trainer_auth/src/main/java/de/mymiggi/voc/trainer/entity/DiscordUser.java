package de.mymiggi.voc.trainer.entity;

public class DiscordUser
{
	private String id;
	private String username;
	private String avatar;
	private String discriminator;
	private int public_flags;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getAvatar()
	{
		return avatar;
	}

	public void setAvatar(String avatar)
	{
		this.avatar = avatar;
	}

	public String getDiscriminator()
	{
		return discriminator;
	}

	public void setDiscriminator(String discriminator)
	{
		this.discriminator = discriminator;
	}

	public int getPublic_flags()
	{
		return public_flags;
	}

	public void setPublic_flags(int public_flags)
	{
		this.public_flags = public_flags;
	}
}
