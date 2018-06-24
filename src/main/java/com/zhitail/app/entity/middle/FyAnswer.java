package com.zhitail.app.entity.middle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FyAnswer {
	private Integer index;
	private Double goal;
	private Boolean isGrade;
	private Integer[] orders;
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
	}
	

}
