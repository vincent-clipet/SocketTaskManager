package com.clipet.sockettaskmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{

	//
	// ATTRIBUTES
	//
	private ServerSocket server = null;



	//
	// CONSTRUCTOR
	//
	public Server()
	{
		try
		{
			this.server = new ServerSocket(8599);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void start()
	{
		welcome();
	}



	//
	// METHODS
	//
	private void welcome()
	{
		Socket socket = null;
		ServerRequestProcessor client = null;

		while (true)
		{
			try
			{
				socket = this.server.accept();
				client = new ServerRequestProcessor();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			}

			processRequest(socket, client);
		}
	}

	private void processRequest(Socket socket, ServerRequestProcessor client)
	{
		PrintWriter toSend = null;
		BufferedReader toRead = null;

		try
		{
			toSend = new PrintWriter(socket.getOutputStream(), true);	
			toRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = toRead.readLine();

			String answer = client.processRequest(line);

			toSend.println(answer);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}



	//
	// GET & SET
	//

}
