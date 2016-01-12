package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server implements Runnable{
	// Sockets
	private ServerSocket serverSocket;
	private static volatile ArrayList<ClientThread> clients;

	// Server info
	private int port = 60000;
	private int backlog = 50;
	private InetAddress addr;

	// Thread Variables
	private boolean running;
	Thread t;

	public static volatile boolean updated;
	public static volatile boolean seekingConnect = false;

	// Input from keyboard
	public static String input;

	public Server() {
		clients = new ArrayList<ClientThread>();
		running = true;
		String ip = "";
		//		try {
		//				URL whatismyip = new URL("http://checkip.amazonaws.com");
		//				BufferedReader in = new BufferedReader(new InputStreamReader(
		//				                whatismyip.openStream()));
		//		
		//				ip = in.readLine(); //you get the IP as a String
		//		}
		//		catch (IOException e) {
		//			ServerGUI.addToLog("Exception caught when retreiving IP '" + ip + "':");
		//			ServerGUI.addToLog(e.getMessage());
		//
		//			ServerGUI.addToLog("Please enter your IP:");
		//			//TODO ENTER IP MANUALLY
		//			ip = "10.20.38.112";
		//		}
		ip = "10.20.38.112";
		try {
			ServerGUI.addToLog("Starting Server...");
			// Address that the server listens from
			addr = InetAddress.getByName(ip);
			serverSocket = new ServerSocket(port, backlog, addr);
			ServerGUI.addToLog("Server started on IP " + serverSocket.getInetAddress());		// Server socket started
		}
		catch (IOException e) {
			ServerGUI.addToLog("Exception caught when trying to listen on port 60000 of '" + ip + "' or listening for a connection:");
			ServerGUI.addToLog(e.getMessage());
		}
	}

	// Automatically calls run afterword
	public void start() {
		if (t == null){
			t = new Thread(this);
			t.setDaemon(true);
			t.start();
		}
	}
	public void run(){

		updated = false;
		while (running){
			//			if (!running)
			//				break;

			if (!seekingConnect)
				addClient();

			if (updated){
				ServerGUI.addToLog("Going to update now.");
				updateClients();
				ServerGUI.addToLog("Left updating");
			}
			//if (clients.get(0).getClient().equals(null) && (clients.get(0).getClient().isConnected()) ) System.out.println("blah");	//TODO why is THIS needed? - keeps socket from closing
		}
	}

	// Whenever message is received, relay message to all clients
	public synchronized void updateClients(){
		ServerGUI.addToLog("Updating cleints");
		for (int x = 0; x < clients.size()-1; x++) {	// Note, there is always one more client than there are actual clients because of the one currently searching
			ServerGUI.addToLog("client");
			try (PrintWriter out =  new PrintWriter(clients.get(x).getClient().getOutputStream(), true)) {
				out.println(clients.get(x).name + ": " + clients.get(x).inputLine);		// Send out user input		
			} catch (IOException e) {
				ServerGUI.addToLog("Error sending to clients!");
				e.printStackTrace();
			}
		}

		updated = false;
		
	}

	public synchronized void addClient(){
		ServerGUI.addToLog("Adding client now");
		// Stop the program from adding more client threads
		seekingConnect = true;
		ServerGUI.addToLog("will now continue lokng for connections\nProceeding to add a new client");
		// Create new clientThread
		clients.add(new ClientThread(serverSocket));
		ServerGUI.addToLog("new clienthrread should be done");
		// Seek connections on that thread, then maintain it
		ServerGUI.addToLog("That clientthread should be looking for a client now");
		clients.get(clients.size()-1).start();
		ServerGUI.addToLog("Number of clients: " + clients.size());
	}
	
	public static synchronized void inforNewConnect(){
		// Informing clients
		ServerGUI.addToLog("informing clients now.");
		for (int x = 0; x < clients.size()-1; x++) {	// Note, there is always one more client than there are actual clients because of the one currently searching
			try (PrintWriter out =  new PrintWriter(clients.get(x).getClient().getOutputStream(), true)) {
				//	 TODO make sure it won't send back to user.
					out.println(clients.get(clients.size()-2).name + " has connected.");		// Send out user input, two because one accounts for the the size and one for the extra clientThread looking for connections
			} catch (IOException e) {
				ServerGUI.addToLog("Error sending to clients!");
				e.printStackTrace();
			}
		}
		updated = false;
		ServerGUI.addToLog("done ibforming clients");
	}
	
	public static synchronized void removeClient(ClientThread client){
		// Remove that client
		ServerGUI.addToLog("removing the client");
		for (int x = 0; x < clients.size()-1; x++)
			if (clients.get(x).equals(client)){
				clients.remove(x);
				client = null;
				return;
			}
		ServerGUI.addToLog(client.name + " has disconnected.");
		
		ServerGUI.addToLog("Telling toher clients that the client dc'd");
		// Informing clients
		for (int x = 0; x < clients.size()-1; x++) {	// Note, there is always one more client than there are actual clients because of the one currently searching
			try (PrintWriter out =  new PrintWriter(clients.get(x).getClient().getOutputStream(), true)) {
				out.println(client.name + " has disconnected.");		// Send out user input
			} catch (IOException e) {
				ServerGUI.addToLog("Error sending to clients!");
				e.printStackTrace();
			}
		}
	}
}
