package com.clipet.sockettaskmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Task
{

	//
	// ATTRIBUTES
	//
	private final String name;
	private String desc;
	private final String author;
	private final int id;

	private String worker;
	private String status;

	private static int currentId = 1;
	private static HashMap<String, Task> tasks = new HashMap<String, Task>();



	//
	// CONSTRUCTOR
	//
	public Task(String name, String author)
	{
		this.name = name;
		this.desc = "No description yet ...";
		this.author = author;
		this.id = Task.currentId;
		Task.currentId++;
		this.worker = null;
		this.status = "libre";

		Task.tasks.put(name, this);
	}



	//
	// METHODS
	//
	public void assign(String worker, String status)
	{
		this.setWorker(worker);
		this.setStatus(status);
	}

	public void delete()
	{
		Task.tasks.remove(this.getName());
	}



	//
	// GET & SET
	//
	public String getName()
	{
		return this.name;
	}

	public String getDesc()
	{
		return this.desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getAuthor()
	{
		return this.author;
	}

	public int getId()
	{
		return this.id;
	}

	public String getWorker()
	{
		return this.worker;
	}

	public void setWorker(String worker)
	{
		this.worker = worker;
	}

	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}



	//
	// UTIL
	//	
	public static Task getTaskByName(String name)
	{
		return Task.tasks.get(name);
	}

	public static Task getTaskById(int id)
	{
		for (Entry<String, Task> entry : Task.tasks.entrySet())
		{
			Task t = entry.getValue();

			if (t.getId() == id)
				return t;
		}

		return null;
	}

	public static ArrayList<Task> getTasksByWorker(String worker)
	{
		ArrayList<Task> ret = new ArrayList<Task>();

		for (Entry<String, Task> entry : Task.tasks.entrySet())
		{
			Task t = entry.getValue();

			if (t.getWorker().equals(worker))
				ret.add(t);
		}

		return ret;
	}

	public static ArrayList<Task> getTasksByStatus(String status)
	{
		ArrayList<Task> ret = new ArrayList<Task>();

		for (Entry<String, Task> entry : Task.tasks.entrySet())
		{
			Task t = entry.getValue();

			if (t.getStatus().equals(status))
				ret.add(t);
		}

		return ret;
	}

	public static ArrayList<Task> getAllTasks()
	{
		ArrayList<Task> ret = new ArrayList<Task>();

		for (Entry<String, Task> entry : Task.tasks.entrySet())
			ret.add(entry.getValue());

		return ret;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("========== " + this.name + " ==========").append("\n")
		.append("ID : " + this.id).append("\n")
		.append("Created by : " + this.author).append("\n")
		.append("Description : " + this.desc).append("\n");

		if (worker != null)
			sb.append("Status : " + this.status).append("\n");

		if (worker != null)
			sb.append("Worker : " + this.worker).append("\n");

		return sb.toString();
	}

}
