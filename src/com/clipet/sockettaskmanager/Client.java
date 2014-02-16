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
	private Socket socket = null;
	private PrintWriter output = null;
	private BufferedReader input = null;
	private final String login;



	//
	// CONSTRUCTOR
	//
	public Client(String host, int port, String login)
	{
		this.login = login;

		try
		{
			this.socket = new Socket(host, port);
		}
		catch (UnknownHostException e)
		{
			System.out.println("ERROR :: Host unknown !");
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
	public void run() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("LOGIN " + this.login);
		this.output.println("LOGIN " + this.login);
		String line = this.input.readLine();

		StringBuilder fullLogin = new StringBuilder();
		
		while (! line.equals(";;;"))
		{
			fullLogin.append(line + "\n");
			line = this.input.readLine();
		}

		System.out.println("> " + fullLogin.toString());

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
					fullLine.append("> " + s + "\n");
					s = this.input.readLine();
				}

				System.out.println(fullLine.toString());
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
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String host = "";
		String sPort = "";
		int port = -1;
		String login = "";

		if (args.length < 2)
		{
			System.out.println("Host ?");
			host = br.readLine();
			System.out.println("Port ?");
			sPort = br.readLine();
		}
		else
		{
			host = args[0];
			sPort = args[1];
		}

		try
		{
			port = Integer.valueOf(sPort);
		}
		catch (NumberFormatException exc)
		{
			System.out.println("ERROR :: '" + sPort + "' is not a valid port !");
			System.exit(1);
		}

		while (login.equals(""))
		{
			System.out.println("Login ? ");
			login = br.readLine();
		}

		System.out.println("\n\n===========================================\n\n");

		(new Client(host, port, login)).run();
	}

}