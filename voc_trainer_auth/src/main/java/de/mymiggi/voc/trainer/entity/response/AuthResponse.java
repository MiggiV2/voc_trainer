package de.mymiggi.voc.trainer.entity.response;

public class AuthResponse
{
	protected boolean active;
	protected int code;

	public boolean isActive()
	{
		return active;
	}

	public AuthResponse setActive(boolean active)
	{
		this.active = active;
		return this;
	}

	public int getCode()
	{
		return code;
	}

	public AuthResponse setCode(int code)
	{
		this.code = code;
		return this;
	}
}
