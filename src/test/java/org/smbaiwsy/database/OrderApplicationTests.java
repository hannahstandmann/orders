package org.smbaiwsy.database;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.smbaiwsy.database.entity.Customer;
import org.smbaiwsy.database.entity.Order;
import org.smbaiwsy.database.entity.Product;
import org.smbaiwsy.database.repository.CustomerRepository;
import org.smbaiwsy.database.repository.OrderRepository;
import org.smbaiwsy.database.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = { "org.smbaiwsy.database" })
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class OrderApplicationTests {

	@Autowired
    private CustomerRepository customers;
	
	@Autowired
    private ProductRepository products;
	
	@Autowired
    private OrderRepository orders;
	
	@Test
	public void contextLoads() {
	}

    @Test
    public void testFindCustomerByEMail() {
        Customer customer = new Customer(UUID.randomUUID().toString(),"ana.mattuzzi@me.com","Mihajla Pupina 103/3", "password");
        customers.save(customer);

        Customer findByEmail = customers.findByEMail(customer.getEMail()).get();

        assertThat(findByEmail).extracting(Customer::getEMail).isEqualTo(customer.getEMail());
    }
    
    @Test
    public void testFindCustomerByReferenceId() {
    	    
        Customer customer = new Customer(UUID.randomUUID().toString(),"ana.mattuzzi@me.com","Mihajla Pupina 103/3", "password");
        customers.save(customer);

        Customer findByRefId = customers.findByRefId(customer.getRefId()).get();

        assertThat(findByRefId).extracting(Customer::getRefId).isEqualTo(customer.getRefId());
    }
    
    @Test
    public void testFindProductByReferenceId() {
        Product product = new Product(UUID.randomUUID().toString(),"O Bag",BigDecimal.valueOf(13000));
        products.save(product);

        Product findByRefId = products.findByRefId(product.getRefId()).get();

        assertThat(findByRefId).extracting(Product::getRefId).isEqualTo(product.getRefId());
    }
    
    @Test
    public void testFindOrderByReferenceId() {
    	    Customer customer = new Customer(UUID.randomUUID().toString(),"ana.mattuzzi@me.com","Mihajla Pupina 103/3", "password");
        customers.save(customer);
        Order order = new Order(UUID.randomUUID().toString(), customer ,"new");
        orders.save(order);

        Order findByRefId = orders.findByRefId(order.getRefId()).get();

        assertThat(findByRefId).extracting(Order::getRefId).isEqualTo(order.getRefId());
    }
    
    @Test
    public void testFindOrderByReferenceIdAndCustomerReferenceId() {
    	    Customer customer = new Customer(UUID.randomUUID().toString(),"ana.mattuzzi@me.com","Mihajla Pupina 103/3", "password");
        customers.save(customer);
        Order order = new Order(UUID.randomUUID().toString(), customer ,"new");
        orders.save(order);

        Order findByRefId = orders.findByCustomerRefId(customer.getRefId(), order.getRefId()).get();

        assertThat(findByRefId).extracting(Order::getRefId).isEqualTo(order.getRefId());
    }
}

