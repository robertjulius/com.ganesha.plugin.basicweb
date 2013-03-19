package com.ganesha.plugin.basicweb;

import java.util.ArrayList;
import java.util.List;

public class Templates {
	private static final List<String> files = new ArrayList<>();

	static {

	}

	public static int count() {
		return files.size();
	}

	public static String get(int index) {
		return files.get(index);
	}
}
