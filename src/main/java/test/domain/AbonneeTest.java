package test.domain;

import vodagone.domain.Abonnee;
import org.junit.*;

import static junit.framework.Assert.assertEquals;

public class AbonneeTest {

	private Abonnee getAbonnee () {
		return new Abonnee (0, "Sjoerd Scheffer", "sjoerd@b00n.org", "hahaha");
	}

	@Test
	public void create () {
		Abonnee abonnee = getAbonnee ();
		assertEquals (0, abonnee.getId ());
		assertEquals ("Sjoerd Scheffer", abonnee.getNaam ());
		assertEquals ("sjoerd@b00n.org", abonnee.getEmail ());
		assertEquals ("hahaha", abonnee.getWachtwoord ());
	}

	@Test
	public void changeFields () {
		Abonnee abonnee = getAbonnee ();
		abonnee.setNaam ("Hallo");
		abonnee.setEmail ("sjoerd@haha.nl");
		abonnee.setWachtwoord ("jojojo");
		assertEquals ("Hallo", abonnee.getNaam ());
		assertEquals ("sjoerd@haha.nl", abonnee.getEmail ());
		assertEquals ("jojojo", abonnee.getWachtwoord ());
	}
}
