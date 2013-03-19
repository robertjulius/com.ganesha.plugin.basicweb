package com.cjs.basicweb.modules.module.action;

import java.util.List;

import com.cjs.basicweb.modules.module.ModuleBL;
import com.cjs.basicweb.modules.module.ModuleForm;
import com.cjs.core.exception.AppException;
import com.cjs.struts2.FormAction;

public abstract class ModuleMaintenanceAction extends
		FormAction<ModuleForm, ModuleBL> {

	private static final long serialVersionUID = 8114275581397242184L;

	private List<String> listAccessPaths;

	public ModuleMaintenanceAction() throws AppException {
		super(ModuleForm.class, ModuleBL.class);
	}

	public List<String> getListAccessPaths() {
		return listAccessPaths;
	}

	public void setListAccessPaths(List<String> listAccessPaths) {
		this.listAccessPaths = listAccessPaths;
	}
}
