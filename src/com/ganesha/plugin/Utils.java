package com.ganesha.plugin;import java.io.ByteArrayInputStream;import java.io.File;import java.io.FilenameFilter;import java.io.IOException;import java.io.InputStream;import org.eclipse.core.resources.IFile;import org.eclipse.core.resources.IFolder;import org.eclipse.core.resources.IProject;import org.eclipse.core.resources.IResource;import org.eclipse.core.runtime.CoreException;import org.eclipse.core.runtime.IProgressMonitor;public class Utils {	public static void createResource(IResource resource, IProgressMonitor monitor)			throws CoreException {		if (resource == null || resource.exists()) {			return;		}		if (!resource.getParent().exists()) {			createResource(resource.getParent(), monitor);		}		switch (resource.getType()) {		case IResource.FILE:			((IFile) resource).create(new ByteArrayInputStream(new byte[0]),					true, monitor);			break;		case IResource.FOLDER:			((IFolder) resource).create(IResource.NONE, true, monitor);			break;		case IResource.PROJECT:			((IProject) resource).create(monitor);			((IProject) resource).open(monitor);			break;		}	}	public static boolean isAsciiText(InputStream inputStream)			throws IOException {		byte[] ascii = new byte[] { 9, 10, 13, 0 };		byte[] bytes = new byte[64];		inputStream.read(bytes, 0, bytes.length);		for (int i = 0; i < bytes.length; i++) {			byte b = bytes[i];			if ((b < 32 || b > 127)) {				boolean valid = false;				for (byte bb : ascii) {					if (bb == b) {						valid = true;						break;					}				}				if (!valid) {					return false;				}			}		}		return true;	}	public static boolean isAsciiText(String filePath) throws IOException {		InputStream inputStream = null;		try {			inputStream = Utils.class.getClassLoader().getResourceAsStream(					filePath);			return isAsciiText(inputStream);		} finally {			if (inputStream != null) {				inputStream.close();			}		}	}	public static void main(String[] args) throws Exception {		File file = new File(Utils.class.getClassLoader().getResource("")				.toURI());		file = file.getParentFile().listFiles(new FilenameFilter() {			@Override			public boolean accept(File dir, String name) {				if (name.equals("templates")) {					return true;				} else {					return false;				}			}		})[0];		scanFilesRecursively(file);	}	public static void scanFilesRecursively(File file) {		File[] childs = file.listFiles();		if (childs != null && childs.length > 0) {			for (File child : childs) {				scanFilesRecursively(child);			}		} else {			if (file.isFile()) {				System.out.println("files.add(\"" + file.getPath() + "\");");			} else {				/*				 * TODO				 */			}		}	}}