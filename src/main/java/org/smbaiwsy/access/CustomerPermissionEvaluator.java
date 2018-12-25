package org.smbaiwsy.access;

import java.io.Serializable;
import java.util.Optional;

import org.smbaiwsy.database.entity.Customer;
import org.smbaiwsy.database.repository.CustomerRepository;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.extern.slf4j.Slf4j;

/**
 * Evaluates if the given reference id of the customer is equal to the reference
 * id of the authenticated customer
 * 
 * @author anamattuzzi-stojanovic
 *
 */
@Slf4j
public class CustomerPermissionEvaluator implements PermissionEvaluator {

	private CustomerRepository repository;

	/**
	 * Overloaded constructor
	 * 
	 * @param repository
	 *            the customer repository
	 */
	public CustomerPermissionEvaluator(CustomerRepository repository) {
		this.repository = repository;
	}

	@Override
	public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
		if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
			return false;
		}
		return hasPrivilege(auth, targetDomainObject, permission);
	}

	/**
	 * Evaluates if the given reference id of the customer is equal to the reference
	 * id of the authenticated customer
	 * 
	 * @param auth
	 *            the authentication object
	 * @param targetDomainObject
	 *            the object to be checked
	 * @param permission
	 *            placeholder for the permission
	 * @return true if the given reference id of the customer is equal to the
	 *         reference id of the authenticated customer
	 */
	private boolean hasPrivilege(Authentication auth, Object targetDomainObject, Object permission) {
		log.info(auth.getClass() + " " + permission + " " + targetDomainObject);
		if (auth instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) auth;
			Optional<Customer> customer = repository.findByEMail(token.getName());
			if (customer.isPresent()) {
				return customer.get().getRefId().equals(targetDomainObject.toString());
			}
		}
		return false;
	}

	@Override
	public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
		if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
			return false;
		}
		return hasPrivilege(auth, targetId, permission.toString());
	}
}