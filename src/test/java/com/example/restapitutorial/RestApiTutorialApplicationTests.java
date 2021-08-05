package com.example.restapitutorial;

import com.example.restapitutorial.products.Product;
import com.example.restapitutorial.products.ProductRepository;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RestApiTutorialApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	private static final String productName = "Shoe";

	@BeforeEach
	public void addProductsToDb() {
		Product product = new Product();
		product.setName(productName);
		product.setCostInEuro(23);

		productRepository.save(product);
	}

	@AfterEach
	public void clearProductDb() {
		productRepository.deleteAll();
	}

	@Test
	void testGetRequest() {
		TestRestTemplate restTemplate = new TestRestTemplate();


		ResponseEntity<Product> productEntity
				= restTemplate.getForEntity("http://localhost:8080/products/1", Product.class);
		Assertions.assertEquals(productEntity.getStatusCode(), HttpStatus.OK);
		Assertions.assertNotNull(productEntity.getBody());
		Assertions.assertEquals(productEntity.getBody().getName(), productName);
	}

}
