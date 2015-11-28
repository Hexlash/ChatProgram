package client;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ClientGUI extends JPanel implements Runnable{

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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
