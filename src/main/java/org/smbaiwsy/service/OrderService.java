package org.smbaiwsy.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.smbaiwsy.database.entity.Customer;
import org.smbaiwsy.database.entity.Order;
import org.smbaiwsy.database.entity.OrderLine;
import org.smbaiwsy.database.entity.Product;
import org.smbaiwsy.database.repository.CustomerRepository;
import org.smbaiwsy.database.repository.OrderRepository;
import org.smbaiwsy.database.repository.ProductRepository;
import org.smbaiwsy.exception.InsufficientPermissionsException;
import org.smbaiwsy.rest.dto.OrderLineDTO;
import org.smbaiwsy.rest.dto.OrderRequest;
import org.smbaiwsy.rest.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for handling orders
 * 
 * @author anamattuzzi-stojanovic
 *
 */
@Slf4j
@Service
public class OrderService {
	@Autowired
	OrderRepository repository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ProductRepository productRepository;

	/**
	 * Retrieves the order by it's reference id. In case that the order does not
	 * belong to the authenticated user, identified by his e-mail address, no order
	 * is forwarded to the calling method
	 * 
	 * @param refId
	 *            the reference id of the order
	 * @return the list containing one TO with the information about the order
	 */
	@Transactional
	@PostFilter("filterObject.customerEmail == authentication.name")
	public List<OrderResponse> findById(String refId) {
		Optional<Order> orderOpt = repository.findByRefId(refId);
		OrderResponse response = null;
		if (orderOpt.isPresent()) {
			Order order = orderOpt.get();
			response = createResponse(order);
		}
		List<OrderResponse> orders = new ArrayList<>();
		orders.add(response);
		return orders;
	}

	/**
	 * Retrieves the order by it's reference id and by the customer it belongs to.
	 * In case that the order does not belong to the authenticated user, identified
	 * by his e-mail address, no order is forwarded to the calling method
	 * 
	 * @param customerRefId
	 *            the reference id of the customer
	 * @param refId
	 *            the reference id of the order
	 * @return the list containing one TO with the information about the order
	 */
	@PostFilter("filterObject.customerEmail == authentication.name")
	public List<OrderResponse> findByCustomerRefIdAndRefId(String customerRefId, String refId) {
		Optional<Order> orderOpt = repository.findByCustomerRefId(customerRefId, refId);
		OrderResponse response = null;
		if (orderOpt.isPresent()) {
			Order order = orderOpt.get();
			response = createResponse(order);
		}
		List<OrderResponse> orders = new ArrayList<>();
		orders.add(response);
		return orders;
	}

	/**
	 * returns all the orders. The list of Order TO is returned just in case that
	 * the orders belong to the authenticated user, identified by his e-mail
	 * address
	 * 
	 * @return the list of order TO belonging to the authenticated customer
	 */
	@PostFilter("filterObject.customerEmail == authentication.name")
	public List<OrderResponse> findAll() {
		List<Order> orders = repository.findAll();
		List<OrderResponse> orderResponses = new ArrayList<>();
		orders.forEach(order -> orderResponses.add(createResponse(order)));
		return orderResponses;
	}

	/**
	 * private method used to create order response 
	 * @param order the database entity
	 * @return order TO containing all the relevant information about the order
	 */
	private OrderResponse createResponse(Order order) {
		OrderResponse response = new OrderResponse();
		response.setRefId(order.getRefId());
		response.setCustomerEmail(order.getCustomer().getEMail());
		response.setStatus(order.getOrderStatusId());
		List<OrderLineDTO> orderItems = new ArrayList<>();
		log.info("There are " + order.getLineItems().size() + " items");
		order.getLineItems().forEach(lineItem -> {
			orderItems.add(new OrderLineDTO(lineItem.getProduct().getName(), lineItem.getProduct().getRefId(),
					lineItem.getQuantity(), lineItem.getProduct().getPrice()));
		});
		response.setLineItems(orderItems);
		return response;
	}

	/**
	 * Creates the order from the first from the list of order requests assigned to the authenticated customer, identified by e-mail address
	 * @param orders the list containing the order request TO 
	 * @param customerRefId the unique reference id of the customer
	 * @return the order TO for the created order
	 */
	@Transactional
	@PreFilter(filterTarget = "orders", value = "filterObject.customerEmail == authentication.name")
	public OrderResponse createOrder(List<OrderRequest> orders, String customerRefId)
			throws InsufficientPermissionsException {
		if (orders.size() <= 0) {
			throw new InsufficientPermissionsException("ACCESS_FORBIDDEN", "You have no right to create the resource");
		}
		Order created = new Order();
		OrderRequest order = orders.get(0);
		String refId = UUID.randomUUID().toString();
		created.setRefId(refId);
		created.setOrderStatusId(order.getOrderStatus());
		Optional<Customer> customer = customerRepository.findByRefId(customerRefId);
		if (customer.isPresent()) {
			created.setCustomer(customer.get());
		}
		repository.save(created);
		Set<OrderLine> lineItems = new HashSet<>();
		order.getLineItems().forEach(lineItem -> {
			Optional<Product> product = productRepository.findByRefId(lineItem.getRefId());
			lineItems.add(new OrderLine(created, product.get(), lineItem.getQuantity()));
		});
		created.setLineItems(lineItems);
		repository.save(created);
		return findById(refId).get(0);
	}

}
