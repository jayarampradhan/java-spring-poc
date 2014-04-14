/**
 * 
 */
package com.test.restpoc.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

/**
 * @author pradhj
 * 
 */
@Path("/")
@Component
public class TestController {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getHello() {
		return String.format("Hello world");
	}

}
