package org.smbaiwsy.database.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The product entity created according to the given specification
 * 
 * @author anamattuzzi-stojanovic
 *
 */
@NoArgsConstructor
@Data
@Entity
@Table(name = "Product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "ref_id", unique= true)
	private String refId;
	@Column(name = "name")
	private String name;
	@Column(name = "price")
	private BigDecimal price;
	/**
	 * overloaded constructor
	 * @param refId the unique reference id
	 * @param name the product name
	 * @param price the price of a product
	 */
	public Product(String refId, String name, BigDecimal price) {
		this.refId = refId;
		this.name = name;
		this.price = price;	
	}
}
