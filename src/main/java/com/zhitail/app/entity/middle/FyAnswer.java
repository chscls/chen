package com.zhitail.app.entity.middle;

public class FyAnswer {
	private Double goal;
	private Boolean isGrade=false;
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
	

}
