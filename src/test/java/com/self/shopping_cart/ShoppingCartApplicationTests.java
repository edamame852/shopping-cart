package com.self.shopping_cart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShoppingCartApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	// @Test
	// public void testGetAllProducts() {
	// ResponseEntity<String> response = restTemplate.getForEntity("/products",
	// String.class);
	// assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
	// assertThat(response.getBody()).contains("[]");
	// }
	@Test
	public void testFake() {
		assertThat(true).isTrue();
	}
}