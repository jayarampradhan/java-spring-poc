package com.test.restpoc.controller;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class JerssyApplication extends ResourceConfig{

	public JerssyApplication(){
		register(TestController.class);
		register(RequestContextFilter.class);
		
	}
}
