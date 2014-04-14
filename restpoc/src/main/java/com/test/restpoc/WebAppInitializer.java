package com.test.restpoc;

import java.util.HashMap;
import java.util.Map;

import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Configures the Jerseey servlet container for the context uri path
 * @author Jayaram
 *
 */
@Configuration
@ComponentScan
public class WebAppInitializer extends SpringBootServletInitializer {
	
	/*@Override  
    public void onStartup(ServletContext container) throws ServletException {  
        XmlWebApplicationContext rootContext = new XmlWebApplicationContext();  
        rootContext.setConfigLocations(new String[] { "classpath*:applicationContext.xml" });  
        container.addListener(new ContextLoaderListener(rootContext));  
  
        ServletRegistration.Dynamic dispatcher = container.addServlet("restServlet", ServletContainer.class);
        dispatcher.setInitParameter("javax.ws.rs.Application", "com.test.restpoc.controller.JerssyApplication");
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");  
    } */
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebAppInitializer.class);
    }

    @Bean
    public ServletContainer dispatcherServlet() {
        return new ServletContainer();
    }

    @Bean
    public ServletRegistrationBean dispatcherServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet());
        Map<String,String> params = new HashMap<String,String>();
        params.put("javax.ws.rs.Application","com.test.restpoc.controller.JerssyApplication");
        registration.setInitParameters(params);
        registration.addUrlMappings("/*");
        return registration;
    }
}
