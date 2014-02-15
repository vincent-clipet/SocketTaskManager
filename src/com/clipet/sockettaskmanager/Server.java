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



	//
	// METHODS
	//
	private void run()
	{
		Socket socket = null;
		ServerRequestProcessor processor = null;

		while (true)
		{
			try
			{
				socket = this.server.accept();
				System.out.println("INFO - New client : " + socket.getInetAddress().getHostAddress());
				processor = new ServerRequestProcessor();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			}

			PrintWriter toSend = null;
			BufferedReader toRead = null;

			try
			{
				toSend = new PrintWriter(socket.getOutputStream(), true);	
				toRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			}
			catch (IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			}

			(new ClientThread(this, socket, toSend, toRead, processor)).run();
		}
	}

	private void processRequests(Socket socket, PrintWriter toSend, BufferedReader toRead, ServerRequestProcessor client)
	{
		try
		{
			String line = toRead.readLine();

			while (! line.equalsIgnoreCase("exit"))
			{
				String answer = client.processRequest(line);
				toSend.println(answer + "\n;;;");
				line = toRead.readLine();
			}

			socket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	//
	// CLASSES
	//
	private class ClientThread extends Thread
	{
		private Server serv;
		private Socket socket;
		private PrintWriter output;
		private BufferedReader input;
		ServerRequestProcessor processor;

		public ClientThread(Server serv, Socket socket, PrintWriter toSend, BufferedReader toRead, ServerRequestProcessor client)
		{
			this.serv = serv;
			this.socket = socket;
			this.output = toSend;
			this.input = toRead;
			this.processor = client;
		}

		public void run()
		{
			this.serv.processRequests(this.socket, this.output, this.input, this.processor);
		}
	}



	//
	// MAIN
	//
	public static void main(String[] args)
	{
		if (args.length < 1)
		{
			System.out.println("ERROR :: Invalid parameters number : \njava Server [port]");
			System.exit(1);
		}
		else
		{
			int port = -1;

			try
			{
				port = Integer.valueOf(args[0]);
			}
			catch (NumberFormatException exc)
			{
				System.out.println("ERROR :: '" + args[0] + "' is not a valid port !");
				System.exit(1);
			}

			(new Server(port)).run();
		}
	}

}
