package com.clipet.sockettaskmanager;

import java.util.ArrayList;

public class Client
{

	//
	// ATTRIBUTES
	//
	private Task lastTask;
	private String login; 



	//
	// CONSTRUCTOR
	//
	public Client()
	{
		this.login = null;
		this.lastTask = null;
	}



	//
	// METHODS
	//
	public String processRequest(String line)
	{


		return null; // TODO
	}

	private String taskListToString(ArrayList<Task> tasks)
	{
		StringBuilder sb = new StringBuilder();

		for (Task iter : tasks)
			sb.append(iter.toString()).append("\n");

		int length = sb.length();
		sb.delete(length - 2, length - 1);

		return sb.toString();
	}



	private String login(String login)
	{
		if (this.login == null)
		{
			this.login = login;
			return "Now logged in as '" + login + "'";
		}
		else
			return "ERROR :: You are already logged in as " + this.login + " !";
	}

	private String getById(int id)
	{
		Task t = Task.getTaskById(id);

		if (t == null)
			return "ERROR :: No task with id '" + id + "' !";
		else
		{
			this.lastTask = t;
			return t.toString();
		}
	}

	private String getByName(String name)
	{
		Task t = Task.getTaskByName(name);

		if (t == null)
			return "ERROR :: No task named '" + name + "' !";
		else
		{
			this.lastTask = t;
			return t.toString();
		}
	}

	private String getByStatus(String status)
	{
		ArrayList<Task> tasks = Task.getTasksByStatus(status);

		if (tasks.size() == 0)
			return "ERROR :: No task with status '" + status + "' !";
		else
			return taskListToString(tasks);
	}

	private String getByWorker(String worker)
	{
		ArrayList<Task> tasks = Task.getTasksByWorker(worker);

		if (tasks.size() == 0)
			return "ERROR :: No task for worker '" + worker + "' !";
		else
			return taskListToString(tasks);
	}

	private String getAll()
	{
		ArrayList<Task> tasks = Task.getAllTasks();
		return taskListToString(tasks);
	}

	private String assignTo(String worker)
	{
		if (this.lastTask == null)
			return "ERROR :: No task selected !";
		else
		{
			this.lastTask.setWorker(worker);
			return this.lastTask.toString();
		}	
	}

	private String setStatus(String status)
	{
		if (this.lastTask == null)
			return "ERROR :: No task selected !";
		else
		{
			this.lastTask.setStatus(status);
			return this.lastTask.toString();
		}
	}

	private String setDesc(String desc)
	{
		if (this.lastTask == null)
			return "ERROR :: No task selected !";
		else
		{
			this.lastTask.setDesc(desc);
			return this.lastTask.toString();
		}
	}

	private String create(String name)
	{

	}

	private String delete()
	{

	}

}