package at.tug.oad.travelsales.test.model;

import org.junit.Test;

import at.tug.oad.travelsales.model.IBaseGame;
import at.tug.oad.travelsales.model.IGame;
import at.tug.oad.travelsales.model.ILevel;
import at.tug.oad.travelsales.model.IUser;
import at.tug.oad.travelsales.model.UserType;
import at.tug.oad.travelsales.model.impl.BaseGame;
import at.tug.oad.travelsales.model.impl.Game;
import at.tug.oad.travelsales.model.impl.Level;
import at.tug.oad.travelsales.model.impl.Point;
import at.tug.oad.travelsales.model.impl.User;

/**
 * @author Leopold Christian - 1331948
 * 21.11.2014 - 01:27:40
 * 
 */
public class GameTest {

	@Test
	public void test() {
		IUser u = new User("pass", "u", "uName", "bla@bla.bla", UserType.ADMIN);
		IGame g = new Game("game", "testFile.txt", u, true);
		
		ILevel l = new Level("l1", u);
		IBaseGame bg = new BaseGame("bg1", (User) u, "testFile.txt");
		bg.add(new Point("p1", 0.5, 0.4));
		bg.add(new Point("p2", 0.7, 0.2));
		bg.add(new Point("p3", 0.1, 0.1));
		bg.add(new Point("p4", 0.9, 1d));
		bg.add(new Point("p4", 0d, 1d));
		
		l.add(bg);
		g.add(l);
		
		System.out.println(u);
		System.out.println(g);
	}

}
