package org.smbaiwsy.database.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * The customer entity created according to the given specification
 * @author anamattuzzi-stojanovic
 *
 */
@NoArgsConstructor
@Data
@Entity
@Table(name = "Customer")
public class Customer {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Integer id;
	@Column(name = "ref_id")
	private String refId;
	@Column(name = "email")
	private String eMail;
	@Column(name = "address")
	private String address;
	@Column(name = "password")
	private String password;
	/**
	 * constructor for creating the customer from specified information
	 * @param randomUUID the unique reference id 
	 * @param email user e-mail
	 * @param address the address of the user
	 * @param password the encoded password
	 */
	public Customer(String randomUUID, String email, String address, String password) {
		this.refId = randomUUID;
		this.eMail = email;
		this.address = address;
		this.password = password;
	}

}
