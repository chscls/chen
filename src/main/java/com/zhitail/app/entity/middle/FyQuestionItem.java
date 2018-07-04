package com.zhitail.app.entity.middle;




public class FyQuestionItem {
	
	private java.lang.String content;
	private java.lang.String answer;
	public java.lang.String getAnswer() {
		return answer;
	}
	public void setAnswer(java.lang.String answer) {
		this.answer = answer;
	}
	public java.lang.Boolean getIsAnswer() {
		return isAnswer;
	}
	public void setIsAnswer(java.lang.Boolean isAnswer) {
		this.isAnswer = isAnswer;
	}
	private java.lang.Boolean isSolution;
	private java.lang.Boolean isAnswer;
	private java.lang.Boolean isRich=false;
	public java.lang.Boolean getIsRich() {
		return isRich;
	}
	public void setIsRich(java.lang.Boolean isRich) {
		this.isRich = isRich;
	}
	public java.lang.Boolean getIsSolution() {
		return isSolution;
	}
	public void setIsSolution(java.lang.Boolean isSolution) {
		this.isSolution = isSolution;
	}
	public java.lang.String getContent() {
		return content;
	}
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	public void fullImg(String imgServer) {
		// TODO Auto-generated method stub
		if(this.getIsRich()!=null&&this.getIsRich()&&!this.content.contains("http")) {
			this.content=this.content.replaceAll("<img src=\"", "<img src=\""+imgServer);
		}
	}

}
