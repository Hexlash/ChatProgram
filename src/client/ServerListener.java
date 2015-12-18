package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import server.Server;
import server.ServerGUI;

public class ServerListener implements Runnable{
	public Thread t;
	Socket client;
	Socket socket;
	//public boolean connected;

	public ServerListener(Socket socket){
		this.socket = socket;
		try {
		PrintWriter out =  new PrintWriter(socket.getOutputStream(), true);
		//Opening an output stream out of the client's socket
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//Opening an input stream in through the client's socket
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		//Opening a user input stream
		}
		catch ()
	}
	
	public void start(){
		if (t == null){
			t = new Thread(this);
			t.setDaemon(true);
			t.start();
		}
	}

	public void run(){
		boolean running = true;
		ClientGUI.addToLog("Connected!");

		try {
			
//
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
