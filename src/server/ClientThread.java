package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread implements Runnable{
	// Thread
	public Thread t;

	// Sockets
	private Socket client;
	private ServerSocket serverSocket;

	// Run-time Vars
	private boolean running;
	public String inputLine;
	public String name;

	public ClientThread(ServerSocket serverSocket){
		running = true;
		// Connecting to available clients
		this.serverSocket = serverSocket;
		//connected = false;
	}

	// Waits for connections, then returns a whether or not it found one
	public void findConnections(){
		//	ServerGUI.addToLog("OK4");
		try {
			//ServerGUI.addToLog("OK5");
			ServerGUI.addToLog("Looking for connections");
			client = serverSocket.accept();	// Begin looking for connections
			ServerGUI.addToLog(client.isConnected()? "connected" : "sopmehow not connected?");
		} catch (IOException e) {
			ServerGUI.addToLog("Error establishing connection to client:\n");
			ServerGUI.addToLog(e.getMessage());
			return;
		}
		//ServerGUI.addToLog("ok6");
	}

	public void start(){
		if (t == null){
			t = new Thread(this);
			t.setDaemon(true);
			t.start();
		}
	}

	public void run(){
		findConnections();
		Server.seekingConnect = false;
		ServerGUI.addToLog("Client with IP " + this.client.getInetAddress() + " has connected.");
		
		// Opening input stream that takes input from the client's socket
		try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
			// Receiving name

			ServerGUI.addToLog("Reading in name");
			
			name = in.readLine();
			ServerGUI.addToLog("Client " + this.client.getInetAddress() + " set name to " + name);
			
			ServerGUI.addToLog("Going to inform clients of connect");
			
			Server.inforNewConnect();

			ServerGUI.addToLog("Client shsould be informed of connect\nSetting input back to null");
			
			inputLine = null;

		//	ServerGUI.addToLog("inputLine is" + (inputLine.equals(null)? "null" : inputLine) + "\n Starting main loop");
			
			while (running){
				System.out.println(" waiting for input...");
				inputLine = in.readLine(); //TODO replace with message object 
				// Because message object will be error trapped before sending on client side

				if (inputLine != null) {
					ServerGUI.addToLog(name + ": " + inputLine);
					ServerGUI.addToLog("person said this");
					Server.updated = true;
					ServerGUI.addToLog(Server.updated ? "true" : "false");
				}	
			}
			
		} catch (IOException e) {
			ServerGUI.addToLog("Client with IP " + client.getInetAddress() + " has encountered an error:");
			ServerGUI.addToLog(e.getMessage());
			e.printStackTrace();
			
			// Telling server to remove the client
			Server.removeClient(this);
		}
	}

	public Socket getClient() {
		return client;
	}
}
