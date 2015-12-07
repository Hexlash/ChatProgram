package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThread implements Runnable{
	public Thread t;
	Socket client;
	ServerSocket serverSocket;
	public boolean conFound;

	public ClientThread(ServerSocket serverSocket){
		// Connecting to available clients
		this.serverSocket = serverSocket;
		conFound = false;
	}

	// Waits for connections, then returns a whether or not it found one
	public void findConnections(ArrayList<ClientThread> clients){
		try {
			client = serverSocket.accept();	// Begin looking for connections
		} catch (IOException e) {
			ServerGUI.addToLog("Error establishing connection to client:\n");
			ServerGUI.addToLog(e.getMessage());
			return;
		}
		ServerGUI.addToLog("CONNECTEDX");
		clients.get(clients.size()-1).start();	//TODO catch disconnect
	}
	
	public void start(){
		if (t == null){
			t = new Thread(this);
			t.setDaemon(true);
			t.start();
		}
	}

	public void run(){
		System.out.println("A");
		
		boolean running = true;
		ServerGUI.addToLog("Client with IP " + this.client.getInetAddress() + " has connected.");

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			//Opening input stream that takes input from the client's socket
			
			while (running){
				String inputLine = null;
				//System.out.println("c");
				if(in.ready())
					inputLine = in.readLine(); //TODO replace with message object 
				// Because message object will be error trapped before sending on client side
				//System.out.println("d");
				if (inputLine!=null && inputLine.equals("Off")){
					ServerGUI.addToLog("Client with IP " + client.getInetAddress() + " disconnecting.");
					in.close();
					running = false;
					return;
				}
				//System.out.println("e");
				if(inputLine!=null)
					ServerGUI.addToLog(inputLine);
			
//				if (inputLine.equals("Off")){
//					ServerGUI.addToLog("Client with IP " + client.getInetAddress() + " disconnecting.");
//					in.close();
//					return;
//				}
					
			}
//
		} catch (IOException e) {
			ServerGUI.addToLog("Client with IP " + client.getInetAddress() + " has encountered an error:");
			ServerGUI.addToLog(e.getMessage());
			e.printStackTrace();
		}

		System.out.println("B");
	}
}
