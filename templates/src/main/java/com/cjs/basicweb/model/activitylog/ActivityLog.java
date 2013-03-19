package com.cjs.basicweb.model.activitylog;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lg_activity")
public class ActivityLog implements Serializable {

	private static final long serialVersionUID = 2343345295596584661L;;

	@Column(name = "user", nullable = false)
	private String user;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "user_name", nullable = false)
	private String userName;

	@Column(name = "action_url", nullable = false)
	private String actionUrl;

	@Column(name = "action_type", nullable = false)
	private String actionType;

	@Column(name = "description", nullable = false, length = 1024)
	private String description;

	@Id
	@Column(name = "action_date")
	private Timestamp actionDate;

	public Timestamp getActionDate() {
		return actionDate;
	}

	public String getActionType() {
		return actionType;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public String getDescription() {
		return description;
	}

	public String getUser() {
		return user;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setActionDate(Timestamp actionDate) {
		this.actionDate = actionDate;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
