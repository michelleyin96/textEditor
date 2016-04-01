package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import frame.LogoPanel;
import frame.OfficeFrame;
import spellcheck.PanelButton;
import user.Encrypt;
import user.Password;
import user.User;

public class LoginGUI extends JFrame{
	private LogoPanel logopanel;
	private JLabel username, password, repeat;
	private JTextField userField; 
	private JPasswordField passwordField;
	private PanelButton loginButton; 
	private JPanel fieldContainer, bottomPanel;
	private Client client;
	private OfficeFrame officeFrame;
	
	
	private static final long serialVersionUID = 1L;

	{		
		// credit to stackoverflow
		//set custom cursor
		getRootPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("img/icon/cursor.png").getImage(),
				new Point(0,0),"custom cursor"));

		//set title and size
		setTitle("Trojan Office");
		setSize(640,480);	
		setBackground(Color.gray);
		
		//initialize vars
		initializeVariables();
		createGUI();
		addActionListeners();
		
		revalidate();
		repaint();
	
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		setVisible(true);		
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	private void initializeVariables() {
		logopanel = new LogoPanel(this);
		username = new JLabel("username");
		username.setHorizontalAlignment(SwingConstants.RIGHT);
		password = new JLabel("password");
		password.setHorizontalAlignment(SwingConstants.RIGHT);
		repeat = new JLabel("repeat");
		repeat.setHorizontalAlignment(SwingConstants.RIGHT);
		userField = new JTextField();
		userField.setPreferredSize(new Dimension(this.getWidth(), 20));
		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(this.getWidth(), 20));
		loginButton = new PanelButton("login");
		loginButton.setPreferredSize(new Dimension(100, 30));
		fieldContainer = new JPanel();
		bottomPanel = new JPanel();
	}
	
	private void createGUI() {
		
		Font customFont = null;
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/kenvector_future.ttf"));
			customFont = customFont.deriveFont(Font.PLAIN, (float) 12.0);
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//set fonts
		username.setFont(customFont);
		userField.setFont(customFont);
		password.setFont(customFont);
		passwordField.setFont(customFont);
		loginButton.setFont(customFont);
		UIManager.put("OptionPane.messageFont", customFont);
		UIManager.put("OptionPane.buttonFont", customFont);
		
		
		//set layout manager
		GridLayout layout = new GridLayout(3,1);
		getContentPane().setLayout(layout);
		
		//add logo
		getContentPane().add(logopanel);
		
		GridLayout fieldsLayout = new GridLayout(2,2);
		fieldsLayout.setVgap(10);
		fieldsLayout.setHgap(10);
		fieldContainer.setLayout(fieldsLayout);
		fieldContainer.setBackground(Color.gray);
		fieldContainer.setBorder(new EmptyBorder(40, 50, 40, 200));
		fieldContainer.add(username);
		fieldContainer.add(userField);
		fieldContainer.add(password);
		fieldContainer.add(passwordField);
		getContentPane().add(fieldContainer);
		
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.setBorder(new EmptyBorder(20, 280, 0, 280));
		bottomPanel.setBackground(Color.gray);
		bottomPanel.add(loginButton, BorderLayout.NORTH);
		getContentPane().add(bottomPanel);
		
	}
	
	public void openMainFrame() {
		this.setVisible(false);
		officeFrame = new OfficeFrame();
		officeFrame.setVisible(true);
	}
	
	
	public void addActionListeners() {
		loginButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = userField.getText();
				char[] password = passwordField.getPassword();

				//encrypt password and make user obj
				User user = null;
				try {
					String encryptedPass = Encrypt.encryptPassword(password.toString());
					user = new User(username, encryptedPass, false);
					new LoginClient("localhost", 3649, user, LoginGUI.this);
				} catch (Exception err) {
					System.out.println("err: " + err.getMessage());
				}
				
			}
			
		});
	}
}
