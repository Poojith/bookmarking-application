package databeans;
/**
 * Homework 4 solution for favorite URLs/websites.
 * @author Poojith Kumar Jain (poojithj@andrew.cmu.edu)
 * Date: 07 December, 2016
 * Course: 08-672 (J2EE Web Application Development)
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import org.genericdao.PrimaryKey;

@PrimaryKey("userId")

public class UserBean implements Comparable<UserBean> {

	private int userId;
	private String email;
	private String firstName;
	private String lastName;
	private String hashedPassword = "*";
	private int salt = 0;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public int getSalt() {
		return salt;
	}

	public void setSalt(int salt) {
		this.salt = salt;
	}

	public int hashCode() {
		return email.hashCode();
	}

	public boolean checkPassword(String password) {
		return hashedPassword.equals(hash(password));
	}

	public void encodePassword(String s) {
		salt = newSalt();
		hashedPassword = hash(s);
	}

	private int newSalt() {
		Random random = new Random();
		return random.nextInt(8192) + 1; // salt cannot be zero
	}

	private String hash(String clearPassword) {
		if (salt == 0)
			return null;

		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError("Cannot find SHA1 algorithm in the java.security package");
		}

		String saltString = String.valueOf(salt);

		messageDigest.update(saltString.getBytes());
		messageDigest.update(clearPassword.getBytes());
		byte[] digestBytes = messageDigest.digest();

		StringBuffer digestSB = new StringBuffer();
		for (int i = 0; i < digestBytes.length; i++) {
			int lowNibble = digestBytes[i] & 0x0f;
			int highNibble = (digestBytes[i] >> 4) & 0x0f;
			digestSB.append(Integer.toHexString(highNibble));
			digestSB.append(Integer.toHexString(lowNibble));
		}
		String digestStr = digestSB.toString();

		return digestStr;
	}

	@Override
	public int compareTo(UserBean otherUser) {
		int result = lastName.compareTo(otherUser.lastName);
		if (result != 0) {
			return result;
		}
		result = firstName.compareTo(otherUser.firstName);
		if (result != 0) {
			return result;
		}
		return email.compareTo(otherUser.email);
	}
}