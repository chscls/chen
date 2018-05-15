/**
 * @Title: GlobalExceptionHandler.java
 * @Package com.zysoft.config
 * @Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:成都市知用科技有限公司
 * 
 * @author huangshun
 * @date 2017年11月22日 下午1:01:01
 * @version V1.0
 */
package com.zhitail.frame.common.aop;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhitail.frame.common.exception.BusinessException;
import com.zhitail.frame.common.exception.TokenAuthException;
import com.zhitail.frame.util.service.Result;




/**
 * 全局异常处理类
 * @ClassName: GlobalExceptionHandler
 * @Description: TODO
 * @author huangshun
 * @date 2017年11月22日 下午1:01:01
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
     * 所有异常报错
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value=Exception.class)  
    @ResponseBody
    public Result<Map<String, Object>> allExceptionHandler(HttpServletRequest request,  
            Exception exception) throws Exception  
    {  
    	Map<String, Object> data=new LinkedHashMap<String, Object>();
    	data.put("errcode", "");
    	data.put("errmsg", exception.getMessage());
        exception.printStackTrace();
        Result<Map<String, Object>> rs=new Result<Map<String, Object>>(data,HttpStatus.ACCEPTED);
        return rs;  
    }  
    
    /**
     * 身份认证授权异常报错
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value=TokenAuthException.class)  
    @ResponseBody
    public Result<Map<String, Object>> unToken(HttpServletRequest request,  
    		TokenAuthException exception) throws Exception  
    {  
    	
        return exception.getResult();  
    }  
    
    /**
     * 业务异常报错
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value=BusinessException.class)  
    @ResponseBody
    public Result<Map<String, Object>> businessExceptionHandler(HttpServletRequest request,  
    		BusinessException exception) throws Exception  
    {  
        Result<Map<String, Object>> rs=exception.getResult();
        return rs;  
    }
    
    
}
