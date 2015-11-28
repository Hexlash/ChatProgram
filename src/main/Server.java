package main;

import java.net.*;
import java.io.*;

import javax.swing.JPanel;

//public class MyServer{
public class Server extends JPanel implements Runnable{
	
	public Server(){
		
	}
	
	
	public void run() {


	}


	public static void main(String args[]){

	}

	 








	/*public static void main(String args[]) {

		try {
			System.out.println("Starting Server...");
			InetAddress addr = InetAddress.getByName("Wesleys-MacBook-Air.local");
			ServerSocket serverSocket = new ServerSocket(60000, 50, addr);
			System.out.println("Server started on IP " + serverSocket.getInetAddress() +". Awaiting connections on port 60000.");
			//Server socket started, and listening for connections
			Socket clientSocket = serverSocket.accept(); 
			//Program is paused until connection is established.

			System.out.println("Client with IP " + clientSocket.getInetAddress() + " has connected.");

			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			//Opening output stream that writes to the client's socket (with autoflush (Move data along without buffering it))

			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//Opening input stream that takes input from the client's socket

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				out.println(inputLine);
				System.out.println("\"" + inputLine + "\" was received from and echoed back to the IP of: " + clientSocket.getInetAddress());
				if (inputLine.equals("Off")){
					System.out.println("Server shutting off.");
					in.close();
					out.close();
					clientSocket.close();
					serverSocket.close();
				}
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port 60000 or listening for a connection:");
			System.out.println(e.getMessage());
		}

	}*/
}