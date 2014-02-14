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
	public static boolean contains(String cmd)
	{
		return EnumCommands.valueOf(cmd) != null;
	}

	public static String validateRequest(String line)
	{
		String[] cutLine = line.split(" ");
		EnumCommands ec = EnumCommands.valueOf(cutLine[0]);

		if (! EnumCommands.contains(cutLine[0]))
		{
			return "ERROR :: This command does not exist !\nSee command 'HELP' if you are lost";
		}
		else if (ec.getArgs().length != cutLine.length - 1)
		{
			String ret = "ERROR :: Wrong number of parameters !\n" + ec.getCmd();

			for (int i = 1; i < cutLine.length; i++)
				ret += " [" + ec.getArgs()[i-1] + "]";

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
