package com.cjs.basicweb.model.usergroup;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.cjs.basicweb.model.Trackable;
import com.cjs.basicweb.model.module.Module;
import com.cjs.basicweb.model.user.SimpleUser;

@Entity
@Table(name = "ms_user_group")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class UserGroup extends Trackable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(unique = true, nullable = false)
	private String name;

	private String description;

	@ManyToMany
	@JoinTable(name = "ms_privilege", joinColumns = @JoinColumn(name = "user_group_id"), inverseJoinColumns = @JoinColumn(name = "module_id"))
	private List<Module> modules;

	@OneToMany(mappedBy = "userGroup")
	private List<SimpleUser> users;

	public String getDescription() {
		return description;
	}

	public String getId() {
		return id;
	}

	public List<Module> getModules() {
		return modules;
	}

	public String getName() {
		return name;
	}

	public List<SimpleUser> getUsers() {
		return users;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsers(List<SimpleUser> users) {
		this.users = users;
	}
}
