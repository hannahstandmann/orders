package org.smbaiwsy.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.smbaiwsy.AppConstants;
import org.smbaiwsy.exception.NotFoundException;
import org.smbaiwsy.rest.dto.OrderResponse;
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

}
