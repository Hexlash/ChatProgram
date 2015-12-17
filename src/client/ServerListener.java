package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
		// Connecting to available clients
		this.socket = socket;
		//connected = false;
	}

	// Waits for connections, then returns a whether or not it found one
	public void findConnections(){
	//	ServerGUI.addToLog("OK4");
//		try {
//			//ServerGUI.addToLog("OK5");
//			client = serverSocket.accept();	// Begin looking for connections
//		} catch (IOException e) {
//			ServerGUI.addToLog("Error establishing connection to client:\n");
//			ServerGUI.addToLog(e.getMessage());
//			return;
//		}
		//ServerGUI.addToLog("ok6");
		Server.seekingConnect = false;
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
		boolean running = true;
		ServerGUI.addToLog("Client with IP " + this.client.getInetAddress() + " has connected.");

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			//Opening input stream that takes input from the client's socket
			
			while (running){
				String inputLine = null;
				//System.out.println("c");
				//if(in.ready())
					inputLine = in.readLine(); //TODO replace with message object 
				// Because message object will be error trapped before sending on client side
				//System.out.println("d");
				if (inputLine==null){
					ServerGUI.addToLog("Client with IP " + client.getInetAddress() + " disconnected.");
					in.close();
					running = false;
					return;
				}
				else{
					ServerGUI.addToLog(inputLine);
					Server.updated = true;
					//System.out.println(Server.updated);
				}
			
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
	}
}
