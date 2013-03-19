package com.cjs.basicweb.modules.module;

import java.util.List;

import com.cjs.basicweb.model.FormBean;
import com.cjs.basicweb.model.Pagination;
import com.cjs.basicweb.model.module.Module;
import com.cjs.core.exception.AppException;
import com.opensymphony.xwork2.ActionSupport;

public class ModuleForm extends FormBean {

	private static final long serialVersionUID = 7952657167875968415L;

	private String searchId;
	private String searchName;
	private String searchFirstEntry;
	private String searchParentId;
	private List<Module> searchResult;

	private Pagination pagination;
	private String selectedId;

	private Module old;

	private String newFirstEntry;

	private String newName;

	private String newDescription;
	private String newParentId;
	private String newParentName;
	private List<String> newURLs;
	private List<Module> selectListParent;

	public String getNewDescription() {
		return newDescription;
	}

	public String getNewFirstEntry() {
		return newFirstEntry;
	}

	public String getNewName() {
		return newName;
	}

	public String getNewParentId() {
		return newParentId;
	}

	public String getNewParentName() {
		return newParentName;
	}

	public List<String> getNewURLs() {
		return newURLs;
	}

	public Module getOld() {
		return old;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public String getSearchFirstEntry() {
		return searchFirstEntry;
	}

	public String getSearchId() {
		return searchId;
	}

	public String getSearchName() {
		return searchName;
	}

	public String getSearchParentId() {
		return searchParentId;
	}

	public List<Module> getSearchResult() {
		return searchResult;
	}

	public String getSelectedId() {
		return selectedId;
	}

	public List<Module> getSelectListParent() {
		return selectListParent;
	}

	public void setNewDescription(String newDescription) {
		this.newDescription = newDescription;
	}

	public void setNewFirstEntry(String newFirstEntry) {
		this.newFirstEntry = newFirstEntry;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public void setNewParentId(String newParentId) {
		this.newParentId = newParentId;
	}

	public void setNewParentName(String newParentName) {
		this.newParentName = newParentName;
	}

	public void setNewURLs(List<String> newURLs) {
		this.newURLs = newURLs;
	}

	public void setOld(Module old) {
		this.old = old;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public void setSearchFirstEntry(String searchFirstEntry) {
		this.searchFirstEntry = searchFirstEntry;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public void setSearchParentId(String searchParentId) {
		this.searchParentId = searchParentId;
	}

	public void setSearchResult(List<Module> searchResult) {
		this.searchResult = searchResult;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public void setSelectListParent(List<Module> selectListParent) {
		this.selectListParent = selectListParent;
	}

	@Override
	public void validate(ActionSupport action) throws AppException {
		if (newParentId.equals(selectedId)) {
			action.addFieldError("newParentId",
					action.getText("resource.fieldError.parent"));
		}
	}
}
