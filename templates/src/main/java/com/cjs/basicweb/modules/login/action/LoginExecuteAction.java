package com.cjs.basicweb.modules.login.action;

import com.cjs.basicweb.modules.login.form.LoginForm;
import com.cjs.basicweb.modules.login.logic.LoginBL;
import com.cjs.basicweb.modules.login.usersession.SimpleUserSession;
import com.cjs.basicweb.utility.AppContextManager;
import com.cjs.basicweb.utility.GeneralConstants;
import com.cjs.core.UserSession;
import com.cjs.core.exception.AppException;
import com.cjs.core.exception.UserException;
import com.cjs.struts2.FormAction;

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
