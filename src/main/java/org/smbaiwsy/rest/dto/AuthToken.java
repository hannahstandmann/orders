package org.smbaiwsy.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TO containing customer e-mail address and jWT token
 * 
 * @author anamattuzzi-stojanovic
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {

	/**
	 * the username
	 */
	private String username;
	/**
	 * jWT token
	 */
	private String token;
}
