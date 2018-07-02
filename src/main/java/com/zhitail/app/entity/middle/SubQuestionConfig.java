package com.zhitail.app.entity.middle;

public class SubQuestionConfig {
	private Long id;
	private Double rate;
	public SubQuestionConfig() {
		
		// TODO Auto-generated constructor stub
	}
	public SubQuestionConfig(Long id, Double rate) {
		this.id=id;
		this.rate=rate;
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubQuestionConfig other = (SubQuestionConfig) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
