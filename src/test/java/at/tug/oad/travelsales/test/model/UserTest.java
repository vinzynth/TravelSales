package at.tug.oad.travelsales.test.model;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Test;

import at.chrl.nutils.PasswordHash;
import at.tug.oad.travelsales.model.IUser;
import at.tug.oad.travelsales.model.UserType;
import at.tug.oad.travelsales.model.impl.User;

/**
 * @author Leopold Christian - 1331948
 * 21.11.2014 - 00:31:55
 * 
 */
public class UserTest {

	@Test
	public void test() {
		IUser u = new User("test123", "bla", "Bla", "bla@bla.bla", UserType.NORMAL);
		System.out.println(u);
		
		try {
			System.out.println(PasswordHash.validatePassword("test123", u.getPassword()));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}

}
