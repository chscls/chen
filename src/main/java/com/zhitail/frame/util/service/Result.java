package com.zhitail.frame.util.service;

import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.zhitail.frame.common.exception.BusinessException;




public class Result<T> extends ResponseEntity<T>{
	public Result(HttpStatus status) {
		super(status);
		// TODO Auto-generated constructor stub
	}
	public Result(T body) {
		super(body,HttpStatus.OK);
	}
	


	public Result(T body, HttpStatus status) {
		super(body, null, status);
	}

	public Result(MultiValueMap<String, String> headers,
			HttpStatus status) {
		super(null, headers, status);
	}
	
	
	public static Result<String> error(String errorMsg){
		throw new BusinessException(null, errorMsg, HttpStatus.ACCEPTED);
		
	}

	


	
}
