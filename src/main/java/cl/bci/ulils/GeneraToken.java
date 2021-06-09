package cl.bci.ulils;

import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class GeneraToken {


	public static String getToken(String username) {
		try {

			String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(username)
					.setSubject(username)
					.setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME))
					.signWith(SignatureAlgorithm.HS512, Constants.SUPER_SECRET_KEY).compact();
			return "Bearer "+token;
		} catch (Exception e) {
			throw e;
		}

	}



}