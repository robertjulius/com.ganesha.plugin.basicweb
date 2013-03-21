package com.ganesha.basicweb.modules.resetusersession.logic;

import java.util.ArrayList;
import java.util.List;

import com.ganesha.basicweb.modules.BusinessLogic;
import com.ganesha.basicweb.modules.login.usersession.SimpleSessionManager;
import com.ganesha.basicweb.modules.login.usersession.SimpleUserSession;
import com.ganesha.basicweb.utility.AppContextManager;
import com.ganesha.basicweb.utility.GeneralConstants;
import com.ganesha.basicweb.utility.GeneralConstants.ActionType;
import com.ganesha.core.UserSession;
import com.ganesha.core.exception.AppException;

public class ResetUserSessionBL extends BusinessLogic {

	public SimpleUserSession getDetail(String userId) {
		return (SimpleUserSession) AppContextManager.getSessionManager()
				.getByUserId(userId)
				.getAttribute(GeneralConstants.USER_SESSION);
	}

	public List<SimpleUserSession> getList() throws AppException {
		List<UserSession> userSessions = ((SimpleSessionManager) AppContextManager
				.getSessionManager()).getUserSessions();
		List<SimpleUserSession> simpleUserSessions = new ArrayList<>();
		for (UserSession userSession : userSessions) {
			simpleUserSessions.add((SimpleUserSession) userSession);
		}
		return simpleUserSessions;
	}

	public void reset(String userId) throws AppException {
		saveActivityLog(ActionType.OTHER, "Reset user session with id "
				+ userId);

		AppContextManager.getSessionManager().getByUserId(userId).invalidate();
	}
}
