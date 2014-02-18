package com.clipet.sockettaskmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

public class Server
{

	//
	// ATTRIBUTES
	//
	private ServerSocket server = null;



	//
	// CONSTRUCTOR
	//
	public Server(int port)
	{
		try
		{
			this.server = new ServerSocket(port);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}



	//
	// METHODS
	//
	private void run() throws IOException
	{
		while (true)
		{
			new ClientThread(this.server.accept());
		}
	}



	//
	// MAIN
	//
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String sPort;
		int port = -1;

		if (args.length < 1)
		{
			System.out.println("Port ?");
			sPort = br.readLine();
		}
		else
			sPort = args[0];

		try
		{
			port = Integer.valueOf(sPort);
		}
		catch (NumberFormatException exc)
		{
			System.out.println("ERROR :: '" + sPort + "' is not a valid port !");
			System.exit(1);
		}

		(new Server(port)).run();
	}

}
