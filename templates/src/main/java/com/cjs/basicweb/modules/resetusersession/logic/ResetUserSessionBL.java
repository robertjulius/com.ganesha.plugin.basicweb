package com.cjs.basicweb.modules.resetusersession.logic;

import java.util.ArrayList;
import java.util.List;

import com.cjs.basicweb.modules.BusinessLogic;
import com.cjs.basicweb.modules.login.usersession.SimpleSessionManager;
import com.cjs.basicweb.modules.login.usersession.SimpleUserSession;
import com.cjs.basicweb.utility.AppContextManager;
import com.cjs.basicweb.utility.GeneralConstants;
import com.cjs.basicweb.utility.GeneralConstants.ActionType;
import com.cjs.core.UserSession;
import com.cjs.core.exception.AppException;

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
