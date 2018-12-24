package org.smbaiwsy.database;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.smbaiwsy.database.entity.Customer;
import org.smbaiwsy.database.repository.CustomerRepository;
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
//@SpringBootTest
@ComponentScan(basePackages = { "org.smbaiwsy.database" })
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class OrderApplicationTests {

	@Test
	public void contextLoads() {
	}
	@Autowired
    private CustomerRepository customers;

    @Test
    public void testFindByEMail() {
        Customer customer = new Customer(UUID.randomUUID().toString(),"ana.mattuzzi@me.com","Mihajla Pupina 103/3", "password");
        customers.save(customer);

        Customer findByEmail = customers.findByEMail(customer.getEMail()).get();

        assertThat(findByEmail).extracting(Customer::getEMail).isEqualTo(customer.getEMail());
    }
}

