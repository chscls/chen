/**
 * 
 */
package com.zhitail.frame.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author huangshun
 *
 */
public class BusinessException extends TokenAuthException{

	public BusinessException(String errcode, String errmsg, HttpStatus status) {
		super(errcode, errmsg, status);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
