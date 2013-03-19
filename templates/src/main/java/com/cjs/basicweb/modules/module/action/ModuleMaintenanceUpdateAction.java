package com.cjs.basicweb.modules.module.action;

import java.util.ArrayList;

import com.cjs.basicweb.model.accesspath.AccessPath;
import com.cjs.basicweb.model.user.SimpleUser;
import com.cjs.basicweb.modules.module.ModuleForm;
import com.cjs.basicweb.utility.CommonUtils;
import com.cjs.core.exception.AppException;

public class ModuleMaintenanceUpdateAction extends ModuleMaintenanceAction {

	private static final long serialVersionUID = 8114275581397242184L;

	public ModuleMaintenanceUpdateAction() throws AppException {
		super();
	}

	public String executeUpdate() throws AppException {
		SimpleUser user = (SimpleUser) getUserSession().getUser();

		ModuleForm form = getForm();
		getBL().update(form.getSelectedId(), form.getNewFirstEntry(),
				form.getNewName(), form.getNewDescription(),
				form.getNewParentId(), form.getNewURLs(), user.getId(),
				CommonUtils.getCurrentTimestamp());

		return SUCCESS;
	}

	public String prepareUpdate() throws AppException {
		ModuleForm form = getForm();
		form.clearForm("new");
		form.assignFromEntity("new", form.getOld());

		if (form.getOld().getParent() != null) {
			form.setNewParentId(form.getOld().getParent().getId());
			form.setNewParentName(form.getOld().getParent().getName());
		} else {
			form.setNewParentId(null);
			form.setNewParentName(null);
		}

		if (form.getNewURLs() == null) {
			form.setNewURLs(new ArrayList<String>());
		} else {
			form.getNewURLs().clear();
		}

		for (AccessPath accessPath : form.getOld().getAccessPaths()) {
			form.getNewURLs().add(accessPath.getUrl());
		}

		return SUCCESS;
	}

	public String validateUpdate() throws AppException {
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
