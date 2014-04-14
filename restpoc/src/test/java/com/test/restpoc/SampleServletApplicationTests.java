package com.test.restpoc;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainApp.class)
@WebAppConfiguration
@IntegrationTest
@DirtiesContext
public class SampleServletApplicationTests{
	
	@Test
	public void testHome() throws Exception {
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
				"http://localhost:8080/bootpoc/jerssey/", String.class);
		Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
		Assert.assertEquals("Hello world", entity.getBody());
	}
	
	@Test
	public void testHomeWithSsl() throws Exception {
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
				"https://127.0.0.1:8443/bootpoc/jerssey/", String.class);
		Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
		Assert.assertEquals("Hello world", entity.getBody());
	}
	
}
