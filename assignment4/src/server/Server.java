package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
	private Vector<ServerThread> serverThreads;
	private Boolean serverOpen;
	private ServerSocket ss;
	private int port;
	
	public Server(int port) {
		serverThreads = new Vector<ServerThread>();
		serverOpen = false;
		this.port = port;
		ss = null;
	}
	
	//Returns whether or not server is listening for incoming connections
	public Boolean isOpen() {
		return serverOpen;
	}
	
	//Set flag of whether or not server is listening for incoming connections
	public void setOpen(Boolean bool) {
		serverOpen = bool;
	}
	
	//Allows server to listen for incoming connections
	public void startServer() {
		serverOpen = true;
		try {
			ss = new ServerSocket(port);
			while (true) {
				System.out.println("waiting for connection...");
				Socket s = ss.accept();
				System.out.println("connection from " + s.getInetAddress());
				ServerThread st = new ServerThread(s, this);
				serverThreads.add(st);
			}
		} catch (IOException e) {
			System.out.println("ioe: " + e.getMessage());
		}
	}

	//Stop all communication
	public void stopServer() {
		serverOpen = false;
		if (ss != null) {
			try {
				ss.close();
			} catch (IOException ioe) {
				System.out.println("ioe closing server socket: " + ioe.getMessage());
			}
		}
	}
	
	//Removes thread from server
	public void removeServerThread(ServerThread st) {
		serverThreads.remove(st);
	}
	
	/*public void sendMessageToAllClients(ChatMessage message) {
	for (ServerThread st : serverThreads) {
		st.sendMessage(message);
	}
}*/

}
