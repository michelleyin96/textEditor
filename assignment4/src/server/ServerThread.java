package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import user.User;

public class ServerThread extends Thread {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private PrintWriter pw;
	private Server server;
	public ServerThread(Socket s, Server server) {
		try {
			this.server = server;
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			pw = new PrintWriter(s.getOutputStream(), true);
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		} /*finally {
			pw.close();
			try {
				ois.close();
				oos.close();
			} catch (IOException e) {
				System.out.println("e: " + e.getMessage());
			}
		}*/
	}
	
	public void sendLogin(Boolean valid) {	
		try {
			oos.writeObject(valid);
			System.out.println("Sending authentication to client...");
		} catch (IOException e) {
			System.out.println("e: " + e.getMessage());
		}
	}
	

	public void run() {
		try {
			while(true) {
				//retrieve user object from client
				User user = (User)ois.readObject();
				System.out.print("User recieved: ");
				System.out.println(user.getName() + " " + user.getPass());
				
				//send response to client
				//if new user check existence and add to db
				if (user.newUser()) {
					Boolean valid = authenticateUser(user.getName(), user.getPass());
					server.sendValidationToClient(this, valid);
				}
				//if not just verify password
				else {
					Boolean valid = authenticateUserLogin(user.getName(), user.getPass());
					server.sendValidationToClient(this, valid);
				}
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe in run: " + cnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe in run: " + ioe.getMessage());
		}
	}
	
	public Boolean authenticateUser(String username, String pwd) {
		MySQLDriver sqlDriver = new MySQLDriver();
		Boolean validUser;
		sqlDriver.connect();
		if (sqlDriver.doesExist(username)) {
			validUser = false;
		} else {
			sqlDriver.addUser(username, pwd);
			validUser = true;
		}
		sqlDriver.stop();
		return validUser;
	}
	
	public Boolean authenticateUserLogin(String username, String pwd) {
		MySQLDriver sqlDriver = new MySQLDriver();
		Boolean validUser;
		sqlDriver.connect();
		if (sqlDriver.verifyUser(username, pwd)) {
			validUser = true;
		} else {
			validUser = false;
		}
		sqlDriver.stop();
		return validUser;
	}
}
