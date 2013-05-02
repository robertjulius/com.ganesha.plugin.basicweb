package com.ganesha.basicweb.modules.logout.action;

import com.ganesha.basicweb.modules.BusinessLogic;
import com.ganesha.basicweb.modules.login.logic.LoginBL;
import com.ganesha.basicweb.utility.GeneralConstants.ActionType;
import com.ganesha.core.exception.AppException;
import com.ganesha.core.exception.UserException;
import com.ganesha.struts2.BusinessAction;

public class LogoutExecuteAction extends BusinessAction<LoginBL> {

	private static final long serialVersionUID = -3643549719278354411L;

	public LogoutExecuteAction() throws AppException {
		super(LoginBL.class);
	}

	@Override
	public String execute() throws AppException, UserException {
		BusinessLogic logic = new BusinessLogic() {
			/*
			 * Nothing to implements
			 */
		};
		logic.beginTransaction();
		logic.saveActivityLog(ActionType.OTHER, "");
		logic.commit();

		getSession().invalidate();
		return SUCCESS;
	}
}
