package org.smbaiwsy.database.repository;

import java.util.Optional;

import org.smbaiwsy.database.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * 
 * The repository for the Product entities, provides the access methods.
 * 
 * @author anamattuzzi-stojanovic
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	/**
	 * finds product by it's unique reference id
	 * @param refId the unique reference id
	 * @return the product with the given reference id
	 */
	Optional<Product> findByRefId(String refId);
	/**
	 * finds product by it's name
	 * @param name the name of the product
	 * @return the product with the given name
	 */
	Optional<Product> findByName(String name);
}
