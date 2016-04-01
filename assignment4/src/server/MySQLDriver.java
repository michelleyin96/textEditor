package server;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import com.mysql.jdbc.Driver;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLDriver {
	private Connection con; 
	private final static String selectName = "SELECT * FROM USER WHERE userid=?";
	private final static String selectUserPwd = "SELECT USERID, PWD FROM USER WHERE USERID=? and PWD=?";
	private final static String addUser = "INSERT INTO USER(USERID,PWD) VALUES(?,?)";
	
	public MySQLDriver() {
		try {
			new com.mysql.jdbc.Driver();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addUser(String username, String pwd) {
		try {
			PreparedStatement ps = con.prepareStatement(addUser);
			ps.setString(1,  username);
			ps.setString(2, pwd);
			ps.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean verifyUser(String username, String pwd) {
		try {
			PreparedStatement ps = con.prepareStatement(selectUserPwd);
			ps.setString(1, username);
			ps.setString(2, pwd);
			ResultSet rs = ps.executeQuery(); 
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean doesExist(String username) {
		try {
			PreparedStatement ps = con.prepareStatement(selectName);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				//FactoryServerGUI.addMessage(result.getString(1)+" exists with count: " + 
					//	result.getInt(2));
				System.out.println("Sign-up error: user already exists");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		//FactoryServerGUI.addMessage("Unable to find product with name: " + productName);
		System.out.println("username is valid");
		return false;
	}
	
	protected void connect() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/YinmAssignment4?" + 
					"user=root&password=");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		try {
			con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
