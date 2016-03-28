package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import client.SignupGUI;
import spellcheck.PanelButton;

public class ServerGUI extends JFrame {
	private PanelButton startButton; 
	private JScrollPane scrollPane; 
	private JTextArea textArea;
	//private Boolean stop; 
	//private Boolean start;
	//private Lock sLock;
	//private Condition sCondition;
	//private ServerSocket ss;
	private Server server;
	
	public ServerGUI() {
		super("Server");
		
		//initialize server
		server = new Server(3649);
		
		//create gui & functionality
		initializeVariables();
		createGUI();
		addActionAdapters();
	}
	
	private void initializeVariables() {
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		
		
		//set ui for scrollbar 
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(20, 0));
		scrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
		
		//create textpane
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.getViewport().add(textArea);		
		startButton = new PanelButton("start");
	}
	
	private void createGUI() {
		setSize(640,480);
		setBackground(Color.gray);		
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		add(startButton, BorderLayout.SOUTH);
	}
	
	private void addActionAdapters() {
		class ServerGUIListener implements ActionListener {
			public void actionPerformed(ActionEvent ae) {
				//start the server if it's currently closed
				if (!server.isOpen()) {
					startButton.setText("Stop");
					System.out.println("button started clicked");
					ServerGUI.this.revalidate();
					ServerGUI.this.repaint();
					server.setOpen(true);
					server.startServer();
				} 
				//close server if it's open 
				else {
					startButton.setText("Stop");
					ServerGUI.this.revalidate();
					ServerGUI.this.repaint();
					server.setOpen(false);
					server.stopServer();
					textArea.append("Server stopped \n");
				}
			}

		}
		startButton.addActionListener(new ServerGUIListener());

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
	}
	
	
	/*============== MAIN FUNCTION ================ */
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try{//Set the UI to cross platform for better portability
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch(Exception e){
			System.out.println("Warning! Cross-platform L&F not used!");
		} finally {
			/*Not necessary for Assignment 2 - but this is good practice*/
			SwingUtilities.invokeLater(() -> { new ServerGUI().setVisible(true); });
		}
		
		//set dock image 
		Image dockImage = null;
		try {
			dockImage = ImageIO.read(new File("img/icon/office.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Class<?> applicationClass = Class.forName("com.apple.eawt.Application");
		Method getApplicationMethod = applicationClass.getMethod("getApplication");
		Method setDockIconMethod = applicationClass.getMethod("setDockIconImage", java.awt.Image.class);
		Object macOSXApplication = getApplicationMethod.invoke(null);
		setDockIconMethod.invoke(macOSXApplication, dockImage);
	}
	
}
