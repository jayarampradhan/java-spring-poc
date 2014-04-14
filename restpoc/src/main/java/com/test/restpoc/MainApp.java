package com.test.restpoc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.test.restpoc" })
@Import({
	WebAppInitializer.class,
})
public class MainApp{

private static final Logger log = LoggerFactory.getLogger(MainApp.class);
	
	@Value("${test.tomcat.port:8080}")
	private int tomcatPort;
	
	@Value("${test.tomcat.sessionTimeout:30}")
	private int tomcatSessionTimeout;
	
	@Value("${test.tomcat.contextPath:/bootpoc}/${application.id:jerssey}")
	private String contextPath;

	@Bean
	public ServerProperties serverProperties() {
		ServerProperties serverProperties = new ServerProperties();
		serverProperties.setSessionTimeout(tomcatSessionTimeout);
		serverProperties.setContextPath(contextPath);
		serverProperties.setPort(tomcatPort);
		return serverProperties;
	}
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		tomcat.addAdditionalTomcatConnectors(createSslConnector());
		return tomcat;
	}
	
	private Connector createSslConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
		try {
			File keystore = getKeyStoreFile();
			File truststore = keystore;
			connector.setScheme("https");
			connector.setSecure(true);
			connector.setPort(8443);
			protocol.setSSLEnabled(true);
			protocol.setKeystoreFile(keystore.getAbsolutePath());
			protocol.setKeystorePass("changeit");
			protocol.setTruststoreFile(truststore.getAbsolutePath());
			protocol.setTruststorePass("changeit");
			protocol.setKeyAlias("apitester");
			return connector;
		}
		catch (IOException ex) {
			throw new IllegalStateException("cant access keystore: [" + "keystore"
					+ "] or truststore: [" + "keystore" + "]", ex);
		}
	}

	private File getKeyStoreFile() throws IOException {
		ClassPathResource resource = new ClassPathResource("keystore");
		try {
			return resource.getFile();
		}
		catch (Exception ex) {
			File temp = File.createTempFile("keystore", ".tmp");
			FileCopyUtils.copy(resource.getInputStream(), new FileOutputStream(temp));
			return temp;
		}
	}
	
	
	/**
	 * Application entry point.
	 * @param args command line args
	 */
	public static void main(final String[] args) {
	    log.info("CmdLine Args {}", Arrays.asList(args));
	    try {
			SpringApplication sa = new SpringApplication(new Object[] {  
					MainApp.class 
			});
			sa.run(args);
	    } 
	    catch (Exception e) {
	    	log.error("Unexpected error", e);
	    }
	}

}
