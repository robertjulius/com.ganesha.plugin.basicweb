package com.ganesha.basicweb.modules.login.action;

import com.ganesha.basicweb.modules.login.form.LoginForm;
import com.ganesha.basicweb.modules.login.logic.LoginBL;
import com.ganesha.basicweb.modules.login.usersession.SimpleUserSession;
import com.ganesha.basicweb.utility.AppContextManager;
import com.ganesha.basicweb.utility.GeneralConstants;
import com.ganesha.core.UserSession;
import com.ganesha.core.exception.AppException;
import com.ganesha.core.exception.UserException;
import com.ganesha.struts2.FormAction;

public class LoginExecuteAction extends FormAction<LoginForm, LoginBL> {

	private static final long serialVersionUID = -3643549719278354411L;

	private UserSession userSession;

	public LoginExecuteAction() throws AppException {
		super(LoginForm.class, LoginBL.class);
		userSession = new SimpleUserSession();
	}

	@Override
	public String execute() throws AppException {
		try {
			getSession().put(GeneralConstants.USER_SESSION, userSession);
			getBL().performLogin(getForm().getUserId(),
					getForm().getPassword(), userSession);
			AppContextManager.getSessionManager().registerSession(
					getRequest().getSession());

			return SUCCESS;
		} catch (UserException e) {
			getSession().remove(GeneralConstants.USER_SESSION);
			addActionError(e.getMessageId());
			return INPUT;
		}
	}
}
