package com.cjs.basicweb.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Trackable implements Serializable {

	private static final long serialVersionUID = -490441473844263342L;

	@Column(name = "create_by", nullable = false)
	private String createBy;

	@Column(name = "create_date", nullable = false)
	private Timestamp createDate;

	@Column(name = "update_by", nullable = false)
	private String updateBy;

	@Column(name = "update_date", nullable = false)
	private Timestamp updateDate;

	@Column(name = "rec_status", nullable = false)
	private String recStatus;

	public String getCreateBy() {
		return createBy;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public String getRecStatus() {
		return recStatus;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

}
