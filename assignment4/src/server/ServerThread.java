package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Server server;
	public ServerThread(Socket s, Server server) {
		try {
			this.server = server;
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	/*public void sendMessage(ChatMessage message) {
		try {
			oos.writeObject(message);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}*/

	public void run() {
		/*try {
			while(true) {
				ChatMessage message = (ChatMessage)ois.readObject();
				cs.sendMessageToAllClients(message);
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe in run: " + cnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe in run: " + ioe.getMessage());
		}*/
	}
}
