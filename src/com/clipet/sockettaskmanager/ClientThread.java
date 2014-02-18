package com.clipet.sockettaskmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable
{
	private Thread th;
	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	ServerRequestProcessor processor;

	public ClientThread(Socket socket) throws IOException
	{
		this.socket = socket;
		this.output = new PrintWriter(socket.getOutputStream(), true);;
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.processor = new ServerRequestProcessor();

		this.th = new Thread(this);
		this.th.start();
	}

	public void run()
	{
		System.out.println("INFO - New client : " + this.socket.getInetAddress().getHostAddress());

		try
		{
			String line = this.input.readLine();

			while (! line.equalsIgnoreCase("exit"))
			{
				String answer = this.processor.processRequest(line);
				this.output.println(answer + "\n;;;");
				line = this.input.readLine();
			}

			socket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
