package com.clipet.sockettaskmanager;

import java.util.ArrayList;

public class ServerRequestProcessor
{

	//
	// ATTRIBUTES
	//
	private ArrayList<Task> lastTasks;
	private String login; 



	//
	// CONSTRUCTOR
	//
	public ServerRequestProcessor()
	{
		this.login = null;
		this.lastTasks = new ArrayList<Task>();
	}



	//
	// METHODS
	//
	public String processRequest(String line)
	{
		String[] cut = (line.contains(" ") ? line.split(" ") : new String[] {line});
		String ret = EnumCommands.validateRequest(cut);

		if (ret == null)
			return dispatch(cut);
		else
			return ret;
	}

	private String dispatch(String[] cut)
	{
		if (cut[0].equalsIgnoreCase(EnumCommands.LOGIN.getCmd()))
			return login(cut[1]);
		else if (cut[0].equalsIgnoreCase(EnumCommands.GET_ID.getCmd()))
			return getById(cut[1]);
		else if (cut[0].equalsIgnoreCase(EnumCommands.GET_NAME.getCmd()))
			return getByName(cut[1]);
		else if (cut[0].equalsIgnoreCase(EnumCommands.GET_STATUS.getCmd()))
			return getByStatus(cut[1]);
		else if (cut[0].equalsIgnoreCase(EnumCommands.GET_WORKER.getCmd()))
			return getByWorker(cut[1]);
		else if (cut[0].equalsIgnoreCase(EnumCommands.GET_ALL.getCmd()))
			return getAll();
		else if (cut[0].equalsIgnoreCase(EnumCommands.ASSIGN.getCmd()))
			return assignTo(cut[1]);
		else if (cut[0].equalsIgnoreCase(EnumCommands.STATUS.getCmd()))
			return setStatus(remainingArgsToString(cut, 1));
		else if (cut[0].equalsIgnoreCase(EnumCommands.DESC.getCmd()))
			return setDesc(remainingArgsToString(cut, 1));
		else if (cut[0].equalsIgnoreCase(EnumCommands.CREATE.getCmd()))
			return create(remainingArgsToString(cut, 1));
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
			this.lastTasks = new ArrayList<Task>(1);
			this.lastTasks.add(t);
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
			this.lastTasks = new ArrayList<Task>(1);
			this.lastTasks.add(t);
			return t.toString();
		}
	}

	private String getByStatus(String status)
	{
		if (EnumStatus.contains(status) == null)
		{
			EnumStatus[] values = EnumStatus.values();
			StringBuilder sb = new StringBuilder(values[0].getStatus());

			for (int i = 1; i < values.length; i++)
				sb.append(", " + values[i].getStatus());

			return "ERROR :: Invalid status ! Available statuses are : " + sb.toString();
		}

		ArrayList<Task> tasks = Task.getTasksByStatus(status);

		if (tasks.size() == 0)
			return "ERROR :: No task with status '" + status + "' !";
		else
		{
			this.lastTasks = tasks;
			return taskListToString(tasks);
		}
	}

	private String getByWorker(String worker)
	{
		ArrayList<Task> tasks = Task.getTasksByWorker(worker);

		if (tasks.size() == 0)
			return "ERROR :: No task for worker '" + worker + "' !";
		else
		{
			this.lastTasks = tasks;
			return taskListToString(tasks);
		}
	}

	private String getAll()
	{
		ArrayList<Task> tasks = Task.getAllTasks();
		this.lastTasks = tasks;
		return taskListToString(tasks);
	}

	private String assignTo(String worker)
	{
		if (this.lastTasks.size() == 0)
			return "ERROR :: No task selected !";
		else
		{
			for (Task t : this.lastTasks)
				t.setWorker(worker);

			return taskListToString(this.lastTasks);
		}	
	}

	private String setStatus(String status)
	{
		if (this.lastTasks.size() == 0)
			return "ERROR :: No task selected !";
		else
		{
			EnumStatus es = EnumStatus.contains(status);

			if (es == null)
			{
				EnumStatus[] values = EnumStatus.values();
				StringBuilder sb = new StringBuilder(values[0].getStatus());

				for (int i = 1; i < values.length; i++)
					sb.append(", " + values[i].getStatus());

				return "ERROR :: Invalid status ! Availbale statuses are : " + sb.toString();
			}
			else
			{
				for (Task t : this.lastTasks)
					t.setStatus(es.getStatus());

				return taskListToString(this.lastTasks);
			}
		}
	}

	private String setDesc(String desc)
	{
		if (this.lastTasks.size() == 0)
			return "ERROR :: No task selected !";
		else
		{
			for (Task t : this.lastTasks)
				t.setDesc(desc);

			return taskListToString(this.lastTasks);
		}
	}

	private String create(String name)
	{
		Task t = Task.getTaskByName(name);

		if (t != null)
			return "ERROR :: A task named '" + name + "' already exists !";
		else
		{		
			this.lastTasks = new ArrayList<Task>(1);
			this.lastTasks.add(new Task(name, this.login));
			return "Task '" + name + "' created :\n" + this.lastTasks.get(0).toString();
		}
	}

	private String delete()
	{
		StringBuilder sb = new StringBuilder();

		for (Task t : this.lastTasks)
		{
			if (this.login != t.getAuthor())
				sb.append("Cannot delete task '" + t.getName() + "' because you did not created it !\n");
			else
			{
				sb.append("Task '" + t.getName() + "' deleted.\n");
				t.delete();
			}
		}

		return sb.toString();
	}

	private String getHelp()
	{
		StringBuilder sb = new StringBuilder("Available commands :\n\n");
		EnumCommands[] values = EnumCommands.values();

		for (EnumCommands ec : values)
			sb.append(ec.getHelp() + "\n");

		return sb.toString();
	}



	//
	// UTIL
	//
	private String taskListToString(ArrayList<Task> tasks)
	{
		StringBuilder sb = new StringBuilder();

		if (tasks.size() == 0)
			sb.append("No task selected !\n");
		else
		{
			for (Task iter : tasks)
				sb.append(iter.toString()).append("\n");
		}

		int length = sb.length();
		sb.delete(length - 1, length);

		return sb.toString();
	}

	private String remainingArgsToString(String[] cut, int startIndex)
	{
		StringBuilder sb = new StringBuilder();

		for (int i = startIndex; i < cut.length; i++)
			sb.append(cut[i] + " ");

		int length = sb.length();
		sb.delete(length - 1, length);

		return sb.toString();
	}

}