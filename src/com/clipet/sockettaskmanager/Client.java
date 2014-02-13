package com.clipet.sockettaskmanager;

public class Client
{

	//
	// ATTRIBUTES
	//
	private Task lastTask;
	private String name; 



	//
	// CONSTRUCTOR
	//
	public Client()
	{
		this(null);
	}

	public Client(String name)
	{
		this.name = name;
		this.lastTask = null;
	}



	//
	// METHODS
	//
	public String processRequest(String line)
	{
		return null; // TODO
	}

}