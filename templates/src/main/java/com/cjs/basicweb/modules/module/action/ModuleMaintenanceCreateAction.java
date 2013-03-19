package com.cjs.basicweb.modules.module.action;

import java.util.ArrayList;

import com.cjs.basicweb.model.user.SimpleUser;
import com.cjs.basicweb.modules.module.ModuleForm;
import com.cjs.basicweb.utility.CommonUtils;
import com.cjs.core.exception.AppException;

public class ModuleMaintenanceCreateAction extends ModuleMaintenanceAction {

	private static final long serialVersionUID = 8114275581397242184L;

	public ModuleMaintenanceCreateAction() throws AppException {
		super();
	}

	public String executeCreate() throws AppException {
		SimpleUser user = (SimpleUser) getUserSession().getUser();

		ModuleForm form = getForm();
		getBL().create(form.getNewFirstEntry(), form.getNewName(),
				form.getNewDescription(), form.getNewParentId(),
				form.getNewURLs(), user.getId(),
				CommonUtils.getCurrentTimestamp());

		return SUCCESS;
	}

	public String prepareCreate() throws AppException {
		ModuleForm form = getForm();
		form.clearForm("new");
		form.setNewParentId(null);
		form.setNewParentName(null);

		if (form.getNewURLs() == null) {
			form.setNewURLs(new ArrayList<String>());
		} else {
			form.getNewURLs().clear();
		}

		form.setSelectedId(null);

		return SUCCESS;
	}

	public String validateCreate() throws AppException {
		if (validateForm()) {
			getForm().setNewURLs(new ArrayList<String>());
			if (getListAccessPaths() != null) {
				for (String url : getListAccessPaths()) {
					if (url == null || url.trim().isEmpty()) {
						continue;
					}
					getForm().getNewURLs().add(url);
				}
			}
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
}
