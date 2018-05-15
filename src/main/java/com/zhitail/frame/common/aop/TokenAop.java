/**
 * @Title: TokenAop.java
 * @Package com.zysoft.aop
 * @Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:成都市知用科技有限公司
 * 
 * @author huangshun
 * @date 2017年11月22日 下午1:49:00
 * @version V1.0
 */
package com.zhitail.frame.common.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.common.annotion.TokenAuth;
import com.zhitail.frame.common.exception.TokenAuthException;


/**
 * token认证授权AOP
 * 
 * @ClassName: TokenAop
 * @Description: TODO
 * @author huangshun
 * @date 2017年11月22日 下午1:49:00
 *
 */
@Aspect
@Component
public class TokenAop {

	@Autowired
	private LoginManager loginManager;
	
	@Pointcut(value = "@annotation(com.zhitail.frame.common.annotion.TokenAuth)")
	private void cutToken() {

	}

	@Around("cutToken()")
	public Object doToken(ProceedingJoinPoint point) throws Throwable {
		MethodSignature ms = (MethodSignature) point.getSignature();
		Method method = ms.getMethod();
		TokenAuth tokenAuth = method.getAnnotation(TokenAuth.class);
		String tokenParam = tokenAuth.value();
		String tokenValue=null;
		Boolean isExists=false;
		if(tokenParam==null || tokenParam==""){
			  return point.proceed();
		}
		 //获取拦截方法的参数
        String className = point.getTarget().getClass().getName();
        String[] paramNames = ms.getParameterNames();  //jdk1.8
        Object[] paramValus = point.getArgs();
        if(paramNames.length!=paramValus.length){
        	 return point.proceed();
        }
        //获取参数名是否存在有token
        for(Integer i=0;i<paramNames.length;i++){
        	if(paramNames[i].equals(tokenParam)){
        		tokenValue=(String)paramValus[i];
        		isExists=true;
        		break;
        	}
        }
        if(isExists && !loginManager.verify(tokenValue)){
        	 throw new TokenAuthException(null,"登录已过期,请重新登录",HttpStatus.UNAUTHORIZED);
        }else{
        	 return point.proceed();
        }

	}
}
