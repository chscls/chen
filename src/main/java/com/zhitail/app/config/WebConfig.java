package com.zhitail.app.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhitail.frame.common.upload.FileWebUploader;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;
import java.util.List;
import java.util.Locale;



@EnableWebMvc
@Configuration 
public class WebConfig extends WebMvcConfigurerAdapter {
	
	 
	@Autowired
	private Environment env;

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		  //映射本地资源文件
	
		if(env.getProperty("zyframework.swagger-open")!=null 
				&& env.getProperty("zyframework.swagger-open").equals("true")){
			 registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
			 registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		}
	   
		super.addResourceHandlers(registry);
		
	

	
		String x = env.getProperty("com.zhiyong.resourcePathMap");
	
		if(!StringUtils.isBlank(x)){
			String[] xx=x.split(",");
			String[] ss;
			for(String s:xx){
				ss=s.split("->");
				if(ss[1].contains("classpath")){
					 registry.addResourceHandler( ss[0]).addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+ ss[1].split(":")[1]);  	
				
				}else{
				registry.addResourceHandler(ss[0]).addResourceLocations(
						ResourceUtils.FILE_URL_PREFIX + ss[1]);
				}
				
			}
			
		}
		
		
         super.addResourceHandlers(registry);  
    }  
	
	@Bean
	public MappingJackson2HttpMessageConverter responseBodyConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		  objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		  jsonConverter.setObjectMapper(objectMapper);
		  return jsonConverter;

	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	    super.configureMessageConverters(converters);
	    converters.add(responseBodyConverter());
	}
	
	@Bean(name="fileWebUploader")
	 public FileWebUploader getFileWebUploader(){
		FileWebUploader fileWebUploader=new FileWebUploader();
		 return fileWebUploader;
	 }
}
