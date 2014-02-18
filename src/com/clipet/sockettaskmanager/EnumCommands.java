package com.clipet.sockettaskmanager;

public enum EnumCommands
{

	//
	// ENUM
	//
	LOGIN("LOGIN", "Login.", "name"),

	GET_ID("GET_ID", "Retrieve a task with this ID.", "id"),
	GET_NAME("GET_NAME", "Retrieve a task with this name.", "name"),
	GET_STATUS("GET_STATUS", "Retrieve all tasks with this status.", "status"),
	GET_WORKER("GET_WORKER", "Retrieve all tasks assigned to this worker", "worker"),
	GET_ALL("GET_ALL", "Retrieve all tasks."),

	ASSIGN("ASSIGN", "Assign this worker to the selected task.", "worker"),
	STATUS("STATUS", "Set this status to the selected task.", "status"),
	DESC("DESC", "Assign this description to the selected task.", "desc"),

	CREATE("CREATE", "Create a new task.", "name"),
	DELETE("DELETE", "Delete selected task."),

	HELP("HELP", "Print this page.");



	//
	// ATTRIBUTES
	//
	private String cmd;
	private String desc;
	private String[] args;



	//
	// CONSTRUCTOR
	//
	EnumCommands(String cmd, String desc, String... args)
	{
		this.cmd = cmd;
		this.desc = desc;
		this.args = args;
	}



	//
	// METHODS
	//
	public static EnumCommands contains(String cmd)
	{
		for (EnumCommands ec : EnumCommands.values())
		{
			if (ec.getCmd().equalsIgnoreCase(cmd))
				return ec;
		}

		return null;
	}

	public static String validateRequest(String[] cut)
	{
		EnumCommands ec = EnumCommands.contains(cut[0]);

		if (ec == null)
			return "ERROR :: This command does not exist !\nSee command 'HELP' if you are lost";
		else if (ec.getArgs().length > cut.length - 1)
			return "ERROR :: Wrong number of parameters !\n" + ec.toString();
		else
			return null;
	}



	//
	// GET & SET
	//
	public String getCmd()
	{
		return this.cmd;
	}

	public String[] getArgs()
	{
		return this.args;
	}



	//
	// UTIL
	//
	public String toString()
	{
		StringBuilder sb = new StringBuilder(this.cmd);

		for (String arg : this.args)
			sb.append(" [" + arg + "]");

		return sb.toString();
	}

	public String getHelp()
	{
		return this.toString() + "\n\t==> " + this.desc;
	}

}