package org.smbaiwsy.database.repository;

import java.util.Optional;

import org.smbaiwsy.database.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * 
 * The repository for the Customer entities, provides the access methods.
 * 
 * @author anamattuzzi-stojanovic
 *
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	/**
	 * finds the customer by its unique e-mail address
	 * @param email the e-mail address of the customer
	 * @return the customer with the given e-mail address
	 */
	Optional<Customer> findByEMail(String email);
	/**
	 * finds the customer by its unique reference id
	 * @param refId the unique reference id of the customer
	 * @return the customer with the given reference id
	 */
	Optional<Customer> findByRefId(String refId);

}
