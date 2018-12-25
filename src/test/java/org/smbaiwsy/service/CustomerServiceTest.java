package org.smbaiwsy.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.smbaiwsy.database.entity.Customer;
import org.smbaiwsy.database.repository.CustomerRepository;
import org.smbaiwsy.exception.NotFoundException;
import org.smbaiwsy.rest.dto.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = { "org.smbaiwsy.database" })
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class CustomerServiceTest {

	@MockBean
	private CustomerRepository customerRepository;

	@MockBean
	private RestTemplate restTemplate;

	@Autowired
	CustomerService customerService;

	@Test
	public void findAll() {

		Customer customer1 = new Customer(UUID.randomUUID().toString(), "ana.mattuzzi@me.com", "Mihajla Pupina 103/3",
				"password");
		Customer customer2 = new Customer(UUID.randomUUID().toString(), "ana.mattuzzi@gmail.com",
				"Mihajla Pupina 103/3", "password");
		Customer customer3 = new Customer(UUID.randomUUID().toString(), "hannah.standmann@gmmx.net",
				"Mihajla Pupina 103/3", "password");
		List<Customer> customers = new ArrayList<Customer>() {
			private static final long serialVersionUID = -8922221727427983359L;

			{
				add(customer1);
				add(customer2);
				add(customer3);
			}
		};
		Mockito.when(customerRepository.findAll()).thenReturn(customers);
		List<CustomerResponse> response = customerService.findAll();
		Assert.assertEquals("Number of customers as expected", 3, response.size());
		Assert.assertEquals("First user", "ana.mattuzzi@me.com", response.get(0).getEmail());
		Assert.assertEquals("Second user", "ana.mattuzzi@gmail.com", response.get(1).getEmail());
		Assert.assertEquals("Third user", "hannah.standmann@gmmx.net", response.get(2).getEmail());
	}

	@Test
	public void findOneSuccess() throws NotFoundException {

		Customer customer1 = new Customer(UUID.randomUUID().toString(), "ana.mattuzzi@me.com", "Mihajla Pupina 103/3",
				"password");

		Mockito.when(customerRepository.findByEMail("ana.mattuzzi@me.com")).thenReturn(Optional.of(customer1));
		Customer response = customerService.findOne("ana.mattuzzi@me.com");
		Assert.assertEquals("Found customer", customer1.getRefId(), response.getRefId());

	}

	@Test
	public void loadUserByUsernameSuccess() throws UsernameNotFoundException {

		Customer customer1 = new Customer(UUID.randomUUID().toString(), "ana.mattuzzi@me.com", "Mihajla Pupina 103/3",
				"password");

		Mockito.when(customerRepository.findByEMail("ana.mattuzzi@me.com")).thenReturn(Optional.of(customer1));
		UserDetails response = customerService.loadUserByUsername("ana.mattuzzi@me.com");
		Assert.assertEquals("Found customer username", customer1.getEMail(), response.getUsername());
		Assert.assertEquals("Found customer password", customer1.getPassword(), response.getPassword());

	}

	@Test
	public void findOneFailed() {
		try {
			customerService.findOne("ana.mattuzzi@gmail.com");
			Assert.fail("Expected exception to be thrown");
		} catch (NotFoundException e) {
			assertThat(e).isInstanceOf(NotFoundException.class)
					.hasMessage("No user exists with the indicated e-mail address");
		}
	}

	@Test
	public void loadUserByUsernameFailed() {
		try {
			customerService.loadUserByUsername("ana.mattuzzi@gmail.com");
			Assert.fail("Expected exception to be thrown");
		} catch (UsernameNotFoundException e) {
			assertThat(e).isInstanceOf(UsernameNotFoundException.class).hasMessage("Invalid username or password.");
		}
	}

}
