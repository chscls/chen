package com;

import java.io.File;

import javax.servlet.MultipartConfigElement;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.zhitail.frame.util.jpa.BaseRepositoryFactoryBean;
import com.zhitail.test.SnowflakeIdWorker;


@EnableEurekaServer
@EnableZuulProxy
/*########################################*/
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class Application {
	@Autowired
	private Environment env;
	private static long datacenterId = 0L;
	private static long workerId = 0L;
	private static Integer port=8080;
	private static SnowflakeIdWorker instance;
	public static void main(String[] args) {
		if(args!=null&&args.length>0) {
			port=Integer.parseInt(args[0]);
			datacenterId=Long.parseLong(args[1]);
			workerId= Long.parseLong(args[2]);
		}
			SpringApplication.run(Application.class, args);
	
	}
	 public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {  
	  
	        configurableEmbeddedServletContainer.setPort(port);  
	    }  
	 public static SnowflakeIdWorker getSnowflakeIdWorker(){
		 if(instance!=null) {
			 return instance;
		 }else {
		 return new SnowflakeIdWorker(workerId,datacenterId);
		 }
		 
	 }
	 
	 @Bean  
	    public MultipartConfigElement multipartConfigElement() {  
	        MultipartConfigFactory factory = new MultipartConfigFactory();  
	        factory.setMaxFileSize("10000MB");  
	        factory.setMaxRequestSize("10000MB");  
	        return factory.createMultipartConfig();  
	    }  
	 
	 
	 @Bean
	    public EmbeddedServletContainerFactory servletContainer() {
	        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
	        tomcat.addAdditionalTomcatConnectors(createSslConnector()); // 添加http
	        return tomcat;
	    }

	 private Connector createSslConnector() {
	        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
	        String x = env.getProperty("https.file");
	        String y = env.getProperty("https.password");
	        String p = env.getProperty("https.port");
	            File keystore = new File(x);
	            /*File truststore = new ClassPathResource("sample.jks").getFile();*/
	            connector.setScheme("https");
	            connector.setSecure(true);
	            connector.setPort(Integer.parseInt(p));
	            protocol.setSSLEnabled(true);
	            protocol.setKeystoreFile(keystore.getAbsolutePath());
	            protocol.setKeystorePass(y);
	           // protocol.setKeyPass(key_password);
	            return connector;
	      
	     
	    }
	 
}
