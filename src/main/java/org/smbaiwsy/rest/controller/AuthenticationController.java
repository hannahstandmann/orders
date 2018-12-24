package org.smbaiwsy.rest.controller;

import org.smbaiwsy.config.TokenUtilities;
import org.smbaiwsy.database.entity.Customer;
import org.smbaiwsy.exception.NotFoundException;
import org.smbaiwsy.rest.dto.AuthToken;
import org.smbaiwsy.rest.dto.LoginRequest;
import org.smbaiwsy.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
/**
 * Controller handling the authentication
 * @author anamattuzzi-stojanovic
 *
 */
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenUtilities jwtTokenUtil;

	@Autowired
	private CustomerService userService;
    /**
     * End-point for authentication
     * @param loginRequest TO object containing customer e-mail address and password
     * @return TO containing e-mail address and token
     * @throws AuthenticationException
     * @throws NotFoundException
     */
	@RequestMapping(value = "/generate-token", method = RequestMethod.POST)
	public AuthToken register(@RequestBody LoginRequest loginRequest) throws AuthenticationException, NotFoundException {

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		final Customer customer = userService.findOne(loginRequest.getUsername());
		log.info(customer.getEMail());
		final String token = jwtTokenUtil.generateToken(customer);
		log.info(token);
		return new AuthToken(customer.getEMail(), token);
	}

}
