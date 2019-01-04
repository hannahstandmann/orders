package org.smbaiwsy;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
/**
 * The main application
 * @author anamattuzzi-stojanovic
 *
 */
@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
	
	@Bean
	@Qualifier("sessionMapping")
	public Map<String,String> sessionMapping(){
		return new HashMap<String,String>();
	}

}

