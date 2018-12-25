package org.smbaiwsy.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.smbaiwsy.AppConstants;
import org.smbaiwsy.database.entity.Customer;
import org.smbaiwsy.exception.BadRequestException;
import org.smbaiwsy.exception.InsufficientPermissionsException;
import org.smbaiwsy.exception.NotFoundException;
import org.smbaiwsy.rest.dto.OrderRequest;
import org.smbaiwsy.rest.dto.OrderResponse;
import org.smbaiwsy.service.CustomerService;
import org.smbaiwsy.service.OrderService;

/**
 * Handles rest calls for the orders
 * 
 * @author anamattuzzi-stojanovic
 *
 */
@Slf4j
@RestController
@RequestMapping(value = AppConstants.ORDER_API)
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@Autowired
	CustomerService customerService;

	/**
	 * end-point for the orders
	 * 
	 * @return the list of orders created by the authenticated customer
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	/*
	 * @ApiOperation(value = "Get all the orders", notes =
	 * "Send GET request to get all the orders belonging to the authenticated customer"
	 * , httpMethod = "GET", code = 200, response = OrderResponse.class,
	 * responseContainer = "List", produces = MediaType.APPLICATION_JSON_VALUE)
	 * 
	 * @ApiResponses(value = {
	 * 
	 * @ApiResponse(code = 200, response = OrderResponse.class, responseContainer =
	 * "List", message = "List of all groups info"),
	 * 
	 * @ApiResponse(code = 401, response = ErrorResponse.class, message =
	 * "Invalid or expired token provided. Error code: CREDENTIALS_REJECTED"),
	 * 
	 * @ApiResponse(code = 500, response = ErrorResponse.class, message =
	 * "Runtime server error occurred. Error code: SERVER_ERROR")})
	 */
	public List<OrderResponse> getOrders() {
		log.info("hit the controller");
		return orderService.findAll();
	}

	/**
	 * end-point for the order identified by the unique reference id
	 * 
	 * @param refId
	 *            the unique reference id of the order
	 * @return the TO containing the information about the order
	 * @throws NotFoundException
	 *             if the order with the given reference id is not found in the
	 *             repository
	 */
	@GetMapping(value = "/{refId}", produces = MediaType.APPLICATION_JSON_VALUE)
	/*
	 * @ApiOperation(value = "Get the order according to specification", notes =
	 * "Send GET request to get the order with the given reference id" , httpMethod
	 * = "GET", code = 200, response = OrderResponse.class, produces =
	 * MediaType.APPLICATION_JSON_VALUE)
	 * 
	 * @ApiResponses(value = {
	 * 
	 * @ApiResponse(code = 200, response = OrderResponse.class, message =
	 * "List of all groups info"),
	 * 
	 * @ApiResponse(code = 401, response = ErrorResponse.class, message =
	 * "Invalid or expired token provided. Error code: CREDENTIALS_REJECTED"),
	 * 
	 * @ApiResponse(code = 500, response = ErrorResponse.class, message =
	 * "Runtime server error occurred. Error code: SERVER_ERROR")})
	 */
	public OrderResponse getOrder(@NotBlank @PathVariable(name = "refId", required = true) String refId)
			throws NotFoundException {
		log.info("hit the controller");
		List<OrderResponse> orders = orderService.findById(refId);
		if (orders.size() == 0) {
			throw new NotFoundException("ORDER_NOT_FOUND", "Could not find the order " + refId);
		}
		return orders.get(0);
	}
	
	/**
	 * creates the order for the given customer
	 * 
	 * @param customerRefId
	 *            the unique reference id of the customer
	 * @param orderRequest
	 *            the TO containing the information about the order
	 * @param result
	 *            the result of the request object binding
	 * @param m
	 *            the model
	 * @return the TO containing the information about the created order
	 * @throws BadRequestException
	 *             if the order request TO did not contain all the necessary data
	 * @throws InsufficientPermissionsException
	 *             if the authenticated user is not allowed to create the specified
	 *             order
	 * @throws NotFoundException 
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	/*
	 * @ApiOperation(value = "Order created according to the sent specification",
	 * notes =
	 * "Send POST request to create the order according to the specification." ,
	 * httpMethod = "GET", code = 200, response = OrderResponse.class, produces =
	 * MediaType.APPLICATION_JSON_VALUE)
	 * 
	 * @ApiResponses(value = {
	 * 
	 * @ApiResponse(code = 200, response = OrderResponse.class, message =
	 * "Created order"),
	 * 
	 * @ApiResponse(code = 401, response = ErrorResponse.class, message =
	 * "Invalid or expired token provided. Error code: CREDENTIALS_REJECTED"),
	 * 
	 * @ApiResponse(code = 500, response = ErrorResponse.class, message =
	 * "Runtime server error occurred. Error code: SERVER_ERROR")})
	 */
	public OrderResponse createOrder(@Valid @RequestBody OrderRequest orderRequest, BindingResult result, Model m)
			throws BadRequestException, InsufficientPermissionsException, NotFoundException {
		log.info("hit the controller");
		if (result.hasErrors()) {
			log.info("not able to handle error");
			StringBuilder builder = new StringBuilder();
			result.getAllErrors().forEach(error -> builder.append(error.toString()));
			throw new BadRequestException("BAD_REQUEST", builder.toString());
		}
		log.info("orderService doesn't create order");
		Customer customer = customerService.findOne(orderRequest.getCustomerEmail());
		return orderService.createOrder(orderRequest, customer.getRefId());
	}


}
