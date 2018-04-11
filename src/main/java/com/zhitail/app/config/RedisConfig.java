package com.zhitail.app.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.Method;


@Configuration  
@EnableCaching  
public class RedisConfig extends CachingConfigurerSupport{  
	@Autowired
	private Environment env;
    @Bean  
    public KeyGenerator wiselyKeyGenerator(){  
        return new KeyGenerator() {

			public Object generate(Object arg0, Method arg1, Object... arg2) {
				// TODO Auto-generated method stub
			  StringBuilder sb = new StringBuilder();  
                sb.append(arg0.getClass().getName());  
                sb.append(arg1.getName());  
                for (Object obj : arg2) {  
                    sb.append(obj!=null?obj.toString():"null");  
                }  
                return sb.toString();  
			}  
       
        };  
  
    }  
  
    @Bean  
    public CacheManager cacheManager(  
            @SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {  
        return new RedisCacheManager(redisTemplate);  
    }  
  
    @Bean  
    public RedisTemplate<String, String> redisTemplate(  
            RedisConnectionFactory factory) {  
        StringRedisTemplate template = new StringRedisTemplate(factory);  
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);  
        ObjectMapper om = new ObjectMapper();  
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);  
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);  
        jackson2JsonRedisSerializer.setObjectMapper(om);  
        template.setValueSerializer(jackson2JsonRedisSerializer);  
        template.afterPropertiesSet();  
        return template;  
    }  
    


	
}  