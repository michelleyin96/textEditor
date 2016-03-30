package user;

import java.io.Serializable;

public class User implements Serializable {
	public static final long serialVersionUID = 1;
	private String name; 
	private String password;
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public String getMessage() {
		return password;
	}
}
