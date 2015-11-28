package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
	// Sockets
	private ServerSocket serverSocket;
	private ArrayList<Socket> clientSockets;

	// Server info
	private int port = 60000;
	private int backlog = 50;
	private InetAddress addr;
	
	private boolean running;
	
	public Server() {
		clientSockets = new ArrayList<Socket>();
		running = true;
		
		try {
			ServerGUI.addToLog("Starting Server...");
			// Address that the server listens from
			addr = InetAddress.getByName("Wesleys-MacBook-Air.local");
			serverSocket = new ServerSocket(60000, 50, addr);
			ServerGUI.addToLog("Server started on IP " + serverSocket.getInetAddress());		// Server socket started
		}
		catch (IOException e) {
			ServerGUI.addToLog("Exception caught when trying to listen on port 60000 or listening for a connection:");
			ServerGUI.addToLog(e.getMessage());
		}
	}


	public void start() {
		
		while (running){
			
			
			if (!running)
				break;
			
			
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				ServerGUI.addToLog("");
				e.printStackTrace();
			}
		}

	}

}
