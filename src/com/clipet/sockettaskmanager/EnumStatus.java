package com.clipet.sockettaskmanager;

public enum EnumStatus
{

	//
	// ENUM
	//
	FREE("Free"),
	ASSIGNED("Assigned"),
	DONE("Done");



	//
	// ATTRIBUTES
	//
	private String status;



	//
	// CONSTRUCTOR
	//
	private EnumStatus(String name)
	{
		this.status = name;
	}



	//
	// METHODS
	//
	public static EnumStatus contains(String status)
	{
		for (EnumStatus es : EnumStatus.values())
		{
			if (es.getStatus().equalsIgnoreCase(status))
				return es;
		}

		return null;
	}



	//
	// GET & SET
	//
	public String getStatus()
	{
		return this.status;
	}

}