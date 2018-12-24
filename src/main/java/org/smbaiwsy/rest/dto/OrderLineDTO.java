package org.smbaiwsy.rest.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Transfer object containing the order line information 
 * @author anamattuzzi-stojanovic
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineDTO {
	/**
	 * name of the product
	 */
	private String name;
	/**
	 * unique reference id of the product
	 */
	@NotNull
	private String refId;
	/**
	 * the quantity of bought products
	 */
	@NotNull
	private Integer quantity;
	/**
	 * price of the product
	 */
	private BigDecimal price;

}
