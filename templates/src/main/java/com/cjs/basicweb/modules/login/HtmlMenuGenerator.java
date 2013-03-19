package com.cjs.basicweb.modules.login;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.cjs.basicweb.model.module.Module;

public class HtmlMenuGenerator {

	public static String generateHtmlMenu(TreeMap<String, Privilege> treeMap,
			String contextPath) {
		StringBuilder stringBuilder = new StringBuilder();
		generateHtmlMenu(treeMap, stringBuilder, contextPath);
		return stringBuilder.toString();
	}

	public static HashMap<String, Privilege> registerAllModule(
			List<Module> modules) {
		HashMap<String, Privilege> privileges = new HashMap<>();
		for (Module module : modules) {
			registerAllModule(module, privileges);
		}
		return privileges;
	}

	private static void generateHtmlMenu(TreeMap<String, Privilege> treeMap,
			StringBuilder stringBuilder, String contextPath) {

		Collection<Privilege> nodes = treeMap.values();
		Iterator<Privilege> iterator = nodes.iterator();
		while (iterator.hasNext()) {

			stringBuilder.append("<li>");
			Privilege privilege = iterator.next();

			if (privilege.getAction() != null
					&& !privilege.getAction().trim().isEmpty()
					&& privilege.getChilds().isEmpty()) {
				stringBuilder.append("<a href='" + contextPath
						+ privilege.getAction() + "?initial=true" + "'>");
				stringBuilder.append(privilege.getName());
				stringBuilder.append("</a>");
			} else {
				stringBuilder.append(privilege.getName());
			}

			if (!privilege.getChilds().isEmpty()) {
				stringBuilder.append("<ul>");
				generateHtmlMenu(privilege.getChilds(), stringBuilder,
						contextPath);
				stringBuilder.append("</ul>");
			}

			stringBuilder.append("</li>");
		}

	}

	private static void registerAllModule(Module module,
			HashMap<String, Privilege> privileges) {

		if (!privileges.containsKey(module.getId())) {
			String parentId = null;
			if (module.getParent() != null) {
				parentId = module.getParent().getId();
			}

			privileges.put(module.getId(),
					new Privilege(module.getId(), module.getName(), parentId,
							module.getFirstEntry()));
		}

		for (Module child : module.getChilds()) {
			registerAllModule(child, privileges);
		}
	}
}
