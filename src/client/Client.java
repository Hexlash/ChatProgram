package client;
import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args) {
		//Try with resources, ensures that resources declared are closed (if they implement autocloseable)
		
		try (
				//Socket echoSocket = new Socket("70.72.92.134", 60000); //Sunny's computer
				Socket echoSocket = new Socket("Wesleys-MacBook-Air.local", 60000);
				
				//Creating socket connected to the server on port number 60000
	            PrintWriter out =  new PrintWriter(echoSocket.getOutputStream(), true);
				//Opening an output stream out of the client's socket
	            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
				//Opening an input stream in through the client's socket
	            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				//Opening a user input stream
				
	        ) {
	            String userInput;
	            String echoText;
	            System.out.println("Socket established and connected.");
	            while ((userInput = stdIn.readLine()) != null) {	//While the user input is not empty
	                out.println(userInput);							//Send this input to the server
	                echoText = in.readLine();
	                //If for some reason the connection persists after server shut down, run diagnostic code.
	                if (!echoText.equals(userInput)){
	                	Boolean reconnected = false;
	                	System.out.println("Error, echo does not match. Connection to server lost. Attempting to contact server...");
	                	for (int i = 0; i < 20; i++){
	                		out.println("test");
	                		echoText = in.readLine();
	                		if (echoText.equals(userInput)){
	                			System.out.println("Reconnected.");
	                			i=21;
	                			reconnected = true;
	                		}
	                	}
	                	if (!reconnected){
	                		System.out.println("Failed to detect connection. You are free to manually attempt reconnection.");
	                	}
	                }
	                
	                else
	                	System.out.println("echo: " + echoText);	//Print out the server's output
	           }
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host! Error:\n" + e);
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to host! Error:\n" + e);
	            System.exit(1);
	        }
	}
}