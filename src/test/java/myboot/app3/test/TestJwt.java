package myboot.app3.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.desktop.ScreenSleepEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

public class TestJwt {
	
	@Test
	public void testJwt() throws InterruptedException {
		// Maintenant et cinq secondes plus tard
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.SECOND, 5);
		Date nowPlus5Seconds = c.getTime();

		// un secret pour signer le token
		String secretText = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
		byte[] secret = TextCodec.BASE64.decode(secretText);

		// construction d'un JWT
		String jws = Jwts.builder()//
		        .setIssuer("Jean-Luc MASSAT")//
		        .setSubject("Test JWT")//
		        .claim("name", "JLM")//
		        .claim("scope", "admin")//
		        .setIssuedAt(now)//
		        .setExpiration(nowPlus5Seconds)//
		        .signWith(SignatureAlgorithm.HS256, secret).compact();
		
		// Décodage d'un JWT
		Jws<Claims> jwsDecoded = Jwts.parser()//
		        .setSigningKey(secret)//
		        .parseClaimsJws(jws);
		
		TimeUnit.SECONDS.sleep(5);
		
		Date d = new Date();
		assertTrue(d.compareTo(jwsDecoded.getBody().getExpiration()) > 0);
	}
}
