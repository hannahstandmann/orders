package org.smbaiwsy.database.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * The order entity created according to the given specification
 * @author anamattuzzi-stojanovic
 *
 */
@NoArgsConstructor
@Data
@Entity
@Table(name = "Order_Order")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "ref_id")
	private String refId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	@Column(name = "order_status_id")
	private String orderStatusId;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderLine>  lineItems = new HashSet<OrderLine>();
	/**
	 * overloaded constructor
	 */
	public Order(String refId, Customer customer, String orderStatus) {
		this.refId = refId;
		this.customer = customer;
		this.orderStatusId = orderStatus;		
	}
}
