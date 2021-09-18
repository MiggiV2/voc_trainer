package de.mymiggi.voc.trainer.entity;

public class Application
{
	private String id;
	private String name;
	private String description;
	private String summary;
	private String verify_key;
	private String icon;
	private boolean hook;
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	public String getVerify_key()
	{
		return verify_key;
	}

	public void setVerify_key(String verify_key)
	{
		this.verify_key = verify_key;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public boolean isHook()
	{
		return hook;
	}

	public void setHook(boolean hook)
	{
		this.hook = hook;
	}
}
