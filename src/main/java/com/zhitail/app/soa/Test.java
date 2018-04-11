package com.zhitail.app.soa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhitail.app.entity.TestEn;
@RequestMapping("/test")
@RestController
public class Test {
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	
	public ResponseEntity<List<TestEn> > test(){
		
		List<TestEn> xx=new ArrayList<TestEn>();
		TestEn n = new TestEn();
		n.setXxx("xxxxx");
		n.setYyy(1111111);
		TestEn n2 = new TestEn();
		n2.setXxx("xxxxx222");
	
		xx.add(n);
		xx.add(n2);
		return new ResponseEntity<List<TestEn> >(HttpStatus.PERMANENT_REDIRECT);
		
	}
}
