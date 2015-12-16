package server;

import java.net.*;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TextArea;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.swing.JPanel;

//public class MyServer{
public class ServerGUI extends JPanel implements Runnable{

	public static final int WIDTH = 1200;
	public static final int HEIGHT = 800;

	// Game thread
	private Thread thread;		// Main thread
	private boolean running; 
	private int FPS = 60;
	private long targetTime = 1000/FPS; 

	// Image vars
	private BufferedImage image;
	private Graphics2D g;

	// Information Log Text Box
	private static TextArea textArea;
	private static String log;
	
	// Server
	Server server;
	
	public ServerGUI(){
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify(){				// Declares parent status and adds listeners
		super.addNotify();
		if (thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}

	private void draw(){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		
	}
	
	private void drawToScreen() {						
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		g2.dispose();
	}

	public void init(){
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		
		
		// Creating log
		textArea = new TextArea("Chat Server V.1", 10, 10, TextArea.SCROLLBARS_VERTICAL_ONLY);
		textArea.setBounds(0, 0, WIDTH-WIDTH/10, HEIGHT-50);
		add(textArea);										// Add it to the screen
		setLayout(new BorderLayout());						// Required
		textArea.setEditable(false);						// Preventing the box from being editable
		textArea.setFont(new Font("Serif", Font.PLAIN, 19));// Setting font
		log = "";
		
		server = new Server();
		server.start();										// Create and start Server
		
		draw();
		drawToScreen();
		
	}

	public void run() {
		init();

		while (running){
			draw();
			drawToScreen();
		}
	}
	
	public static void addToLog(String add){
		log+=add+"\n";
		textArea.setText(log);
		textArea.setCaretPosition(textArea.getText().length()); // Auto scroll to bottom
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