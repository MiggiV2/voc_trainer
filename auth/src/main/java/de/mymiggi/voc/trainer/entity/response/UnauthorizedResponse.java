package de.mymiggi.voc.trainer.entity.response;

public class UnauthorizedResponse extends AuthResponse
{
	public UnauthorizedResponse()
	{
		this.active = false;
		this.code = 401;
	}

	public UnauthorizedResponse setActive(boolean active)
	{
		this.active = active;
		return this;
	}

	public UnauthorizedResponse setCode(int code)
	{
		this.code = code;
		return this;
	}
}
