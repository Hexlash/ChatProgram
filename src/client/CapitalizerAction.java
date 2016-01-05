package client;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CapitalizerAction implements ActionListener{	//Class to take in actions from the text field

	private static TextField tf;

	public CapitalizerAction(TextField tf) {
		CapitalizerAction.tf = tf;
	}

	public void actionPerformed(ActionEvent ae) {
		//ClientGUI.addToLog(tf.getText());	// Submitting text to client terminal
		Client.input = tf.getText();		// Setting input so the Client class sends it to server
		tf.setText("");		// Clearing box
		Client.update = true;
	}
}
