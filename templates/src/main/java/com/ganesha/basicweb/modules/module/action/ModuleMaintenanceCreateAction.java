package com.ganesha.basicweb.modules.module.action;

import java.util.ArrayList;

import com.ganesha.basicweb.model.user.SimpleUser;
import com.ganesha.basicweb.modules.module.ModuleForm;
import com.ganesha.basicweb.utility.CommonUtils;
import com.ganesha.core.exception.AppException;

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

		form.getNewURLs().add("/modules/modulename/initial.action");
		form.getNewURLs().add("/modules/modulename/main.action");
		form.getNewURLs().add("/modules/modulename/search.action");
		form.getNewURLs().add("/modules/modulename/searchResult.action");
		form.getNewURLs().add("/modules/modulename/prepareDetail.action");
		form.getNewURLs().add("/modules/modulename/detail.action");
		form.getNewURLs().add("/modules/modulename/prepareUpdate.action");
		form.getNewURLs().add("/modules/modulename/formUpdate.action");
		form.getNewURLs().add("/modules/modulename/validateUpdate.action");
		form.getNewURLs().add("/modules/modulename/confirmUpdate.action");
		form.getNewURLs().add("/modules/modulename/executeUpdate.action");
		form.getNewURLs().add("/modules/modulename/summaryUpdate.action");
		form.getNewURLs().add("/modules/modulename/prepareCreate.action");
		form.getNewURLs().add("/modules/modulename/formCreate.action");
		form.getNewURLs().add("/modules/modulename/validateCreate.action");
		form.getNewURLs().add("/modules/modulename/confirmCreate.action");
		form.getNewURLs().add("/modules/modulename/executeCreate.action");
		form.getNewURLs().add("/modules/modulename/summaryCreate.action");
		form.getNewURLs().add("/modules/modulename/executeDelete.action");
		form.getNewURLs().add("/modules/modulename/summaryDelete.action");

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
