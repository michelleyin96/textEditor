//Working off assignment 2 solution

package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import frame.LogoPanel;
import frame.OfficeFrame;
import server.ServerGUI;
import spellcheck.PanelButton;
import textdocument.TextDocumentManager;

public class ClientGUI extends JFrame {
	private LogoPanel logopanel;
	private PanelButton loginButton, signupButton, offlineButton;
	private SignupGUI signupGUI;
	private LoginGUI loginGUI;
	private Client client;
	private OfficeFrame officeFrame;
		
	private static final long serialVersionUID = 1L;

	//instance constructor
	{			
		//set custom cursor
		getRootPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("img/icon/cursor.png").getImage(),
				new Point(0,0),"custom cursor"));

		//set title, size, background
		setTitle("Trojan Office");
		setSize(640,480);
		setBackground(Color.gray);
		
		//set layout manager
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//add logo
		getContentPane().setBackground(Color.gray);
		logopanel = new LogoPanel(this);
		logopanel.setVisible(true);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 4;
		c.ipady = 60;
		getContentPane().add(logopanel, c);
		
		//add login button
		loginButton = new PanelButton("LOGIN");
		loginButton.setPreferredSize(new Dimension(120, 15));
		c.ipady = 20;
		c.gridx = 3;
		c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0,5,10,5); 
		//c.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(loginButton, c);
		
		//add signup button
		signupButton = new PanelButton("SIGNUP");
		signupButton.setPreferredSize(new Dimension(120, 15));
		c.ipady = 20;
		c.gridx = 4;
		c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0,0,10,5); 
		//c.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(signupButton, c);
		
		
		//add offline button
		offlineButton = new PanelButton("OFFLINE");
		offlineButton.setPreferredSize(new Dimension(120, 15));
		c.gridx = 3;
		c.gridy = 2;
		c.gridwidth = 2; 
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0,20,60,20); 
		getContentPane().add(offlineButton, c);
		
		//initialize other frames
		initializeOtherFrames();
		
		//add action listeners 
		addActionListeners();
		
		revalidate();
		repaint();	
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		setVisible(true);		
	}
	
	private void initializeOtherFrames() {
		client = null;
		loginGUI = new LoginGUI();
		loginGUI.setVisible(false);
		signupGUI = new SignupGUI();
		signupGUI.setVisible(false);
	}
	
	private void addActionListeners() {
		class SignupListener implements ActionListener {
			public void actionPerformed(ActionEvent ae) {
				ClientGUI.this.setVisible(false);
				signupGUI.setVisible(true);
			}
		}
		signupButton.addActionListener(new SignupListener());
		
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ClientGUI.this.setVisible(false);
				loginGUI.setVisible(true);
			}
			
		});
		offlineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientGUI.this.setVisible(false);
				new OfficeFrame().setVisible(true);
			}			
		});
	}
	
		
	/* ========== main function =================== */
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try{//Set the UI to cross platform for better portability
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch(Exception e){
			System.out.println("Warning! Cross-platform L&F not used!");
		} finally {
			SwingUtilities.invokeLater(() -> { new ClientGUI().setVisible(true); });
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



