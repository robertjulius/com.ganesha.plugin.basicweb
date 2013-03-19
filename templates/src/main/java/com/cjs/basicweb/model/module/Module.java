package com.cjs.basicweb.model.module;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.cjs.basicweb.model.Trackable;
import com.cjs.basicweb.model.accesspath.AccessPath;
import com.cjs.basicweb.model.usergroup.UserGroup;

@Entity
@Table(name = "ms_module")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Module extends Trackable {

	private static final long serialVersionUID = -1218882314919256632L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "name", unique = true, nullable = false)
	private String name;

	private String description;

	@Column(name = "first_entry", unique = true)
	private String firstEntry;

	@ManyToOne
	@JoinColumn(name = "parent")
	private Module parent;

	@OneToMany(mappedBy = "parent")
	private List<Module> childs;

	@OneToMany(mappedBy = "module")
	private List<AccessPath> accessPaths;

	@ManyToMany(mappedBy = "modules")
	private List<UserGroup> userGroups;

	public List<AccessPath> getAccessPaths() {
		return accessPaths;
	}

	public List<Module> getChilds() {
		return childs;
	}

	public String getDescription() {
		return description;
	}

	public String getFirstEntry() {
		return firstEntry;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Module getParent() {
		return parent;
	}

	public List<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setAccessPaths(List<AccessPath> accessPaths) {
		this.accessPaths = accessPaths;
	}

	public void setChilds(List<Module> childs) {
		this.childs = childs;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFirstEntry(String firstEntry) {
		this.firstEntry = firstEntry;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(Module parent) {
		this.parent = parent;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}
}
