/**
 * @Title: TokenAuth.java
 * @Package com.zysoft.aop.annotion
 * @Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:成都市知用科技有限公司
 * 
 * @author huangshun
 * @date 2017年11月22日 下午1:45:55
 * @version V1.0
 */
package com.zhitail.frame.common.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口认证授权注解
 * @ClassName: TokenAuth
 * @Description: TODO
 * @author huangshun
 * @date 2017年11月22日 下午1:45:55
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TokenAuth {
	
	String value() default "token";
}
