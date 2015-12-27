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

	public Client(){
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
		ServerListener serverListener = new ServerListener(socket);	// Establish a listener on the server to receive input 

		try {
			PrintWriter out =  new PrintWriter(socket.getOutputStream(), true);

			while (running) {
				if (input != null)
					out.println(input);		// Send out user input
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}