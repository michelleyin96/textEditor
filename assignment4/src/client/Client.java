package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import user.User;

public class Client extends Thread {
	protected ObjectInputStream ois;
	private ObjectOutputStream oos;
	private BufferedReader br;
	private Boolean isConnected;
	private SignupGUI signupGUI;
	private Socket s;
	protected User user;
	
	public Client(String hostname, int port, User user) {
		this.user = user;
		s = null;
		isConnected = false;
		
		while (!isConnected) {
			try {
				s = new Socket(hostname, port);
				System.out.println("Connected");
				isConnected = true;
				oos = new ObjectOutputStream(s.getOutputStream());
				ois = new ObjectInputStream(s.getInputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				this.start();	
				
				//send user to server
				try {
					oos.writeObject(user);
					oos.flush();
				} catch (IOException ioe) {
					System.out.println("ioe: " + ioe.getMessage());
				}
				
			} catch (IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			} 
		}
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void closeConnection() {
			try {
				if (s != null) {
					s.close();
				}
			} catch (IOException ioe) {
				System.out.println("ioe2: " + ioe.getMessage());
			}
	}
	
	public Boolean isSocketConnected() {
		if (s != null) {
			return s.isConnected();
		}
		return false;
	}
	
	
	public void run() {		
	}
}
