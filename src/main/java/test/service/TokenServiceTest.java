package test.service;

import org.junit.Assert;
import org.junit.Test;
import vodagone.service.TokenService;

public class TokenServiceTest {

	private TokenService getTokenService () {
		return new TokenService (new AbonneeMapperMock ());
	}

	@Test
	public void testLoginSucceed () {
		TokenService tokenService = getTokenService ();
		Assert.assertNotNull (tokenService.login ("sjoerd@b00n.org", "hahaha"));
	}

	@Test
	public void testLoginFail () {
		TokenService tokenService = getTokenService ();
		Assert.assertNull (tokenService.login ("sjoerd@b00dn.org", "hahahaj"));
	}

}
