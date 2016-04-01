package user;

public class Encrypt {
	public static String encryptPassword(String password) {
		String encrypted = "";
		for (int i=0; i<password.length(); i++) {
			char c = password.charAt(i);
			
			//shift letters
			if ((c >= 'a') && (c <= 'z')) {
				char shift = (char) (c + 4);
				if (shift > 'z') {
					shift = (char) (shift - 26);
				} 
				if (shift < 'a') {
					shift = (char) (shift + 26);
				}
				encrypted += shift;
			}
			
			//shift numbers
			if ((c >= '0') && (c >= '9')) {
				char shift = (char)(c + 4);
				if (shift > '9') {
					shift = (char) (shift - 10);
				}
				encrypted += shift;
			}			
		}
		return encrypted;
	}
}
