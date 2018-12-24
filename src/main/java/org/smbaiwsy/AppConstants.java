package org.smbaiwsy;
/**
 * Interface defining the application constants
 * @author anamattuzzi-stojanovic
 *
 */
public interface AppConstants {
	/**
	 * customers endpoint
	 */
	String CUSTOMER_API = "/customers";
	/**
	 * orders endpoint
	 */
	String ORDER_API = "/orders";
	/**
	 * the validity period of the access token
	 */
	long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
	/**
	 * the signing key for the token
	 */
    String SIGNING_KEY = "anamattuzzi20181223";
    /*
     * the prefix for the jWT token
     */
    String TOKEN_PREFIX = "Bearer ";
    /**
     * the name of the authorization header
     */
    String HEADER_STRING = "Authorization";
}
