package com.zhitail.app.soa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zhitail.app.entity.FyCatalog;
import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyQuestion.Type;
import com.zhitail.app.entity.FyShow;
import com.zhitail.app.entity.middle.QuestionConfig;
import com.zhitail.app.entity.middle.ShowAble;
import com.zhitail.app.entity.middle.SubQuestionConfig;
import com.zhitail.app.manager.FyCatalogMng;
import com.zhitail.app.manager.FyQuestionMng;

@Component
public class PublicComponent {
	 @Value("${com.zhitail.upload.imgServer}")
	 private String imgServer;
	@Autowired
	private FyQuestionMng questionMng;
	@Autowired
	private FyCatalogMng catalogMng;
	public void fullQuestions(FyQuestion question) {
		if(question.getType()==Type.synthesis) {
		// TODO Auto-generated method stub
		FyQuestion temp;
		List<SubQuestionConfig> ids = question.getSubQuestionConfigs();
		if (ids.size() > 0) {
			Long[] qids = new Long[ids.size()];
			for (int i = 0; i < ids.size(); i++) {
				qids[i] = ids.get(i).getId();
			}
			List<FyQuestion> qs =questionMng.findByIds(qids);
			Map<Long, FyQuestion> map = new HashMap<Long, FyQuestion>(qs.size());
			for (FyQuestion q : qs) {
				map.put(q.getId(), q);
			}
			List<FyQuestion> fqs = new ArrayList<FyQuestion>();
			for (SubQuestionConfig config : ids) {
				if (map.containsKey(config.getId())) {
					temp = map.get(config.getId());
					temp.setRate(config.getRate());
					fqs.add(temp);
				}
			}
			question.setSubQuestions(fqs);

		} else {
			question.setSubQuestions(new ArrayList<FyQuestion>());
		}

	}}
	
	public void fullShow(FyShow show) {
		List<Long> ids =show.getShowIds();
		
		if(ids.size()==0) {
			return;
		}
		List<ShowAble> ls=new ArrayList<ShowAble>();
		FyShow.Type t = show.getType();
		if(t==FyShow.Type.catalog) {
			List<FyCatalog> list = catalogMng.findByIds(ids.toArray(new Long[ids.size()]));
			Map<Long,FyCatalog> map=new HashMap<Long,FyCatalog>();
			for(FyCatalog f:list) {
				map.put(f.getId(), f);
			}
			for(Long id:ids) {
				if(map.containsKey(id)) {
					ls.add(map.get(id).makeShowAble());
				}
			}
			show.setList(ls);
		}
	
	}
	public void fullQuestions(FyTest test) {
		FyQuestion temp;
		List<QuestionConfig> ids = test.getQuestionConfigs();
		if (ids.size() > 0) {
			Long[] qids = new Long[ids.size()];
			for (int i = 0; i < ids.size(); i++) {
				qids[i] = ids.get(i).getId();
			}
			List<FyQuestion> qs = questionMng.findByIds(qids);
			Map<Long, FyQuestion> map = new HashMap<Long, FyQuestion>(qs.size());
			for (FyQuestion q : qs) {
				map.put(q.getId(), q);
			}
			List<FyQuestion> fqs = new ArrayList<FyQuestion>();
			for (QuestionConfig config : ids) {
				if (map.containsKey(config.getId())) {
					temp = map.get(config.getId());
					temp.setScore(config.getScore());
					
				
					fullQuestions(temp);
						
				
					temp.fullImg(imgServer);
					fqs.add(temp);
				}
			}
			test.setQuestions(fqs);

		} else {
			test.setQuestions(new ArrayList<FyQuestion>());
		}

	}
}
