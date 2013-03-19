package com.cjs.basicweb.modules.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.cjs.basicweb.model.module.Module;

public class PrivilegeUtils {

	public static TreeMap<String, Privilege> generateTree(
			String[] privilegeIds, List<Module> AllModules) {
		TreeMap<String, Privilege> treeMap = new TreeMap<String, Privilege>();
		createTreeMap(treeMap, AllModules);

		ArrayList<Privilege> leafs = new ArrayList<Privilege>();
		getLeafs(leafs, treeMap);

		privilegeIds = getValidPrivilegeIds(privilegeIds, leafs);

		while (!isEquals(privilegeIds, leafs)) {
			for (Privilege leaf : leafs) {
				if (!isExists(leaf.getId(), privilegeIds)) {
					if (leaf.getParentId() == null) {
						treeMap.remove(leaf.getId());
					} else {
						Privilege parent = getPrivilegeFromTree(
								leaf.getParentId(), treeMap);
						parent.getChilds().remove(leaf.getId());
					}
				}
			}
			leafs.clear();
			getLeafs(leafs, treeMap);

		}

		return treeMap;
	}

	private static void createTreeMap(TreeMap<String, Privilege> treeMap,
			List<Module> modules) {
		HashMap<String, Privilege> hashMap = HtmlMenuGenerator
				.registerAllModule(modules);

		Iterator<String> iterator = hashMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Privilege child = hashMap.get(key);
			Privilege parent = hashMap.get(child.getParentId());
			if (parent != null) {
				parent.addChild(child);
			} else {
				treeMap.put(child.getId(), child);
			}
		}
	}

	private static void getLeafs(ArrayList<Privilege> leafs,
			TreeMap<String, Privilege> treeMap) {
		Iterator<String> iterator = treeMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Privilege privilege = treeMap.get(key);
			if (privilege.getChilds().isEmpty()) {
				leafs.add(privilege);
			} else {
				getLeafs(leafs, privilege.getChilds());
			}
		}
	}

	private static Privilege getPrivilegeFromTree(String key,
			TreeMap<String, Privilege> treeMap) {
		if (treeMap.containsKey(key)) {
			return treeMap.get(key);
		}

		Iterator<String> iterator = treeMap.keySet().iterator();
		while (iterator.hasNext()) {
			String privilegeId = iterator.next();
			Privilege privilege = treeMap.get(privilegeId);
			return getPrivilegeFromTree(key, privilege.getChilds());
		}

		return null;
	}

	private static String[] getValidPrivilegeIds(String[] privilegeIds,
			ArrayList<Privilege> leafs) {
		List<String> valid = new ArrayList<>();
		for (String privilegeId : privilegeIds) {
			for (Privilege leaf : leafs) {
				if (privilegeId.equals(leaf.getId())) {
					valid.add(privilegeId);
					break;
				}
			}
		}
		return valid.toArray(new String[] {});
	}

	private static boolean isEquals(String[] privilegeIds,
			ArrayList<Privilege> leafs) {
		if (privilegeIds.length != leafs.size()) {
			return false;
		}

		int equals = 0;
		for (String privilegeId : privilegeIds) {
			for (Privilege leaf : leafs) {
				if (leaf.getId().equals(privilegeId)) {
					equals += 1;
					break;
				}
			}
		}

		return equals == leafs.size();
	}

	private static boolean isExists(String string, String[] strings) {
		for (String temp : strings) {
			if (string.equals(temp)) {
				return true;
			}
		}
		return false;
	}
}
