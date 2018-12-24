package org.smbaiwsy.database.repository;

import java.util.Optional;

import org.smbaiwsy.database.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
/**
 * 
 * The repository for the Order entities, provides the access methods.
 * 
 * @author anamattuzzi-stojanovic
 *
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	/**
	 * finds order by it's unique reference id
	 * @param refId the unique reference id
	 * @return order with the given unique id
	 */
	Optional<Order> findByRefId(String refId);
	/**
	 * finds order by it's unique reference id and the reference id of the customer who created it
	 * @param customerRefId the unique reference id of the customer
	 * @param refId the unique reference id of the order
	 * @return order with the given unique id and the reference id of the customer who created it
	 */
	@Query("select o from Order o join o.customer c where o.refId = :refId and c.refId = :customerRefId")
	Optional<Order> findByCustomerRefId(@Param ("customerRefId") String customerRefId, @Param ("refId") String refId);
}
