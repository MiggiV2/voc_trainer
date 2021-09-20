package de.mymiggi.voc.trainer.entity;

public class DiscordResponse
{
	private String expires;
	private String[] scopes;
	private DiscordUser user;
	private boolean active = true;
	private String role;

	public String getExpires()
	{
		return expires;
	}

	public void setExpires(String expires)
	{
		this.expires = expires;
	}

	public String[] getScopes()
	{
		return scopes;
	}

	public void setScopes(String[] scopes)
	{
		this.scopes = scopes;
	}

	public void setUser(DiscordUser user)
	{
		this.user = user;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public String getUsername()
	{
		return String.format("user=%s#%s;avater=%s;id=%s",
			user.getUsername(),
			user.getDiscriminator(),
			user.getAvatar(),
			user.getId());
	}

	public String getID()
	{
		return user.getId();
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}
}
