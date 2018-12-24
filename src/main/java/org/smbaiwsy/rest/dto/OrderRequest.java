package org.smbaiwsy.rest.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transfer object for Order Request
 * 
 * @author anamattuzzi-stojanovic
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

	// @ApiModelProperty(value = "Customer emai;.", required = true, dataType =
	// "String", example = "ana.mattuzzi@gmail.com", position = 1)
	@NotNull
	private String customerEmail;
	/**
	 * Order Status
	 **/
	// @ApiModelProperty(value = "status of the order", required = true,
	// dataType = "String", example = "new", position = 2)
	@NotNull
	private String orderStatus;

	/**
	 * line items
	 **/
	// @ApiModelProperty(value = "line items", required = true,
	// dataType = "List", example = "PRODUCT1, PRODUCT2, PRODUCT3", position = 3)
	@NotNull
	private List<OrderLineDTO> lineItems;

}
