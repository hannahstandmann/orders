package org.smbaiwsy.database.repository;

import org.smbaiwsy.database.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * 
 * The repository for the OrderLine entities, provides the access methods.
 * 
 * @author anamattuzzi-stojanovic
 *
 */
@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

}
