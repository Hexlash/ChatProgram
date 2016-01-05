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
		ClientGUI.addToLog(Client.input = tf.getText());	// Submitting text
		tf.setText("");		// Clearing box
	}
}
