package com.clipet.sockettaskmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client
{

	//
	// ATTRIBUTES
	//
	Socket socket = null;
	PrintWriter output = null;
	BufferedReader input = null;



	//
	// CONSTRUCTOR
	//
	public Client(String host, int port)
	{
		try
		{
			this.socket = new Socket(host, port);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		try
		{
			this.output = new PrintWriter(this.socket.getOutputStream(), true);
			this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
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
	public void run()
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = "";

		while (! line.equalsIgnoreCase("exit"))
		{
			try
			{
				line = br.readLine();
				this.output.println(line);

				String s = this.input.readLine();
				StringBuilder fullLine = new StringBuilder();

				while (! s.equals(";;;"))
				{
					fullLine.append(s + "\n");
					s = this.input.readLine();
				}

				System.out.println("> " + fullLine.toString());
			}
			catch (IOException e)
			{
				e.printStackTrace();				
			}
		}

		System.exit(0);
	}

	public String send(String req)
	{
		this.output.println(req);

		try
		{
			return this.input.readLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		return null;
	}



	//
	// MAIN
	//
	public static void main(String[] args)
	{
		if (args.length < 2)
		{
			System.out.println("ERROR :: Invalid parameters number : \njava Client [host] [port]");
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
				System.out.println("ERROR :: '" + args[1] + "' is not a valid port !");
				System.exit(1);
			}

			(new Client(args[0], port)).run();
		}
	}

}