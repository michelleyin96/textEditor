package user;

import java.io.Serializable;

public class User implements Serializable {
	public static final long serialVersionUID = 1;
	private String name; 
	private String password;
	private Boolean newUser;
	
	public User(String name, String password, Boolean newUser) {
		this.name = name;
		this.password = password;
		this.newUser = newUser;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPass() {
		return password;
	}
	
	public Boolean newUser() {
		return newUser;
	}
}
