package com.cjs.basicweb.modules.usergroupmaintenance;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.cjs.basicweb.modules.login.Privilege;

public class HtmlPrivilegeTreeGenerator {

	public static String generateHtmlCheckBox(
			TreeMap<String, Privilege> treeMap, List<String> initialModuleIds) {
		StringBuilder stringBuilder = new StringBuilder();
		generateHtmlCheckBox(treeMap, stringBuilder, initialModuleIds);
		return stringBuilder.toString().replaceFirst("<hr />", "");
	}

	public static String generateHtmlTree(TreeMap<String, Privilege> treeMap) {
		StringBuilder stringBuilder = new StringBuilder();
		generateHtmlTree(treeMap, stringBuilder);
		return stringBuilder.toString().replaceFirst("<hr />", "");
	}

	private static void generateHtmlCheckBox(
			TreeMap<String, Privilege> treeMap, StringBuilder stringBuilder,
			List<String> initialModuleIds) {

		Collection<Privilege> nodes = treeMap.values();
		Iterator<Privilege> iterator = nodes.iterator();
		while (iterator.hasNext()) {

			stringBuilder.append("<li>");
			Privilege privilege = iterator.next();

			if (!privilege.getChilds().isEmpty()) {

				stringBuilder.append("<b>");
				if (privilege.getParentId() == null
						|| privilege.getParentId().trim().isEmpty()) {
					stringBuilder.append("<hr />");
					stringBuilder.append("<font color='#0000BB'>");
					stringBuilder.append(privilege.getName());
					stringBuilder.append("</font>");
				} else {
					stringBuilder.append(privilege.getName());

				}
				stringBuilder.append("</b>");

				stringBuilder.append("<ul>");
				generateHtmlCheckBox(privilege.getChilds(), stringBuilder,
						initialModuleIds);
				stringBuilder.append("</ul>");

			} else {
				StringBuilder checkBoxBuilder = new StringBuilder();
				checkBoxBuilder
						.append("<input type='checkbox' name='newModuleIds' value='"
								+ privilege.getId() + "' ");
				if (initialModuleIds.contains(privilege.getId())) {
					checkBoxBuilder.append("checked='checked'");
				}
				checkBoxBuilder.append("/>");
				checkBoxBuilder.append(privilege.getName());

				stringBuilder.append("<label>");
				if (privilege.getParentId() == null
						|| privilege.getParentId().trim().isEmpty()) {
					stringBuilder.append("<b>");
					stringBuilder.append("<hr />");
					stringBuilder.append("<font color='#0000BB'>");
					stringBuilder.append(checkBoxBuilder);
					stringBuilder.append("</font>");
					stringBuilder.append("</b>");
				} else {
					stringBuilder.append(checkBoxBuilder);
				}
				stringBuilder.append("</label>");
			}

			stringBuilder.append("</li>");
		}
	}

	private static void generateHtmlTree(TreeMap<String, Privilege> treeMap,
			StringBuilder stringBuilder) {

		Collection<Privilege> nodes = treeMap.values();
		Iterator<Privilege> iterator = nodes.iterator();
		while (iterator.hasNext()) {

			Privilege privilege = iterator.next();

			stringBuilder.append("<li>");

			if (!privilege.getChilds().isEmpty()) {
				stringBuilder.append("<b>");
				if (privilege.getParentId() == null
						|| privilege.getParentId().trim().isEmpty()) {
					stringBuilder.append("<hr />");
					stringBuilder.append("<font color='#0000BB'>");
					stringBuilder.append(privilege.getName());
					stringBuilder.append("</font>");
				} else {
					stringBuilder.append(privilege.getName());
				}
				stringBuilder.append("</b>");

				stringBuilder.append("<ul>");
				generateHtmlTree(privilege.getChilds(), stringBuilder);
				stringBuilder.append("</ul>");

			} else {
				if (privilege.getParentId() == null
						|| privilege.getParentId().trim().isEmpty()) {
					stringBuilder.append("<b>");
					stringBuilder.append("<hr />");
					stringBuilder.append("<font color='#0000BB'>");
					stringBuilder.append(privilege.getName());
					stringBuilder.append("</font>");
					stringBuilder.append("<b>");
				} else {
					stringBuilder.append(privilege.getName());
				}
			}

			stringBuilder.append("</li>");
		}
	}
}
