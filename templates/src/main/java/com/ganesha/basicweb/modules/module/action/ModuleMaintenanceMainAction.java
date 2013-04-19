package com.ganesha.basicweb.modules.module.action;

import java.util.List;

import com.ganesha.basicweb.model.Pagination;
import com.ganesha.basicweb.model.module.Module;
import com.ganesha.basicweb.modules.module.ModuleForm;
import com.ganesha.core.exception.AppException;

public class ModuleMaintenanceMainAction extends ModuleMaintenanceAction {

	private static final long serialVersionUID = 8114275581397242184L;

	public ModuleMaintenanceMainAction() throws AppException {
		super();
	}

	public String initial() throws AppException {
		ModuleForm form = getForm();

		List<Module> modules = getBL().getAllModules();
		modules.add(0, new Module());
		form.setSelectListParent(modules);

		form.setPagination(new Pagination(10));

		return SUCCESS;
	}

	public String prepareDetail() throws AppException {
		String selectedId = getForm().getSelectedId();

		Module module = getBL().getDetail(selectedId);
		ModuleForm form = getForm();
		form.setOld(module);

		return SUCCESS;
	}

	public String search() throws AppException {
		String name = getForm().getSearchName();
		String firstEntry = getForm().getSearchFirstEntry();
		String parentId = getForm().getSearchParentId();

		Pagination pagination = getForm().getPagination();
		List<Module> modules = getBL().search(name, firstEntry, parentId,
				pagination);
		getForm().setSearchResult(modules);
		return SUCCESS;
	}
}
