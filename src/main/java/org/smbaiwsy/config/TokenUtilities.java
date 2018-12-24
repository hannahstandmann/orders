package org.smbaiwsy.config;

import org.smbaiwsy.AppConstants;
import org.smbaiwsy.database.entity.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

/**
 * Class for handling the jWT Tokens
 * 
 * @author anamattuzzi-stojanovic
 *
 */
@Component
public class TokenUtilities {

	/**
	 * Creates the jWT token from the Customer object
	 * 
	 * @param customer
	 *            the object used for authentication
	 * @return the jWT token
	 */
	public String generateToken(Customer customer) {
		return generateTokenFromSubject(customer.getEMail());
	}

	/**
	 * Generates jWT token from the the given string
	 * 
	 * @param subject
	 *            the string uniquely identifying the user
	 * @return the jWT token
	 */
	private String generateTokenFromSubject(String subject) {

		Claims claims = Jwts.claims().setSubject(subject);
		claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

		return Jwts.builder().setClaims(claims).setIssuer("http://whatever.web.runbox.net")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + AppConstants.ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
				.signWith(SignatureAlgorithm.HS256, AppConstants.SIGNING_KEY).compact();
	}

	/**
	 * validates the jwT token
	 * 
	 * @param token
	 *            the jWT token
	 * @param userDetails
	 *            the object containing the data about the user
	 * @return true if the token is still valid
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !didTokenExpire(token));
	}

	/**
	 * generic function for retrieving the information about the token
	 * 
	 * @param token
	 *            the jWT token
	 * @param claimsResolver
	 *            the accessor for the claims
	 * @return the sought claims
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * retrieves the username, in this case the e-mail address, from token
	 * 
	 * @param token
	 *            the jWT token
	 * @return the email address of the customer
	 */
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	/**
	 * retrieves the expiration dare of the specified token
	 * 
	 * @param token
	 *            the jWT token
	 * @return the moment when the token expires
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	/**
	 * creates the claims for the token
	 * 
	 * @param token
	 *            the jWT token
	 * @return the claims object
	 */
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(AppConstants.SIGNING_KEY).parseClaimsJws(token).getBody();
	}

	/**
	 * Checks if the token expired
	 * 
	 * @param token
	 *            the jWT token
	 * @return true if the token expired
	 */
	private Boolean didTokenExpire(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

}
