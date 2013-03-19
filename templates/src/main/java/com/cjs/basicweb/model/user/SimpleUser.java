package com.cjs.basicweb.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.cjs.basicweb.model.Trackable;
import com.cjs.basicweb.model.usergroup.UserGroup;
import com.cjs.core.User;

@Entity
@Table(name = "ms_user")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class SimpleUser extends Trackable implements User {

	private static final long serialVersionUID = -1218882314919256632L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "user_id", unique = true, nullable = false)
	private String userId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(nullable = false)
	private String password;

	@ManyToOne
	@JoinColumn(name = "user_group_id")
	private UserGroup userGroup;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
