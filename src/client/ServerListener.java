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

		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ClientGUI.addToLog("Connected!");
			//Opening an input stream in through the client's socket
			while(running){
				String inputLine = null;
				//System.out.println("c");
				//if(in.ready())
				inputLine = in.readLine(); //TODO replace with message object 
				//System.out.println("d");
				if (inputLine == null){
					ClientGUI.addToLog("Disconnected from Server.");
					in.close();
					running = false;
					return;
				}
				else{
					ClientGUI.addToLog(inputLine);
					//System.out.println(Server.updated);
				}
			}
			//
		} catch (IOException e) {
			ClientGUI.addToLog("Can't find server!");
			e.printStackTrace();
		}
	}
}
