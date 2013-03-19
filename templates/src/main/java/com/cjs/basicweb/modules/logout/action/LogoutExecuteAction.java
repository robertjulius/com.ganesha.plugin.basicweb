package com.cjs.basicweb.modules.logout.action;

import com.cjs.basicweb.modules.BusinessLogic;
import com.cjs.basicweb.modules.login.logic.LoginBL;
import com.cjs.basicweb.utility.GeneralConstants.ActionType;
import com.cjs.core.exception.AppException;
import com.cjs.core.exception.UserException;
import com.cjs.struts2.BaseAction;

public class LogoutExecuteAction extends BaseAction<LoginBL> {

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
