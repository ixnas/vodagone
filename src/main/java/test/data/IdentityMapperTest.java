package test.data;

import vodagone.data.IdentityMapper;
import vodagone.domain.Abonnee;
import org.junit.*;

public class IdentityMapperTest {

	private Abonnee getAbonnee () {
		return new Abonnee (0, "Sjoerd Scheffer", "sjoerd@b00n.org", "hahaha");
	}

	@Test
	public void addAndCheckIfExists () {
		Abonnee abonnee = getAbonnee ();
		IdentityMapper identityMapper = new IdentityMapper ();
		identityMapper.addToIdentityMap (abonnee);
		Assert.assertTrue (identityMapper.alreadyInIdentityMap (abonnee.getId ()));
		Assert.assertTrue (identityMapper.alreadyInIdentityMap (abonnee.getEmail ()));
		Assert.assertEquals (identityMapper.getFromIdentityMap (abonnee.getId ()), abonnee);
		Assert.assertEquals (identityMapper.getFromIdentityMap (abonnee.getEmail ()), abonnee);
	}

	public void removeAndCheckIfExists () {
		Abonnee abonnee = getAbonnee ();
		IdentityMapper identityMapper = new IdentityMapper ();
		identityMapper.addToIdentityMap (abonnee);
		identityMapper.clearIdentityMap ();
		Assert.assertFalse (identityMapper.alreadyInIdentityMap (abonnee.getId ()));
		Assert.assertFalse (identityMapper.alreadyInIdentityMap (abonnee.getEmail ()));
		Assert.assertEquals (identityMapper.getFromIdentityMap (abonnee.getId ()), null);
		Assert.assertEquals (identityMapper.getFromIdentityMap (abonnee.getEmail ()), null);
	}

}
