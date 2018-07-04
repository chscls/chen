package com.zhitail.app.entity.middle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FyQuestion.Type;

public class FyAnswer {
	public FyAnswer[] subAnswers;
	public FyAnswer[] getSubAnswers() {
		return subAnswers;
	}
	public void setSubAnswers(FyAnswer[] subAnswers) {
		this.subAnswers = subAnswers;
	}
	private Integer index;
	private Double goal;
	private Boolean isGrade;
	private Integer[] orders;
	public FyAnswer() {
		
		
	}
	public FyAnswer(int i) {
		this.index=i;
		this.isGrade=false;
		// TODO Auto-generated constructor stub
	}
	public Integer[] getOrders() {
		return orders;
	}
	public void setOrders(Integer[] orders) {
		this.orders = orders;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	private Integer[] indexs;
	private String[] answers;
	public Double getGoal() {
		return goal;
	}
	public void setGoal(Double goal) {
		this.goal = goal;
	}
	public Boolean getIsGrade() {
		return isGrade;
	}
	public void setIsGrade(Boolean isGrade) {
		this.isGrade = isGrade;
	}
	
	public Integer[] getIndexs() {
		return indexs;
	}
	public void setIndexs(Integer[] indexs) {
		this.indexs = indexs;
	}
	public String[] getAnswers() {
		return answers;
	}
	public void setAnswers(String[] answers) {
		this.answers = answers;
	}
	public void makeOrder(Integer length) {
		List<Integer> ins = new ArrayList<Integer>(length);
		for(int i=0;i<length;i++) {
			ins.add(i);
		}
		Collections.shuffle(ins);
		// TODO Auto-generated method stub
		this.orders=ins.toArray(new Integer[length]);
		this.indexs=new Integer[0];
	}
	public void makeOrders(List<FyQuestion> subQuestions) {
		List<FyAnswer> ans = new ArrayList<FyAnswer>(subQuestions.size());
		FyAnswer a;
		for(int i=0;i<subQuestions.size();i++) {
			a=new FyAnswer(i);
			if(subQuestions.get(i).getType()==Type.single||subQuestions.get(i).getType()==Type.mutiply||subQuestions.get(i).getType()==Type.judge) {
				a.makeOrder(subQuestions.get(i).getItems().size());		
			}
			ans.add(a);
		}
		this.subAnswers = ans.toArray(new FyAnswer[ans.size()]) ;
		// TODO Auto-generated method stub
		
	}
	

}
