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
		PrintWriter output = null;
		BufferedReader input = null;

		try
		{
			output = new PrintWriter(socket.getOutputStream(), true);	
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = input.readLine();

			String answer = client.processRequest(line);

			output.println(answer);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}



	//
	// MAIN
	//
	public static void main(String[] args)
	{
		if (args.length <= 1)
		{
			System.out.println("ERROR :: Invalid parameters number : \njava Server [port]");
			System.exit(1);
		}
		else
		{
			int port = -1;

			try
			{
				port = Integer.valueOf(args[1]);
			}
			catch (NumberFormatException exc)
			{
				System.out.println("ERROR :: '" + args[2] + "' is not a valid port !");
				System.exit(1);
			}
			
			Server s = new Server(port);
			s.start();
		}
	}

}