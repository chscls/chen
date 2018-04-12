package com.zhitail.app.soa.wx;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bytebuddy.jar.asm.commons.TryCatchBlockSorter;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.mysql.fabric.xmlrpc.base.Array;
import com.zhitail.app.soa.LoginManager;

/**
 * 3.11.用户相关接口-账号相关接口定义
 * @ClassName: AccoutService
 * @Description: TODO
 * @author huangshunfindDict
 * @date 2018年4月2日 下午1:31:40
 *
*/

@RequestMapping("/services/FyUserSvc")
@RestController
public class FyUserSvc {
	@Autowired
	private LoginManager loginManager;

	
	
}
