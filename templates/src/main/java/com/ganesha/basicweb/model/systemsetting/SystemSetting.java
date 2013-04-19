package com.ganesha.basicweb.model.systemsetting;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ganesha.basicweb.model.TableEntity;

@Entity
@Table(name = "ms_system_setting")
public class SystemSetting implements TableEntity {

	private static final long serialVersionUID = -2855686542697649307L;
	private static final Map<String, String> map = new ConcurrentHashMap<>();

	@Id
	private String id;

	@Column(nullable = false)
	private String value;

	public static void clearProperty() {
		map.clear();
	}

	public static boolean containsId(String id) {
		return map.containsKey(id);
	}

	public static String getProperty(String id) {
		return map.get(id);
	}

	public static void setProperty(String id, String value) {
		map.put(id, value);
	}

	public String getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
