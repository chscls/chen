package com;

import javax.servlet.MultipartConfigElement;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.zhitail.frame.util.jpa.BaseRepositoryFactoryBean;




@SpringBootApplication
@ServletComponentScan
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class Application {
	 @Value("${http.port}")
	    private Integer port;
	public static void main(String[] args) {
			SpringApplication.run(Application.class, args);
	
	}
	 private Connector createStandardConnector() {
	        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	        connector.setPort(port);
	        return connector;
	    }
	 @Bean
	    public EmbeddedServletContainerFactory servletContainer() {
	        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
	        tomcat.addAdditionalTomcatConnectors(createStandardConnector()); // 添加http
	        return tomcat;
	    }
	 @Bean  
	    public MultipartConfigElement multipartConfigElement() {  
	        MultipartConfigFactory factory = new MultipartConfigFactory();  
	        factory.setMaxFileSize("10000MB");  
	        factory.setMaxRequestSize("10000MB");  
	        return factory.createMultipartConfig();  
	    }  
}
