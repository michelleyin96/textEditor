package client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Socket s;
	
	public Client(String hostname, int port) {
		s = null;
		try {
			s = new Socket(hostname, port);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			this.start();
			
		} catch (IOException ioe) {
			System.out.println("ioe1: " + ioe.getMessage());
		} finally {
			try {
				if (s != null) {
					s.close();
				}
				/*if (scan != null) {
					scan.close();
				}*/
			} catch (IOException ioe) {
				System.out.println("ioe2: " + ioe.getMessage());
			}
		}
	}
	
	public Boolean isSocketConnected() {
		if (s != null) {
			return s.isConnected();
		}
		return false;
	}
	
	public void run() {
		
		/*try {
			while(true) {
				ChatMessage message = (ChatMessage)ois.readObject();
				System.out.println(message.getName() + ": " + message.getMessage());
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}*/
	}
}
