package de.mymiggi.voc.trainer.entity.response;

import de.mymiggi.voc.trainer.entity.DiscordUser;

public class DiscordResponse extends AuthResponse
{
	private String expires;
	private String[] scopes;
	private DiscordUser user;
	private String role;

	public DiscordResponse()
	{
		this.active = true;
	}

	public String getExpires()
	{
		return expires;
	}

	public DiscordResponse setExpires(String expires)
	{
		this.expires = expires;
		return this;
	}

	public String[] getScopes()
	{
		return scopes;
	}

	public DiscordResponse setScopes(String[] scopes)
	{
		this.scopes = scopes;
		return this;
	}

	public DiscordResponse setUser(DiscordUser user)
	{
		this.user = user;
		return this;
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

	public DiscordResponse setRole(String role)
	{
		this.role = role;
		return this;
	}

	public DiscordResponse setActive(boolean active)
	{
		this.active = active;
		return this;
	}

	public DiscordResponse setCode(int code)
	{
		this.code = code;
		return this;
	}
}
