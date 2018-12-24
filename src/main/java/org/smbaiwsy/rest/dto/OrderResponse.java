package org.smbaiwsy.rest.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Transfer object for the order response
 * @author anamattuzzi-stojanovic
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

	/**
	 * Unique refId of the order
	 **/

	private String refId;

	/**
	 * Customer email
	 **/
	// @ApiModelProperty(value = "customer email", required = true, dataType =
	// "String", example = "ana.mattuzzi@gmail.com", position = 1)
	private String customerEmail;

	/**
	 * Order status
	 **/
	// @ApiModelProperty(value = "order status", required = true, dataType =
	// "String", example = "new", position = 2)
	private String status;

	/**
	 * line items
	 **/
	// @ApiModelProperty(value = "list of order line items", required = true,
	// dataType = "List", example = "PRODUCT1, PRODUCT2, PRODUCT3", position = 3)
	private List<OrderLineDTO> lineItems;

}
