package com.clipet.sockettaskmanager;

import java.util.ArrayList;

public class ServerRequestProcessor
{

	//
	// ATTRIBUTES
	//
	private Task lastTask;
	private String login; 



	//
	// CONSTRUCTOR
	//
	public ServerRequestProcessor()
	{
		this.login = null;
		this.lastTask = null;
	}



	//
	// METHODS
	//
	public String processRequest(String line)
	{
		String ret = EnumCommands.validateRequest(line);

		if (ret == null)
			return dispatch(line);
		else
			return ret;
	}

	private String dispatch(String line)
	{
		String[] cut = line.split(line);

		if (cut[0].equalsIgnoreCase(EnumCommands.LOGIN.getCmd()))
			return login(cut[1]);
		else if (cut[0].equalsIgnoreCase(EnumCommands.GET_BY_ID.getCmd()))
			return getById(cut[1]);
		else if (cut[0].equalsIgnoreCase(EnumCommands.GET_BY_NAME.getCmd()))
			return getByName(cut[1]);
		else if (cut[0].equalsIgnoreCase(EnumCommands.GET_BY_STATUS.getCmd()))
			return getByStatus(cut[1]);
		else if (cut[0].equalsIgnoreCase(EnumCommands.GET_BY_WORKER.getCmd()))
			return getByWorker(cut[1]);
		else if (cut[0].equalsIgnoreCase(EnumCommands.GET_ALL.getCmd()))
			return getAll();
		else if (cut[0].equalsIgnoreCase(EnumCommands.ASSIGN.getCmd()))
			return assignTo(cut[1]);
		else if (cut[0].equalsIgnoreCase(EnumCommands.STATUS.getCmd()))
			return setStatus(cut[1]);
		else if (cut[0].equalsIgnoreCase(EnumCommands.DESC.getCmd()))
			return setDesc(cut[1]);
		else if (cut[0].equalsIgnoreCase(EnumCommands.CREATE.getCmd()))
			return create(cut[1]);
		else if (cut[0].equalsIgnoreCase(EnumCommands.DELETE.getCmd()))
			return delete();
		else if (cut[0].equalsIgnoreCase(EnumCommands.HELP.getCmd()))
			return getHelp();
		else
			return "ERROR :: Programmer is a noob.";
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

	private String getById(String sId)
	{
		int id = -1;

		try
		{
			id = Integer.valueOf(sId);
		}
		catch (NumberFormatException exc)
		{
			return "ERROR :: '" + sId + "' is not a valid ID !";
		}

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
		if (this.login == null)
			return "You must be logged in to create a new task !";
		else
		{
			this.lastTask = new Task(name, this.login);
			return "Task '" + name + "' created :\n";
		}
	}

	private String delete()
	{
		if (this.login == null)
			return "You must be logged in to delete a task !";
		else if (this.login != this.lastTask.getAuthor())
			return "You cannot delete a task you did not create !";
		else
		{
			String ret = this.lastTask.getName();
			this.lastTask.delete();
			this.lastTask = null;
			return "Task '" + ret + "' deleted.";
		}
	}

	private String getHelp()
	{
		//TODO
		return "HELP !";
	}



	//
	// UTIL
	//
	private String taskListToString(ArrayList<Task> tasks)
	{
		StringBuilder sb = new StringBuilder();

		for (Task iter : tasks)
			sb.append(iter.toString()).append("\n");

		int length = sb.length();
		sb.delete(length - 2, length - 1);

		return sb.toString();
	}

}