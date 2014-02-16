package com.clipet.sockettaskmanager;

public enum EnumCommands
{

	//
	// ENUM
	//
	LOGIN("LOGIN", "name"),

	GET_BY_ID("GET_BY_ID", "id"),
	GET_BY_NAME("GET_BY_NAME", "name"),
	GET_BY_STATUS("GET_BY_STATUS", "status"),
	GET_BY_WORKER("GET_BY_WORKER", "worker"),
	GET_ALL("GET_ALL"),

	ASSIGN("ASSIGN", "worker"),
	STATUS("STATUS", "status"),
	DESC("DESC", "desc"),

	CREATE("CREATE", "name"),
	DELETE("DELETE"),

	HELP("HELP");



	//
	// ATTRIBUTES
	//
	private String cmd;
	private String[] args;



	//
	// CONSTRUCTOR
	//
	EnumCommands(String cmd, String... args)
	{
		this.cmd = cmd;
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
		{
			String ret = "ERROR :: Wrong number of parameters !\n" + ec.getCmd();

			for (String arg : ec.getArgs())
				ret += " [" + arg + "]";

			return ret;
		}
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
}
