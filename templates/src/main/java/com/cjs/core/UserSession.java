package com.cjs.core;

import java.sql.Timestamp;

public interface UserSession {
	public Timestamp getLoginTime();

	public User getUser();

	public void setLoginTime(Timestamp timestamp);

	public void setUser(User user);
}
