package com.ganesha.plugin.basicweb.wizards.newmodule;

import static com.ganesha.plugin.basicweb.Constants.BL_VAR;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.ganesha.plugin.Utils;
import com.ganesha.plugin.basicweb.Constants;

public class NewModuleWizard extends Wizard implements INewWizard {
	private ISelection selection;

	private String moduleName;
	private String prefixClassName;
	private Properties properties;
	private String entitySimpleName;
	private String entityFullName;

	public NewModuleWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		addPage(new NewModuleWizardPage());
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	@Override
	public boolean performFinish() {
		NewModuleWizardPage page = (NewModuleWizardPage) getPage(NewModuleWizardPage.class
				.getName());

		moduleName = page.getModuleName();

		while (moduleName.contains("  ")) {
			moduleName = moduleName.replaceAll("  ", " ");
		}
		String[] moduleNames = moduleName.split(" ");
		prefixClassName = "";
		for (String string : moduleNames) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(string.substring(0, 1).toUpperCase());
			stringBuilder.append(string.substring(1).toLowerCase());
			prefixClassName += stringBuilder.toString();
		}

		@SuppressWarnings("restriction")
		String fullyQualifiedName = page.getEntity().getFullyQualifiedName();
		entityFullName = fullyQualifiedName;

		@SuppressWarnings("restriction")
		String elementName = page.getEntity().getElementName();
		entitySimpleName = elementName;

		IRunnableWithProgress op = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error",
					realException.getMessage());
			return false;
		}
		return true;
	}

	private void createAction(String modulePath, IProject project,
			IProgressMonitor monitor) throws CoreException {

		String actionPath = modulePath + IPath.SEPARATOR + "action";
		String packageName = properties
				.getProperty(Constants.COM_GANESHA_CLIENT_BASEPACKAGE_MODULES)
				+ "." + moduleName.replace(" ", "").toLowerCase();

		IFolder folder = project.getFolder(actionPath);
		Utils.createResource(folder, monitor);

		List<InputStream> inputStreams = new ArrayList<>();

		{ // base action
			IFile baseAction = project.getFile(actionPath + IPath.SEPARATOR
					+ prefixClassName + "Action.java");
			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.BASE_ACTION_VAR,
					baseAction.getName().replace(".java", ""));
			map.put(Constants.FORM_VAR, prefixClassName + "Form");
			map.put(Constants.BL_VAR, prefixClassName + "BL");
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);

			String entityVarName = entitySimpleName.substring(0, 1)
					.toLowerCase() + entitySimpleName.substring(1);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			InputStream inputStream = openContentStream("template/BaseAction",
					map);
			inputStreams.add(inputStream);

			createFile(baseAction, inputStream, monitor);
		}

		{ // main action
			IFile mainAction = project.getFile(actionPath + IPath.SEPARATOR
					+ prefixClassName + "MainAction.java");
			String baseAction = prefixClassName + "Action";

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.MAIN_ACTION_VAR,
					mainAction.getName().replace(".java", ""));
			map.put(Constants.BASE_ACTION_VAR, baseAction);
			map.put(Constants.FORM_VAR, prefixClassName + "Form");
			map.put(Constants.BL_VAR, prefixClassName + "BL");
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);

			String entityVarName = entitySimpleName.substring(0, 1)
					.toLowerCase() + entitySimpleName.substring(1);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			InputStream inputStream = openContentStream("template/MainAction",
					map);
			inputStreams.add(inputStream);

			createFile(mainAction, inputStream, monitor);
		}

		{ // create action
			IFile createAction = project.getFile(actionPath + IPath.SEPARATOR
					+ prefixClassName + "CreateAction.java");
			String baseAction = prefixClassName + "Action";

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.CREATE_ACTION_VAR, createAction.getName()
					.replace(".java", ""));
			map.put(Constants.BASE_ACTION_VAR, baseAction);
			map.put(Constants.FORM_VAR, prefixClassName + "Form");
			map.put(Constants.BL_VAR, prefixClassName + "BL");
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);

			String entityVarName = entitySimpleName.substring(0, 1)
					.toLowerCase() + entitySimpleName.substring(1);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			InputStream inputStream = openContentStream(
					"template/CreateAction", map);
			inputStreams.add(inputStream);

			createFile(createAction, inputStream, monitor);
		}

		{ // delete action
			IFile deleteAction = project.getFile(actionPath + IPath.SEPARATOR
					+ prefixClassName + "DeleteAction.java");
			String baseAction = prefixClassName + "Action";

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.DELETE_ACTION_VAR, deleteAction.getName()
					.replace(".java", ""));
			map.put(Constants.BASE_ACTION_VAR, baseAction);
			map.put(Constants.FORM_VAR, prefixClassName + "Form");
			map.put(Constants.BL_VAR, prefixClassName + "BL");
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);

			String entityVarName = entitySimpleName.substring(0, 1)
					.toLowerCase() + entitySimpleName.substring(1);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			InputStream inputStream = openContentStream(
					"template/DeleteAction", map);
			inputStreams.add(inputStream);

			createFile(deleteAction, inputStream, monitor);
		}

		{ // update action
			IFile updateAction = project.getFile(actionPath + IPath.SEPARATOR
					+ prefixClassName + "UpdateAction.java");
			String baseAction = prefixClassName + "Action";

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.UPDATE_ACTION_VAR, updateAction.getName()
					.replace(".java", ""));
			map.put(Constants.BASE_ACTION_VAR, baseAction);
			map.put(Constants.FORM_VAR, prefixClassName + "Form");
			map.put(Constants.BL_VAR, prefixClassName + "BL");
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);

			String entityVarName = entitySimpleName.substring(0, 1)
					.toLowerCase() + entitySimpleName.substring(1);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			InputStream inputStream = openContentStream(
					"template/UpdateAction", map);
			inputStreams.add(inputStream);

			createFile(updateAction, inputStream, monitor);
		}
	}

	private void createFile(IFile file, InputStream contentStream,
			IProgressMonitor monitor) throws CoreException {

		if (file.exists()) {
			file.setContents(contentStream, true, true, monitor);
		} else {
			file.create(contentStream, true, monitor);
		}
	}

	private void createForm(String modulePath, IProject project,
			IProgressMonitor monitor) throws CoreException {
		String packageName = properties
				.getProperty(Constants.COM_GANESHA_CLIENT_BASEPACKAGE_MODULES)
				+ "." + moduleName.replace(" ", "").toLowerCase();

		List<InputStream> inputStreams = new ArrayList<>();

		{ // business logic class
			IFile businessLogic = project.getFile(modulePath + IPath.SEPARATOR
					+ prefixClassName + "Form.java");
			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.FORM_VAR,
					businessLogic.getName().replace(".java", ""));
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);

			String entityVarName = entitySimpleName.substring(0, 1)
					.toLowerCase() + entitySimpleName.substring(1);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			InputStream inputStream = openContentStream("template/Form", map);
			inputStreams.add(inputStream);

			createFile(businessLogic, inputStream, monitor);
		}
	}

	private void createLogic(String modulePath, IProject project,
			IProgressMonitor monitor) throws CoreException {
		String packageName = properties
				.getProperty(Constants.COM_GANESHA_CLIENT_BASEPACKAGE_MODULES)
				+ "." + moduleName.replace(" ", "").toLowerCase();

		List<InputStream> inputStreams = new ArrayList<>();

		{ // business logic class
			IFile businessLogic = project.getFile(modulePath + IPath.SEPARATOR
					+ prefixClassName + "BL.java");
			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(BL_VAR, businessLogic.getName().replace(".java", ""));
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);

			String entityVarName = entitySimpleName.substring(0, 1)
					.toLowerCase() + entitySimpleName.substring(1);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			InputStream inputStream = openContentStream("template/BL", map);
			inputStreams.add(inputStream);

			createFile(businessLogic, inputStream, monitor);
		}
	}

	private void createModule(String javaSource, IProject project,
			IProgressMonitor monitor) throws CoreException {

		String basePackage = javaSource
				+ IPath.SEPARATOR
				+ (String) properties
						.get(Constants.COM_GANESHA_CLIENT_BASEPACKAGE_MODULES);

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(basePackage);
		stringBuilder.append(".").append(
				moduleName.replace(" ", "").toLowerCase());

		String modulePath = stringBuilder.toString().replace('.',
				IPath.SEPARATOR);

		IFolder folder = project.getFolder(modulePath);
		Utils.createResource(folder, monitor);

		createLogic(modulePath, project, monitor);
		createForm(modulePath, project, monitor);
		createAction(modulePath, project, monitor);
	}

	private void createNLSProperties() {

	}

	private void createStrutsXml() {

	}

	private void doFinish(IProgressMonitor monitor) throws CoreException {

		InputStream inputStream = null;
		try {

			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1) {
				return;
			}

			IResource resource = (IResource) ssel.getFirstElement();
			IProject project = resource.getProject();
			IFile file = (IFile) project.findMember("/.ganesha");

			inputStream = file.getContents();

			if (properties == null) {
				properties = new Properties();
			} else {
				properties.clear();
			}
			properties.load(inputStream);

			createModule(Constants.JAVA_SOURCE, project, monitor);

		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, this.getClass()
					.getName(), IStatus.OK, e.getLocalizedMessage(), null);
			throw new CoreException(status);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					IStatus status = new Status(IStatus.ERROR, this.getClass()
							.getName(), IStatus.OK, e.getLocalizedMessage(),
							null);
					throw new CoreException(status);
				}
			}
		}

		// IResource resource = root.findMember(new Path(moduleName));
		// if (!resource.exists() || !(resource instanceof IContainer)) {
		// throwCoreException("Container \"" + moduleName
		// + "\" does not exist.");
		// }
		// IContainer container = (IContainer) resource;
		// final IFile file = container.getFile(new Path(fileName));
		// try {
		// InputStream stream = openContentStream();
		// if (file.exists()) {
		// file.setContents(stream, true, true, monitor);
		// } else {
		// file.create(stream, true, monitor);
		// }
		// stream.close();
		// } catch (IOException e) {
		// }
		// monitor.worked(1);
		// monitor.setTaskName("Opening file for editing...");
		// getShell().getDisplay().asyncExec(new Runnable() {
		// @Override
		// public void run() {
		// IWorkbenchPage page = PlatformUI.getWorkbench()
		// .getActiveWorkbenchWindow().getActivePage();
		// try {
		// IDE.openEditor(page, file, true);
		// } catch (PartInitException e) {
		// }
		// }
		// });
		// monitor.worked(1);
	}

	private InputStream openContentStream(String filePath,
			Map<String, String> map) throws CoreException {

		String newLine = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();

		InputStream input = null;
		BufferedReader reader = null;

		try {
			input = this.getClass().getResourceAsStream(filePath);
			reader = new BufferedReader(new InputStreamReader(input));

			String line;
			while ((line = reader.readLine()) != null) {
				Iterator<String> iterator = map.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					String value = map.get(key);
					line = line.replaceAll("\\" + key, value);
				}
				sb.append(line);
				sb.append(newLine);
			}

			return new ByteArrayInputStream(sb.toString().getBytes());

		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, this.getClass()
					.getName(), IStatus.OK, e.getLocalizedMessage(), null);
			throw new CoreException(status);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					IStatus status = new Status(IStatus.ERROR, this.getClass()
							.getName(), IStatus.OK, e.getLocalizedMessage(),
							null);
					throw new CoreException(status);
				}
			}
		}
	}
}