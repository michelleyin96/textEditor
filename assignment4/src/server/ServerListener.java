package server;

public class ServerListener extends Thread {
	private volatile boolean isListening = true;
	private Server s;

	@Override
	public void run() {
		while (isListening) {
			new Server(3649);
		}
	}

}
