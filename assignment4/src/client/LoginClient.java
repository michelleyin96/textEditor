package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import user.User;

public class LoginClient extends Client {
	private LoginGUI loginGUI;
	
	public LoginClient(String hostname, int port, User user, LoginGUI loginGUI) {
		super(hostname, port, user);
		this.loginGUI = loginGUI;
	}
	
	@Override 
	public void run() {
		try {
			while(true) {
				Boolean valid = (Boolean) ois.readObject();
				if (valid) {
					System.out.println("validated user!");
					loginGUI.openMainFrame();
				} else {
					System.out.println("invalid user");
					JOptionPane.showMessageDialog(loginGUI, 
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
