package com.zhitail.frame.util.jpa;


import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;


/**
*
* @author Dayu <yulm@sortec.com.cn>
*/
@WebFilter(filterName="myFilter",urlPatterns="/*")
@Configuration
public class MyOpenSessionFilter implements Filter {
   
   private final OpenEntityManagerInViewFilter filter;

   public MyOpenSessionFilter() {
       filter = new OpenEntityManagerInViewFilter();
       filter.setEntityManagerFactoryBeanName("entityManagerFactory");
       
      // .setSessionFactoryBeanName("sessionFactory_soc");
       
   }

   

   public void init(FilterConfig filterConfig) throws ServletException {
       filter.init(filterConfig);
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       filter.doFilter(request, response, chain);
   }

   public void destroy() {
       filter.destroy();
   }
   
}