package com.example.restapitutorial;

import com.example.restapitutorial.products.Product;
import com.example.restapitutorial.products.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RestApiTutorialApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	private static final String productName = "Shoe";

	@BeforeEach
	public void addProductsToDatabase() {
		Product product = new Product();
		product.setName(productName);
		product.setCostInEuro(30);

		productRepository.save(product);
	}

	@AfterEach
	public void deleteProductsFromDatabase() {
		productRepository.deleteAll();
	}

	@Test
	void contextLoads() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Product> productFromApi
				= restTemplate.getForEntity("http://localhost:8080/products/1", Product.class);

		Assertions.assertNotEquals(productFromApi.getBody(),  null);
		Assertions.assertEquals(Objects.requireNonNull(productFromApi.getBody()).getName(), productName);
	}

}
