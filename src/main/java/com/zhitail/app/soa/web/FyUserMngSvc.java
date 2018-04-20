package com.zhitail.app.soa.web;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;







import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.middle.AntUser;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;



@RequestMapping("/services/FyUserMngSvc")
@RestController
public class FyUserMngSvc {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FyUserMng userMng;
	@RequestMapping(value = "/account",method=RequestMethod.POST)
	public ResponseEntity<JSONObject> account(String userName,String password) {
	FyUser u = 	userMng.findByUserName(userName);
	if(u.getPassword().equals(password)){
		JSONObject jo = new JSONObject();
		jo.put("status", "ok");
		jo.put("currentAuthority", "admin");
		String token = loginManager.getToken(u);
		jo.put("token", token);
		 return new  ResponseEntity<JSONObject>(jo,HttpStatus.OK);
	}
		JSONObject jo= JSONObject.parseObject("{\"status\":\"error\",\"currentAuthority\":\"user\"}");
		return new  ResponseEntity<JSONObject>(jo,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fake_chart_data",method=RequestMethod.GET)
	public ResponseEntity<JSONObject> fake_chart_data(String token) {
		JSONObject jo= JSONObject.parseObject("{\"visitData\":[{\"x\":\"2018-04-12\",\"y\":7},{\"x\":\"2018-04-13\",\"y\":5},{\"x\":\"2018-04-14\",\"y\":4},{\"x\":\"2018-04-15\",\"y\":2},{\"x\":\"2018-04-16\",\"y\":4},{\"x\":\"2018-04-17\",\"y\":7},{\"x\":\"2018-04-18\",\"y\":5},{\"x\":\"2018-04-19\",\"y\":6},{\"x\":\"2018-04-20\",\"y\":5},{\"x\":\"2018-04-21\",\"y\":9},{\"x\":\"2018-04-22\",\"y\":6},{\"x\":\"2018-04-23\",\"y\":3},{\"x\":\"2018-04-24\",\"y\":1},{\"x\":\"2018-04-25\",\"y\":5},{\"x\":\"2018-04-26\",\"y\":3},{\"x\":\"2018-04-27\",\"y\":6},{\"x\":\"2018-04-28\",\"y\":5}],\"visitData2\":[{\"x\":\"2018-04-12\",\"y\":1},{\"x\":\"2018-04-13\",\"y\":6},{\"x\":\"2018-04-14\",\"y\":4},{\"x\":\"2018-04-15\",\"y\":8},{\"x\":\"2018-04-16\",\"y\":3},{\"x\":\"2018-04-17\",\"y\":7},{\"x\":\"2018-04-18\",\"y\":2}],\"salesData\":[{\"x\":\"1月\",\"y\":805},{\"x\":\"2月\",\"y\":378},{\"x\":\"3月\",\"y\":1074},{\"x\":\"4月\",\"y\":218},{\"x\":\"5月\",\"y\":282},{\"x\":\"6月\",\"y\":699},{\"x\":\"7月\",\"y\":859},{\"x\":\"8月\",\"y\":1071},{\"x\":\"9月\",\"y\":1146},{\"x\":\"10月\",\"y\":1100},{\"x\":\"11月\",\"y\":343},{\"x\":\"12月\",\"y\":456}],\"searchData\":[{\"index\":1,\"keyword\":\"搜索关键词-0\",\"count\":470,\"range\":32,\"status\":1},{\"index\":2,\"keyword\":\"搜索关键词-1\",\"count\":788,\"range\":18,\"status\":1},{\"index\":3,\"keyword\":\"搜索关键词-2\",\"count\":685,\"range\":10,\"status\":0},{\"index\":4,\"keyword\":\"搜索关键词-3\",\"count\":862,\"range\":13,\"status\":0},{\"index\":5,\"keyword\":\"搜索关键词-4\",\"count\":477,\"range\":67,\"status\":1},{\"index\":6,\"keyword\":\"搜索关键词-5\",\"count\":664,\"range\":11,\"status\":1},{\"index\":7,\"keyword\":\"搜索关键词-6\",\"count\":73,\"range\":67,\"status\":0},{\"index\":8,\"keyword\":\"搜索关键词-7\",\"count\":388,\"range\":72,\"status\":0},{\"index\":9,\"keyword\":\"搜索关键词-8\",\"count\":988,\"range\":19,\"status\":1},{\"index\":10,\"keyword\":\"搜索关键词-9\",\"count\":284,\"range\":3,\"status\":0},{\"index\":11,\"keyword\":\"搜索关键词-10\",\"count\":799,\"range\":58,\"status\":0},{\"index\":12,\"keyword\":\"搜索关键词-11\",\"count\":522,\"range\":37,\"status\":0},{\"index\":13,\"keyword\":\"搜索关键词-12\",\"count\":226,\"range\":47,\"status\":0},{\"index\":14,\"keyword\":\"搜索关键词-13\",\"count\":768,\"range\":92,\"status\":1},{\"index\":15,\"keyword\":\"搜索关键词-14\",\"count\":332,\"range\":85,\"status\":1},{\"index\":16,\"keyword\":\"搜索关键词-15\",\"count\":939,\"range\":80,\"status\":0},{\"index\":17,\"keyword\":\"搜索关键词-16\",\"count\":157,\"range\":59,\"status\":0},{\"index\":18,\"keyword\":\"搜索关键词-17\",\"count\":265,\"range\":88,\"status\":0},{\"index\":19,\"keyword\":\"搜索关键词-18\",\"count\":833,\"range\":42,\"status\":1},{\"index\":20,\"keyword\":\"搜索关键词-19\",\"count\":219,\"range\":79,\"status\":0},{\"index\":21,\"keyword\":\"搜索关键词-20\",\"count\":192,\"range\":66,\"status\":0},{\"index\":22,\"keyword\":\"搜索关键词-21\",\"count\":3,\"range\":85,\"status\":0},{\"index\":23,\"keyword\":\"搜索关键词-22\",\"count\":992,\"range\":31,\"status\":0},{\"index\":24,\"keyword\":\"搜索关键词-23\",\"count\":191,\"range\":25,\"status\":0},{\"index\":25,\"keyword\":\"搜索关键词-24\",\"count\":144,\"range\":75,\"status\":1},{\"index\":26,\"keyword\":\"搜索关键词-25\",\"count\":925,\"range\":13,\"status\":1},{\"index\":27,\"keyword\":\"搜索关键词-26\",\"count\":292,\"range\":80,\"status\":0},{\"index\":28,\"keyword\":\"搜索关键词-27\",\"count\":886,\"range\":50,\"status\":0},{\"index\":29,\"keyword\":\"搜索关键词-28\",\"count\":883,\"range\":87,\"status\":0},{\"index\":30,\"keyword\":\"搜索关键词-29\",\"count\":931,\"range\":36,\"status\":1},{\"index\":31,\"keyword\":\"搜索关键词-30\",\"count\":805,\"range\":59,\"status\":1},{\"index\":32,\"keyword\":\"搜索关键词-31\",\"count\":867,\"range\":96,\"status\":0},{\"index\":33,\"keyword\":\"搜索关键词-32\",\"count\":92,\"range\":54,\"status\":1},{\"index\":34,\"keyword\":\"搜索关键词-33\",\"count\":450,\"range\":64,\"status\":1},{\"index\":35,\"keyword\":\"搜索关键词-34\",\"count\":647,\"range\":11,\"status\":0},{\"index\":36,\"keyword\":\"搜索关键词-35\",\"count\":174,\"range\":42,\"status\":1},{\"index\":37,\"keyword\":\"搜索关键词-36\",\"count\":886,\"range\":77,\"status\":0},{\"index\":38,\"keyword\":\"搜索关键词-37\",\"count\":2,\"range\":69,\"status\":1},{\"index\":39,\"keyword\":\"搜索关键词-38\",\"count\":909,\"range\":41,\"status\":0},{\"index\":40,\"keyword\":\"搜索关键词-39\",\"count\":514,\"range\":39,\"status\":1},{\"index\":41,\"keyword\":\"搜索关键词-40\",\"count\":482,\"range\":20,\"status\":0},{\"index\":42,\"keyword\":\"搜索关键词-41\",\"count\":653,\"range\":61,\"status\":0},{\"index\":43,\"keyword\":\"搜索关键词-42\",\"count\":47,\"range\":90,\"status\":1},{\"index\":44,\"keyword\":\"搜索关键词-43\",\"count\":551,\"range\":44,\"status\":0},{\"index\":45,\"keyword\":\"搜索关键词-44\",\"count\":53,\"range\":71,\"status\":0},{\"index\":46,\"keyword\":\"搜索关键词-45\",\"count\":484,\"range\":99,\"status\":1},{\"index\":47,\"keyword\":\"搜索关键词-46\",\"count\":468,\"range\":99,\"status\":0},{\"index\":48,\"keyword\":\"搜索关键词-47\",\"count\":829,\"range\":3,\"status\":0},{\"index\":49,\"keyword\":\"搜索关键词-48\",\"count\":854,\"range\":96,\"status\":0},{\"index\":50,\"keyword\":\"搜索关键词-49\",\"count\":206,\"range\":27,\"status\":0}],\"offlineData\":[{\"name\":\"门店0\",\"cvr\":0.1},{\"name\":\"门店1\",\"cvr\":0.2},{\"name\":\"门店2\",\"cvr\":0.3},{\"name\":\"门店3\",\"cvr\":0.4},{\"name\":\"门店4\",\"cvr\":0.1},{\"name\":\"门店5\",\"cvr\":0.7},{\"name\":\"门店6\",\"cvr\":0.4},{\"name\":\"门店7\",\"cvr\":0.7},{\"name\":\"门店8\",\"cvr\":0.6},{\"name\":\"门店9\",\"cvr\":0.6}],\"offlineChartData\":[{\"x\":1523521591295,\"y1\":99,\"y2\":61},{\"x\":1523523391295,\"y1\":29,\"y2\":25},{\"x\":1523525191295,\"y1\":108,\"y2\":12},{\"x\":1523526991295,\"y1\":107,\"y2\":82},{\"x\":1523528791295,\"y1\":79,\"y2\":40},{\"x\":1523530591295,\"y1\":55,\"y2\":76},{\"x\":1523532391295,\"y1\":17,\"y2\":40},{\"x\":1523534191295,\"y1\":54,\"y2\":104},{\"x\":1523535991295,\"y1\":57,\"y2\":33},{\"x\":1523537791295,\"y1\":62,\"y2\":60},{\"x\":1523539591295,\"y1\":68,\"y2\":20},{\"x\":1523541391295,\"y1\":52,\"y2\":43},{\"x\":1523543191295,\"y1\":22,\"y2\":51},{\"x\":1523544991295,\"y1\":31,\"y2\":90},{\"x\":1523546791295,\"y1\":42,\"y2\":100},{\"x\":1523548591295,\"y1\":47,\"y2\":99},{\"x\":1523550391295,\"y1\":85,\"y2\":75},{\"x\":1523552191295,\"y1\":90,\"y2\":108},{\"x\":1523553991295,\"y1\":11,\"y2\":44},{\"x\":1523555791295,\"y1\":70,\"y2\":53}],\"salesTypeData\":[{\"x\":\"家用电器\",\"y\":4544},{\"x\":\"食用酒水\",\"y\":3321},{\"x\":\"个护健康\",\"y\":3113},{\"x\":\"服饰箱包\",\"y\":2341},{\"x\":\"母婴产品\",\"y\":1231},{\"x\":\"其他\",\"y\":1231}],\"salesTypeDataOnline\":[{\"x\":\"家用电器\",\"y\":244},{\"x\":\"食用酒水\",\"y\":321},{\"x\":\"个护健康\",\"y\":311},{\"x\":\"服饰箱包\",\"y\":41},{\"x\":\"母婴产品\",\"y\":121},{\"x\":\"其他\",\"y\":111}],\"salesTypeDataOffline\":[{\"x\":\"家用电器\",\"y\":99},{\"x\":\"个护健康\",\"y\":188},{\"x\":\"服饰箱包\",\"y\":344},{\"x\":\"母婴产品\",\"y\":255},{\"x\":\"其他\",\"y\":65}],\"radarData\":[{\"name\":\"个人\",\"label\":\"引用\",\"value\":10},{\"name\":\"个人\",\"label\":\"口碑\",\"value\":8},{\"name\":\"个人\",\"label\":\"产量\",\"value\":4},{\"name\":\"个人\",\"label\":\"贡献\",\"value\":5},{\"name\":\"个人\",\"label\":\"热度\",\"value\":7},{\"name\":\"团队\",\"label\":\"引用\",\"value\":3},{\"name\":\"团队\",\"label\":\"口碑\",\"value\":9},{\"name\":\"团队\",\"label\":\"产量\",\"value\":6},{\"name\":\"团队\",\"label\":\"贡献\",\"value\":3},{\"name\":\"团队\",\"label\":\"热度\",\"value\":1},{\"name\":\"部门\",\"label\":\"引用\",\"value\":4},{\"name\":\"部门\",\"label\":\"口碑\",\"value\":1},{\"name\":\"部门\",\"label\":\"产量\",\"value\":6},{\"name\":\"部门\",\"label\":\"贡献\",\"value\":5},{\"name\":\"部门\",\"label\":\"热度\",\"value\":7}]}");
		
		return new  ResponseEntity<JSONObject>(jo,HttpStatus.OK);
	}
	@RequestMapping(value = "/notice",method=RequestMethod.GET)
	public ResponseEntity<JSONArray> notice(String token) {
	JSONArray jo= JSONArray.parseArray("[{\"id\":\"xxx1\",\"title\":\"Alipay\",\"logo\":\"https://gw.alipayobjects.com/zos/rmsportal/WdGqmHpayyMjiEhcKoVE.png\",\"description\":\"那是一种内在的东西，他们到达不了，也无法触及的\",\"updatedAt\":\"2018-04-12T08:26:31.200Z\",\"member\":\"科学搬砖组\",\"href\":\"\",\"memberLink\":\"\"},{\"id\":\"xxx2\",\"title\":\"Angular\",\"logo\":\"https://gw.alipayobjects.com/zos/rmsportal/zOsKZmFRdUtvpqCImOVY.png\",\"description\":\"希望是一个好东西，也许是最好的，好东西是不会消亡的\",\"updatedAt\":\"2017-07-24T00:00:00.000Z\",\"member\":\"全组都是吴彦祖\",\"href\":\"\",\"memberLink\":\"\"},{\"id\":\"xxx3\",\"title\":\"Ant Design\",\"logo\":\"https://gw.alipayobjects.com/zos/rmsportal/dURIMkkrRFpPgTuzkwnB.png\",\"description\":\"城镇中有那么多的酒馆，她却偏偏走进了我的酒馆\",\"updatedAt\":\"2018-04-12T08:26:31.200Z\",\"member\":\"中二少女团\",\"href\":\"\",\"memberLink\":\"\"},{\"id\":\"xxx4\",\"title\":\"Ant Design Pro\",\"logo\":\"https://gw.alipayobjects.com/zos/rmsportal/sfjbOqnsXXJgNCjCzDBL.png\",\"description\":\"那时候我只会想自己想要什么，从不想自己拥有什么\",\"updatedAt\":\"2017-07-23T00:00:00.000Z\",\"member\":\"程序员日常\",\"href\":\"\",\"memberLink\":\"\"},{\"id\":\"xxx5\",\"title\":\"Bootstrap\",\"logo\":\"https://gw.alipayobjects.com/zos/rmsportal/siCrBXXhmvTQGWPNLBow.png\",\"description\":\"凛冬将至\",\"updatedAt\":\"2017-07-23T00:00:00.000Z\",\"member\":\"高逼格设计天团\",\"href\":\"\",\"memberLink\":\"\"},{\"id\":\"xxx6\",\"title\":\"React\",\"logo\":\"https://gw.alipayobjects.com/zos/rmsportal/kZzEzemZyKLKFsojXItE.png\",\"description\":\"生命就像一盒巧克力，结果往往出人意料\",\"updatedAt\":\"2017-07-23T00:00:00.000Z\",\"member\":\"骗你来学计算机\",\"href\":\"\",\"memberLink\":\"\"}]");
			
			return new  ResponseEntity<JSONArray>(jo,HttpStatus.OK);
	}
	@RequestMapping(value = "/activities",method=RequestMethod.GET)
	public ResponseEntity<JSONArray> activities(String token) {
		JSONArray jo= JSONArray.parseArray("[{\"id\":\"trend-1\",\"updatedAt\":\"2018-04-12T08:26:31.200Z\",\"user\":{\"name\":\"曲丽丽\",\"avatar\":\"https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png\"},\"group\":{\"name\":\"高逼格设计天团\",\"link\":\"http://github.com/\"},\"project\":{\"name\":\"六月迭代\",\"link\":\"http://github.com/\"},\"template\":\"在 @{group} 新建项目 @{project}\"},{\"id\":\"trend-2\",\"updatedAt\":\"2018-04-12T08:26:31.200Z\",\"user\":{\"name\":\"付小小\",\"avatar\":\"https://gw.alipayobjects.com/zos/rmsportal/cnrhVkzwxjPwAaCfPbdc.png\"},\"group\":{\"name\":\"高逼格设计天团\",\"link\":\"http://github.com/\"},\"project\":{\"name\":\"六月迭代\",\"link\":\"http://github.com/\"},\"template\":\"在 @{group} 新建项目 @{project}\"},{\"id\":\"trend-3\",\"updatedAt\":\"2018-04-12T08:26:31.201Z\",\"user\":{\"name\":\"林东东\",\"avatar\":\"https://gw.alipayobjects.com/zos/rmsportal/gaOngJwsRYRaVAuXXcmB.png\"},\"group\":{\"name\":\"中二少女团\",\"link\":\"http://github.com/\"},\"project\":{\"name\":\"六月迭代\",\"link\":\"http://github.com/\"},\"template\":\"在 @{group} 新建项目 @{project}\"},{\"id\":\"trend-4\",\"updatedAt\":\"2018-04-12T08:26:31.201Z\",\"user\":{\"name\":\"周星星\",\"avatar\":\"https://gw.alipayobjects.com/zos/rmsportal/WhxKECPNujWoWEFNdnJE.png\"},\"project\":{\"name\":\"5 月日常迭代\",\"link\":\"http://github.com/\"},\"template\":\"将 @{project} 更新至已发布状态\"},{\"id\":\"trend-5\",\"updatedAt\":\"2018-04-12T08:26:31.201Z\",\"user\":{\"name\":\"朱偏右\",\"avatar\":\"https://gw.alipayobjects.com/zos/rmsportal/ubnKSIfAJTxIgXOKlciN.png\"},\"project\":{\"name\":\"工程效能\",\"link\":\"http://github.com/\"},\"comment\":{\"name\":\"留言\",\"link\":\"http://github.com/\"},\"template\":\"在 @{project} 发布了 @{comment}\"},{\"id\":\"trend-6\",\"updatedAt\":\"2018-04-12T08:26:31.201Z\",\"user\":{\"name\":\"乐哥\",\"avatar\":\"https://gw.alipayobjects.com/zos/rmsportal/jZUIxmJycoymBprLOUbT.png\"},\"group\":{\"name\":\"程序员日常\",\"link\":\"http://github.com/\"},\"project\":{\"name\":\"品牌迭代\",\"link\":\"http://github.com/\"},\"template\":\"在 @{group} 新建项目 @{project}\"}]");	
		return new  ResponseEntity<JSONArray>(jo,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/tags",method=RequestMethod.GET)
	public ResponseEntity<JSONObject> tags(String token) {
	JSONObject jo= JSONObject.parseObject("{\"list\":[{\"name\":\"平凉市\",\"value\":53,\"type\":1},{\"name\":\"唐山市\",\"value\":4,\"type\":1},{\"name\":\"天津市\",\"value\":93,\"type\":1},{\"name\":\"巢湖市\",\"value\":58,\"type\":2},{\"name\":\"龙岩市\",\"value\":90,\"type\":0},{\"name\":\"娄底市\",\"value\":74,\"type\":0},{\"name\":\"果洛藏族自治州\",\"value\":40,\"type\":0},{\"name\":\"固原市\",\"value\":65,\"type\":2},{\"name\":\"赣州市\",\"value\":80,\"type\":0},{\"name\":\"吴忠市\",\"value\":18,\"type\":0},{\"name\":\"林芝地区\",\"value\":94,\"type\":1},{\"name\":\"株洲市\",\"value\":83,\"type\":1},{\"name\":\"石嘴山市\",\"value\":100,\"type\":2},{\"name\":\"山南地区\",\"value\":17,\"type\":1},{\"name\":\"玉林市\",\"value\":94,\"type\":2},{\"name\":\"铜川市\",\"value\":45,\"type\":0},{\"name\":\"北京市\",\"value\":84,\"type\":1},{\"name\":\"上海市\",\"value\":96,\"type\":1},{\"name\":\"汉中市\",\"value\":6,\"type\":1},{\"name\":\"莱芜市\",\"value\":66,\"type\":0},{\"name\":\"高雄市\",\"value\":42,\"type\":1},{\"name\":\"上海市\",\"value\":45,\"type\":1},{\"name\":\"金昌市\",\"value\":8,\"type\":1},{\"name\":\"海北藏族自治州\",\"value\":74,\"type\":2},{\"name\":\"西宁市\",\"value\":39,\"type\":2},{\"name\":\"南通市\",\"value\":51,\"type\":2},{\"name\":\"河源市\",\"value\":46,\"type\":1},{\"name\":\"白城市\",\"value\":9,\"type\":1},{\"name\":\"香港岛\",\"value\":18,\"type\":1},{\"name\":\"七台河市\",\"value\":95,\"type\":1},{\"name\":\"上饶市\",\"value\":96,\"type\":2},{\"name\":\"阳江市\",\"value\":53,\"type\":0},{\"name\":\"新界\",\"value\":49,\"type\":1},{\"name\":\"海口市\",\"value\":30,\"type\":0},{\"name\":\"河源市\",\"value\":17,\"type\":1},{\"name\":\"泰州市\",\"value\":46,\"type\":0},{\"name\":\"崇左市\",\"value\":58,\"type\":1},{\"name\":\"安顺市\",\"value\":51,\"type\":1},{\"name\":\"六盘水市\",\"value\":8,\"type\":2},{\"name\":\"庆阳市\",\"value\":48,\"type\":0},{\"name\":\"乌兰察布市\",\"value\":8,\"type\":1},{\"name\":\"澳门半岛\",\"value\":88,\"type\":0},{\"name\":\"承德市\",\"value\":67,\"type\":0},{\"name\":\"白银市\",\"value\":88,\"type\":0},{\"name\":\"基隆市\",\"value\":75,\"type\":1},{\"name\":\"重庆市\",\"value\":14,\"type\":1},{\"name\":\"昆明市\",\"value\":95,\"type\":2},{\"name\":\"遵义市\",\"value\":85,\"type\":0},{\"name\":\"丽江市\",\"value\":79,\"type\":0},{\"name\":\"哈密地区\",\"value\":56,\"type\":1},{\"name\":\"张家界市\",\"value\":8,\"type\":0},{\"name\":\"本溪市\",\"value\":43,\"type\":0},{\"name\":\"上海市\",\"value\":70,\"type\":1},{\"name\":\"黄冈市\",\"value\":3,\"type\":1},{\"name\":\"鸡西市\",\"value\":64,\"type\":0},{\"name\":\"九龙\",\"value\":10,\"type\":2},{\"name\":\"上海市\",\"value\":8,\"type\":1},{\"name\":\"鹤岗市\",\"value\":87,\"type\":0},{\"name\":\"巴中市\",\"value\":73,\"type\":1},{\"name\":\"黄石市\",\"value\":12,\"type\":1},{\"name\":\"商洛市\",\"value\":95,\"type\":0},{\"name\":\"石嘴山市\",\"value\":27,\"type\":1},{\"name\":\"澳门半岛\",\"value\":34,\"type\":0},{\"name\":\"重庆市\",\"value\":11,\"type\":1},{\"name\":\"伊春市\",\"value\":14,\"type\":1},{\"name\":\"金昌市\",\"value\":41,\"type\":2},{\"name\":\"三明市\",\"value\":59,\"type\":1},{\"name\":\"伊犁哈萨克自治州\",\"value\":93,\"type\":2},{\"name\":\"厦门市\",\"value\":93,\"type\":1},{\"name\":\"泰安市\",\"value\":13,\"type\":1},{\"name\":\"潮州市\",\"value\":34,\"type\":2},{\"name\":\"鞍山市\",\"value\":8,\"type\":0},{\"name\":\"屏东县\",\"value\":86,\"type\":0},{\"name\":\"防城港市\",\"value\":37,\"type\":1},{\"name\":\"常州市\",\"value\":71,\"type\":2},{\"name\":\"天津市\",\"value\":45,\"type\":2},{\"name\":\"三门峡市\",\"value\":30,\"type\":1},{\"name\":\"乌兰察布市\",\"value\":85,\"type\":1},{\"name\":\"焦作市\",\"value\":6,\"type\":1},{\"name\":\"固原市\",\"value\":20,\"type\":1},{\"name\":\"六盘水市\",\"value\":88,\"type\":1},{\"name\":\"三亚市\",\"value\":53,\"type\":0},{\"name\":\"上海市\",\"value\":35,\"type\":2},{\"name\":\"许昌市\",\"value\":47,\"type\":1},{\"name\":\"营口市\",\"value\":2,\"type\":1},{\"name\":\"澳门半岛\",\"value\":43,\"type\":1},{\"name\":\"台州市\",\"value\":60,\"type\":0},{\"name\":\"赣州市\",\"value\":94,\"type\":1},{\"name\":\"包头市\",\"value\":45,\"type\":0},{\"name\":\"襄阳市\",\"value\":68,\"type\":2},{\"name\":\"邯郸市\",\"value\":60,\"type\":1},{\"name\":\"北京市\",\"value\":67,\"type\":1},{\"name\":\"池州市\",\"value\":18,\"type\":1},{\"name\":\"黑河市\",\"value\":68,\"type\":1},{\"name\":\"基隆市\",\"value\":86,\"type\":1},{\"name\":\"巴中市\",\"value\":6,\"type\":1},{\"name\":\"海外\",\"value\":86,\"type\":1},{\"name\":\"丹东市\",\"value\":49,\"type\":1},{\"name\":\"苏州市\",\"value\":58,\"type\":2},{\"name\":\"安庆市\",\"value\":36,\"type\":1}]}");
	return new  ResponseEntity<JSONObject>(jo,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/currentUser",method=RequestMethod.GET)
	public ResponseEntity<AntUser> currentUser(String token) {
		
		
		AntUser au = new AntUser();
		FyUser u=userMng.findById(1L);
		au.setAvatar(u.getHeadImg());
		au.setName(u.getRealname());
		au.setNotifyCount(12);
		au.setUserid(u.getId().toString());
		return new  ResponseEntity<AntUser>(au,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/queryUser",method=RequestMethod.GET)
	public Result queryUser(String token,FyUser.Type type, Integer pageNo,Integer pageSize,FyUser search) {
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		Pagination<FyUser> page = userMng.getPage(type,pageNo,pageSize,search);
		
		return new  Result(page);
	}
	
	/**
	 * 新增用户
	 * @param token
	 * @param user  用户
	 * @param groupId  分组id
	 * @return FyUser
	 */
	@RequestMapping(value = "/addUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result addUser( String token,FyUser user,FyUser.Type type) {
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		if(user.getId()==null){
		
		user = userMng.save(user);
		}else{
			user = userMng.update(user);
		}
		return new  Result(user);
	}
	
	
	/**
	 *  删除用户
	 * @param token
	 * @param ids
	 * @return Boolean
	 */
	@RequestMapping(value = "/removeUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result removeClazz(String token, Long[] ids) {

		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}

		userMng.delete(ids);

	
		return new Result(true);

	}
	
}
