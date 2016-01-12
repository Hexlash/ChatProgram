package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerListener implements Runnable{
	public Thread t;
	Socket client;
	Socket socket;
	//public boolean connected;

	public ServerListener(Socket socket){
		this.socket = socket;
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

		try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream() ) ) ) {
			//ClientGUI.addToLog("Connected!");
			ClientGUI.addToLog("Server listener created");
			// Opening an input stream in through the client's socket
			ClientGUI.addToLog("input is null");
			String inputLine = null;
			while(running){
				// Starting with no input
				ClientGUI.addToLog("Waiting for server input/update");
				// Waiting for input from the server
				inputLine = in.readLine(); //TODO replace with message object 
				
				
				if (inputLine != null) {
					ClientGUI.addToLog("input detected");
					ClientGUI.addToLog(inputLine);
				}
			}
		} catch (IOException e) {
			ClientGUI.addToLog("Can't find server!");
			e.printStackTrace();
		}
	}
}
