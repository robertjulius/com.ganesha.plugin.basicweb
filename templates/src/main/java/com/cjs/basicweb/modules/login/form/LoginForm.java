package com.cjs.basicweb.modules.login.form;

import com.cjs.basicweb.model.FormBean;

public class LoginForm extends FormBean {

	private static final long serialVersionUID = -5790003599258456725L;

	private String userId;
	private String password;

	public String getPassword() {
		return password;
	}

	public String getUserId() {
		return userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
