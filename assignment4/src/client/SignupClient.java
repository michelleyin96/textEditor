package client;

import java.io.IOException;

import javax.swing.JOptionPane;

import user.User;

public class SignupClient extends Client {
	private SignupGUI signupGUI;
	
	public SignupClient(String hostname, int port, User user, SignupGUI signupGUI) {
		super(hostname, port, user);
		this.signupGUI = signupGUI;
	}
	
	@Override 
	public void run() {
		try {
			while(true) {
				Boolean valid = (Boolean) ois.readObject();
				if (valid) {
					System.out.println("validated user!");
					signupGUI.openMainFrame();
				} else {
					System.out.println("invalid user");
					JOptionPane.showMessageDialog(signupGUI, 
							"Username or Password Invalid", 
							"Log-in Failed", JOptionPane.ERROR_MESSAGE);
				}
			} 
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
		
	}
}
