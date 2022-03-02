package de.mymiggi.voc.trainer.config;

import org.hibernate.cfg.Configuration;
import org.jboss.logging.Logger;

public class DBConfig
{
	private String host;
	private String password;
	private String userName;
	private String dbName;

	private static final Logger LOGGER = Logger.getLogger(DBConfig.class.getName());

	public DBConfig()
	{
		this.host = System.getenv("DB_HOST");
		this.password = System.getenv("DB_PASSWORD");
		this.userName = System.getenv("DB_USERNAME");
		this.setDbName(System.getenv("DB_NAME"));
		if (host == null || password == null || userName == null)
		{
			this.host = "localhost";
			this.password = "postgres";
			this.userName = "postgres";
			this.setDbName("postgres");
			System.out.println("#################################################################################################");
			LOGGER.error("Database is not confiurted! Pls set environment variables: DB_HOST, DB_PASSWORD, DB_USERNAME, DB_NAME! Using dev config! NOT FOR PRODUCTIVE");
			LOGGER.warn(String.format("HOST:%s, PASSWORD:%s, USERNAME:%s, DBNAME", host, password, userName, dbName));
			System.out.println("#################################################################################################");
		}
	}

	public Configuration buildConfig()
	{
		Configuration config = new Configuration();
		config.setProperty("hibernate.connection.url", "jdbc:postgresql://" + this.getHost() + ":5432/" + this.getDbName());
		config.setProperty("hibernate.connection.username", this.getUserName());
		config.setProperty("hibernate.connection.password", this.getPassword());
		return config;
	}

	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getDbName()
	{
		return dbName;
	}

	public void setDbName(String dbName)
	{
		this.dbName = dbName;
	}
}
