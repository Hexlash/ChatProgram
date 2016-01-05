package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ClientGUI extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	// Box where text is input
	public static TextField tf;				


	// Server
	Client client;

	public ClientGUI(){
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
		textArea = new TextArea("Chat Client V.1", 10, 10, TextArea.SCROLLBARS_VERTICAL_ONLY);
		textArea.setBounds(0, 0, WIDTH-WIDTH/10, HEIGHT-50);
		add(textArea);										// Add it to the screen
		setLayout(new BorderLayout());						// Required
		textArea.setEditable(false);						// Preventing the box from being editable
		textArea.setFont(new Font("Serif", Font.PLAIN, 19));// Setting font
		log = "";

		// Textfield
		tf = new TextField();				//Setting up a new text field
		tf.setBounds(0, HEIGHT-50, WIDTH-WIDTH/10, 25);		//Set size and location
		add(tf);							//Add it to the screen
		setLayout(new BorderLayout());		//Required
		CapitalizerAction ca = new CapitalizerAction(tf);//The action listener
		tf.addActionListener(ca);			//Adding the action Listener
		tf.setVisible(true);
		tf.setFont(new Font("Serif", Font.PLAIN, 19));

		client = new Client();
		client.start();										// Create and start the client

		draw();
		drawToScreen();
	}


	public void run() {
		init();

		long start, elapsed, wait;
		
		while (running) {
			start = System.nanoTime();
			
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed/1000000;
			if(wait <0) wait = 5;
			try{
				Thread.sleep(wait);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}


	}

	public static void addToLog(String add){
		log+=add+"\n";
		textArea.setText(log);
		textArea.setCaretPosition(textArea.getText().length()); // Auto scroll to bottom
	}
}
