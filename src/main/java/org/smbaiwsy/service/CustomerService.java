package org.smbaiwsy.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.smbaiwsy.database.entity.Customer;
import org.smbaiwsy.database.repository.CustomerRepository;
import org.smbaiwsy.exception.NotFoundException;
import org.smbaiwsy.rest.dto.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
/**
 * Service handling the customers and their identification
 * @author anamattuzzi-stojanovic
 *
 */
@Slf4j
@Service(value = "userService")
public class CustomerService implements UserDetailsService {
	@Autowired
	CustomerRepository repository;
	
	/**
	 * retrieves the list of all the customers, all the authenticated users can see the list of all the customers
	 * @return the list of existing customers
	 */
	public List<CustomerResponse> findAll(){	
		List<CustomerResponse> customers = new ArrayList<>();
		log.info("Repository is not null "+ (repository != null));
		repository.findAll()
				.forEach(customer -> customers.add(new CustomerResponse(customer.getRefId(),
						customer.getEMail(), customer.getAddress())));
		return customers;
	}

	/**
	 * implements the method for the users identified by their email
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info(email);
		Optional<Customer> customer = repository.findByEMail(email);
		
		if(!customer.isPresent()){
			log.info("Customer is not present");
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		log.info(new BCryptPasswordEncoder().encode("password"));
		return new org.springframework.security.core.userdetails.User(customer.get().getEMail(), customer.get().getPassword(), getAuthority());

	}
	/**
	 * creates the simple authority user
	 * @return the created authoruty for the ROLE_USER
	 */
	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}
	/**
	 * finds the customer by the e-mail address 
	 * @param email the e-mail address of the sought customer
	 * @return the customer identified by the indicated e-mail address
	 * @throws NotFoundException if the customer does not exist in the repository
	 */
	public Customer findOne(String email) throws NotFoundException{
		Optional<Customer> customer = repository.findByEMail(email);
		if (!customer.isPresent()) {
			throw new NotFoundException("USER_NOT_FOUND", "No user exists with the indicated e-mail address");
		}
		log.info(customer.get().getEMail());
		return customer.get();
	}

}
