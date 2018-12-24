package org.smbaiwsy.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The order line item entity created according to the given specification
 * 
 * @author anamattuzzi-stojanovic
 *
 */
@NoArgsConstructor
@Data
@Entity
@Table(name = "OrderLine")
public class OrderLine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_line_id")
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	@EqualsAndHashCode.Exclude
	private Order order;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;
	@Column(name = "quantity")
	private Integer quantity;

	/**
	 * Constructor for creating the line item from the specified information
	 * 
	 * @param created
	 *            the order to add the item to
	 * @param product
	 *            the product for which the line item is created
	 * @param quantity
	 *            the quantity of the product
	 */
	public OrderLine(Order created, Product product, Integer quantity) {
		this.order = created;
		this.product = product;
		this.quantity = quantity;
	}

}
