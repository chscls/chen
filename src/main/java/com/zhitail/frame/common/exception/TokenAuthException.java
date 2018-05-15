package com.zhitail.frame.common.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.zhitail.frame.util.service.Result;



/**
  *  token认证授权异常的封装
  * @ClassName: TokenAuthException
  * @Description: TODO
  * @author huangshun
  * @date 2017年11月22日 下午3:16:22
  *
 */
@SuppressWarnings("serial")
public class TokenAuthException extends RuntimeException{

	private String errcode;
	private String errmsg;
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	/**
	 * 返回数据
	 */
	private Result<Map<String,Object>> result;
	
	public TokenAuthException(String errcode, String errmsg,HttpStatus status){
		this.errmsg=errmsg;
		Map<String,Object> data=init();
		result=new Result<Map<String,Object>>(data,status);
	}

	public Result<Map<String,Object>> getResult() {
		return result;
	}
	
	private Map<String,Object> init(){
		Map<String,Object> data=new LinkedHashMap<String,Object>();
		data.put("errcode", this.errcode!=null?this.errcode:"");
		data.put("errmsg", this.errmsg!=null?this.errmsg:"");
		return data;
	}
	
	
	
	
}
