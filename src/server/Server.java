package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class Server implements Runnable{
	// Sockets
	private ServerSocket serverSocket;
	private ArrayList<ClientThread> clients;

	// Server info
	private int port = 60000;
	private int backlog = 50;
	private InetAddress addr;

	// Thread Variables
	private boolean running;
	Thread t;

	public static volatile boolean updated = false;
	public static volatile boolean seekingConnect = false;
	
	// Input from keyboard
	public static String input;
	private boolean isInput;

	public Server() {
		clients = new ArrayList<ClientThread>();
		running = true;
		String ip = "";
		//		try {
		//				URL whatismyip = new URL("http://checkip.amazonaws.com");
		//				BufferedReader in = new BufferedReader(new InputStreamReader(
		//				                whatismyip.openStream()));
		//		
		//				ip = in.readLine(); //you get the IP as a String
		//		}
		//		catch (IOException e) {
		//			ServerGUI.addToLog("Exception caught when retreiving IP '" + ip + "':");
		//			ServerGUI.addToLog(e.getMessage());
		//
		//			ServerGUI.addToLog("Please enter your IP:");
		//			//TODO ENTER IP MANUALLY
		//			ip = "10.20.38.112";
		//		}
		ip = "10.20.38.112";
		try {
			ServerGUI.addToLog("Starting Server...");
			// Address that the server listens from
			addr = InetAddress.getByName(ip);
			serverSocket = new ServerSocket(port, backlog, addr);
			ServerGUI.addToLog("Server started on IP " + serverSocket.getInetAddress());		// Server socket started
		}
		catch (IOException e) {
			ServerGUI.addToLog("Exception caught when trying to listen on port 60000 of '" + ip + "' or listening for a connection:");
			ServerGUI.addToLog(e.getMessage());
		}
	}

	// Automatically calls run afterword
	public void start() {
		if (t == null){
			t = new Thread(this);
			t.setDaemon(true);
			t.start();
		}
	}
	public void run(){
		while (running){



			//			if (!running)
			//				break;
			//ServerGUI.addToLog("OK1");
			//ServerGUI.addToLog("Starting new client search");

			if (!seekingConnect)
				addClient();
			
			

		}
	}

	// TODO Whenever a message is received, update the logs of all the clients
	public synchronized void updateClients(){

		ServerGUI.addToLog("Update clients now");

		updated = false;
	}

	public void addClient(){
		// Stop the program from adding more client threads
		seekingConnect = true;
		// Create new clientThread
		clients.add(new ClientThread(serverSocket));
		//ServerGUI.addToLog("OK2");
		// Seek connections on that thread, then maintain it
		clients.get(clients.size()-1).start();	//TODO catch disconnect
		//ServerGUI.addToLog("OK3");
		
		
	}

	
}
