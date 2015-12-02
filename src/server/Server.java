package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class Server extends Thread{
	// Sockets
	private ServerSocket serverSocket;
	private ArrayList<ClientThread> clients;

	// Server info
	private int port = 60000;
	private int backlog = 50;
	private InetAddress addr;

	private boolean running;

	public Server() {
		clients = new ArrayList<ClientThread>();
		running = true;

		//		URL whatismyip = new URL("http://checkip.amazonaws.com");
		//		BufferedReader in = new BufferedReader(new InputStreamReader(
		//		                whatismyip.openStream()));
		//
		//		String ip = in.readLine(); //you get the IP as a String

		try {
			ServerGUI.addToLog("Starting Server...");
			// Address that the server listens from
			addr = InetAddress.getByName("10.20.38.112");
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

			ServerGUI.addToLog("Starting new client search");
			// Create new clientThread
			clients.add(new ClientThread(serverSocket));
			// Seek connections on that thread
			
			if (clients.get(clients.size()-1).findConnections()){
				ServerGUI.addToLog("CONNECTEDX");
				clients.get(clients.size()-1).start();	//TODO catch disconnect
			}



		}
	}

	// TODO Whenever a message is received, update the logs of all the clients
	public void updateClients(){

	}

}
