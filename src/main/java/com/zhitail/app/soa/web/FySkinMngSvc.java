package com.zhitail.app.soa.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FySkin;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.middle.AntUser;
import com.zhitail.app.manager.FySkinMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.common.annotion.TokenAuth;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;

@RequestMapping("/services/FySkinMngSvc")
@RestController
public class FySkinMngSvc {
	@Value("${com.zhitail.upload.imgServer}")
	private String imgServer;
	@Autowired
	private FySkinMng beanMng;

	@TokenAuth(value = "token")
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public Result query(String token, Integer pageNo, Integer pageSize, FySkin search) {
		Pagination<FySkin> page = beanMng.getPage(pageNo, pageSize, search);
		for (FySkin q : page.getList()) {

			q.fullImg(imgServer);

		}
		return new Result(page);
	}

	@TokenAuth(value = "token")
	@RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result add(String token, FySkin bean) {
		if (bean.getId() == null) {

			bean.init();
			bean = beanMng.save(bean);
		} else {
			bean = beanMng.update(bean);
		}
		return new Result(bean);

	}

	@TokenAuth(value = "token")
	@RequestMapping(value = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result remove(String token, Long[] ids) {
		beanMng.delete(ids);
		return new Result(true);
	}
}
