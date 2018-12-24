package org.smbaiwsy.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Request object containing the login information
 * @author anamattuzzi-stojanovic
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	/**
	 * username
	 */
	private String username;
	/*
	 * password
	 */
	private String password;

}
