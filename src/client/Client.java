package client;
import java.io.*;
import java.net.*;

public class Client implements Runnable{
	// Sockets
	private Socket socket;

	// Thread Variables
	private boolean running;
	Thread t;

	// Input from keyboard
	public static String input;
	public static boolean update = false;

	public Client(){
		running = true;
		try {
			// Connecting socket
			socket = new Socket("10.20.38.112", 60000);
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host! Error:\n" + e);

		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to host! Error:\n" + e);
		}

	}

	public void start(){
		if (t == null){
			t = new Thread(this);
			t.setDaemon(true);
			t.start();
		}
	}

	public void run() {
		// Establish a listener on the server to receive input 
		ServerListener serverListener = new ServerListener(socket);
		// Starting server listening on new thread
		serverListener.start();
		
		try {
			// Establishing the object that sends the messages from the socket
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			
			while (running) {
				System.out.println(update);	// TODO why is this needed??
				if (update){				// If input is received, then
					out.println(input);		// Send out user input
					update = false;
				}
				
			}
				
		} catch (IOException e) {
			ClientGUI.addToLog("Error sending!");
			e.printStackTrace();
		}

	}
}