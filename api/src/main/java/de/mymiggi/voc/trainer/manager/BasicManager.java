package de.mymiggi.voc.trainer.manager;

import java.util.ArrayList;
import java.util.List;

import de.mymiggi.voc.trainer.UniversalHibernateClient;

public class BasicManager<T>
{
	private Class<T> objectClass;
	protected List<T> entrys = new ArrayList<T>();
	protected UniversalHibernateClient hibernateClient = new UniversalHibernateClient();

	public BasicManager(Class<T> objectClass)
	{
		this.objectClass = objectClass;
		startUpdateThread();
	}

	public List<T> getEntrys()
	{
		return entrys;
	}

	public void setEntrys(List<T> entrys)
	{
		this.entrys = entrys;
	}

	public boolean save(T listToSave)
	{
		boolean status = hibernateClient.save(listToSave);
		syncList();
		return status;
	}

	public boolean saveList(List<T> listToSave)
	{
		boolean status = hibernateClient.saveList(listToSave);
		syncList();
		return status;
	}

	public boolean delete(T toDelete)
	{
		boolean status = hibernateClient.delete(toDelete);
		syncList();
		return status;
	}

	public void syncList()
	{
		this.setEntrys(hibernateClient.getList(objectClass));
		afterSyncList();
	}

	/**
	 * Triggered after syncList()
	 */
	protected void afterSyncList()
	{

	}

	private void startUpdateThread()
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				boolean running = true;
				while (running)
				{
					syncList();
					try
					{
						Thread.sleep(30 * 60 * 1000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
	}
}
