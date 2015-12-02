package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread extends Thread{
	Socket client;
	ServerSocket serverSocket;

	public ClientThread(ServerSocket serverSocket){
		// Connecting to available clients
		this.serverSocket = serverSocket;
	}

	// Waits for connections, then returns a whether or not it found one
	public boolean findConnections(){
		try {
			client = serverSocket.accept();	// Begin looking for connections
		} catch (IOException e) {
			ServerGUI.addToLog("Error establishing connection to client:\n");
			ServerGUI.addToLog(e.getMessage());
			return false;
		}
		return true;
	}

	public void start(){
		boolean running = true;
		ServerGUI.addToLog("Client with IP " + client.getInetAddress() + " has connected.");

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			//Opening input stream that takes input from the client's socket

			while (running){
				String inputLine = in.readLine(); //TODO replace with message object 

				// Because message object will be error trapped before sending on client side, message will enver be null
				ServerGUI.addToLog(inputLine);
//				if (inputLine.equals("Off")){
//					ServerGUI.addToLog("Client with IP " + client.getInetAddress() + " disconnecting.");
//					in.close();
//					return;
//				}
					
			}

		} catch (IOException e) {
			ServerGUI.addToLog("Client with IP " + client.getInetAddress() + " has encountered an error:");
			ServerGUI.addToLog(e.getMessage());
		}


	}
}
