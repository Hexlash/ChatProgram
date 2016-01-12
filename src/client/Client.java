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
	public String name;

	public Client(){
		// Setting name
		ClientGUI.addToLog("Please enter name (max 10 chars): ");
		do {
			System.out.println(" ");	// TODO WHY DO I NEED THIS
			if (input != null){
				name = input.substring(0, Math.min(input.length(), 10));
				ClientGUI.addToLog("Name set to " + name);
			}
		} while (name == null);
		
		running = true;
		try {
			// Connecting socket
			ClientGUI.addToLog("trying to connect");
			socket = new Socket("10.20.38.112", 60000);

			ClientGUI.addToLog(socket.isConnected()? "Connected" : "somehow not connected?");
		} catch (UnknownHostException e) {
			ClientGUI.addToLog("Cannot find server! Is it up?");
			System.err.println("Don't know about host! Error:\n" + e);
		} catch (IOException e) {
			ClientGUI.addToLog("No I/O with host. Is it up?");
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
		ClientGUI.addToLog("creating new serverlistener");
		ServerListener serverListener = new ServerListener(socket);
		// Starting server listening on new thread
		serverListener.start();

		// Establishing the object that sends the messages from the socket
		try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
			// Telling the server what the name is
			out.println(name);
			update = false;
			while (running) {
				if (socket.isConnected() ) System.out.println("ON");	// TODO why is this needed?? - without it, the user input isn't taken

				
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