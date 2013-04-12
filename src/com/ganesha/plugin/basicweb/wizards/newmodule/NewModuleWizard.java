package com.ganesha.plugin.basicweb.wizards.newmodule;

import static com.ganesha.plugin.basicweb.Constants.CLASS_BL_VAR;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.ganesha.plugin.basicweb.wizards.RowItem;

public class NewModuleWizard extends Wizard implements INewWizard {
	private ISelection selection;

	private String moduleName;
	private String prefixClassName;
	private Properties properties;
	private String entitySimpleName;
	private String entityFullName;
	private List<RowItem> criterias;
	private List<RowItem> modifyFields;

	public NewModuleWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		addPage(new NewModuleWizardPage());
		addPage(new BasicCRUDMainPage());
		addPage(new BasicCRUDDetailPage());
		addPage(new BasicCRUDModifyPage());
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

		BasicCRUDMainPage mainPage = (BasicCRUDMainPage) getPage(BasicCRUDMainPage.NAME);
		this.criterias = mainPage.getSearchCriterias();

		BasicCRUDModifyPage modifyPage = (BasicCRUDModifyPage) getPage(BasicCRUDModifyPage.NAME);
		this.modifyFields = modifyPage.getModifyFields();

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

	private void appendRootStrutsXml(IProject project, IProgressMonitor monitor)
			throws CoreException {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append(properties
						.getProperty(Constants.COM_GANESHA_CLIENT_BASEPACKAGE_MODULES))
				.append(".").append(prefixClassName.toLowerCase()).append(".")
				.append(prefixClassName.toLowerCase());

		String moduleXmlPath = stringBuilder.toString().replace('.',
				IPath.SEPARATOR)
				+ "-struts.xml";

		InputStream inputStream = null;
		try {
			IFile strutsXml = project.getFile(Constants.RESOURCE
					+ IPath.SEPARATOR + "struts.xml");

			Map<String, String> map = new HashMap<>();

			String newIncludeLine = "<include file=\"" + moduleXmlPath
					+ "\"></include>";
			map.put(Constants.STRUTS_NEW_MODULE_LINE, newIncludeLine + "\n\t"
					+ Constants.STRUTS_NEW_MODULE_LINE);

			inputStream = Utils.openContentStream(strutsXml, map,
					this.getClass());
			createFile(strutsXml, inputStream, monitor);
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
	}

	private void createAction(String modulePath, IProject project,
			IProgressMonitor monitor) throws CoreException {

		String actionPath = modulePath + IPath.SEPARATOR + "action";
		String packageName = properties
				.getProperty(Constants.COM_GANESHA_CLIENT_BASEPACKAGE_MODULES)
				+ "." + prefixClassName.toLowerCase();

		IFolder folder = project.getFolder(actionPath);
		Utils.createResource(folder, monitor);

		List<InputStream> inputStreams = new ArrayList<>();

		{ // base action
			IFile baseAction = project.getFile(actionPath + IPath.SEPARATOR
					+ prefixClassName + "Action.java");

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.CLASS_ACTION_BASE,
					baseAction.getName().replace(".java", ""));
			map.put(Constants.CLASS_FORM_VAR, prefixClassName + "Form");
			map.put(Constants.CLASS_BL_VAR, prefixClassName + "BL");
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);

			String entityVarName = entitySimpleName.substring(0, 1)
					.toLowerCase() + entitySimpleName.substring(1);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			InputStream inputStream = Utils.openContentStream(
					"template/BaseAction", map, this.getClass(), false);
			inputStreams.add(inputStream);

			createFile(baseAction, inputStream, monitor);
		}

		{ // main action
			IFile mainAction = project.getFile(actionPath + IPath.SEPARATOR
					+ prefixClassName + "MainAction.java");
			String baseAction = prefixClassName + "Action";

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.CLASS_ACTION_MAIN,
					mainAction.getName().replace(".java", ""));
			map.put(Constants.CLASS_ACTION_BASE, baseAction);
			map.put(Constants.CLASS_FORM_VAR, prefixClassName + "Form");
			map.put(Constants.CLASS_BL_VAR, prefixClassName + "BL");
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);

			{ // Create search criterias
				StringBuilder stringBuilder = new StringBuilder();
				for (RowItem rowItem : criterias) {
					String getter = rowItem.getName();
					getter = new StringBuilder("get")
							.append(getter.substring(0, 1).toUpperCase())
							.append(getter.substring(1)).append("()")
							.toString();
					stringBuilder.append("\t\t").append(rowItem.getType())
							.append(" ").append(rowItem.getName())
							.append(" = form.").append(getter).append(";\n");
				}
				map.put(Constants.LIST_OF_SEARCH_CRITERIA, stringBuilder
						.toString().replaceFirst("\t\t", ""));
			}

			String entityVarName = entitySimpleName.substring(0, 1)
					.toLowerCase() + entitySimpleName.substring(1);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			InputStream inputStream = Utils.openContentStream(
					"template/MainAction", map, this.getClass(), false);
			inputStreams.add(inputStream);

			createFile(mainAction, inputStream, monitor);
		}

		{ // create action
			IFile createAction = project.getFile(actionPath + IPath.SEPARATOR
					+ prefixClassName + "CreateAction.java");
			String baseAction = prefixClassName + "Action";

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.CLASS_ACTION_CREATE, createAction.getName()
					.replace(".java", ""));
			map.put(Constants.CLASS_ACTION_BASE, baseAction);
			map.put(Constants.CLASS_FORM_VAR, prefixClassName + "Form");
			map.put(Constants.CLASS_BL_VAR, prefixClassName + "BL");
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);

			String entityVarName = entitySimpleName.substring(0, 1)
					.toLowerCase() + entitySimpleName.substring(1);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			InputStream inputStream = Utils.openContentStream(
					"template/CreateAction", map, this.getClass(), false);
			inputStreams.add(inputStream);

			createFile(createAction, inputStream, monitor);
		}

		{ // delete action
			IFile deleteAction = project.getFile(actionPath + IPath.SEPARATOR
					+ prefixClassName + "DeleteAction.java");
			String baseAction = prefixClassName + "Action";

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.CLASS_ACTION_DELETE, deleteAction.getName()
					.replace(".java", ""));
			map.put(Constants.CLASS_ACTION_BASE, baseAction);
			map.put(Constants.CLASS_FORM_VAR, prefixClassName + "Form");
			map.put(Constants.CLASS_BL_VAR, prefixClassName + "BL");
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);

			String entityVarName = entitySimpleName.substring(0, 1)
					.toLowerCase() + entitySimpleName.substring(1);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			InputStream inputStream = Utils.openContentStream(
					"template/DeleteAction", map, this.getClass(), false);
			inputStreams.add(inputStream);

			createFile(deleteAction, inputStream, monitor);
		}

		{ // update action
			IFile updateAction = project.getFile(actionPath + IPath.SEPARATOR
					+ prefixClassName + "UpdateAction.java");
			String baseAction = prefixClassName + "Action";

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.CLASS_ACTION_UPDATE, updateAction.getName()
					.replace(".java", ""));
			map.put(Constants.CLASS_ACTION_BASE, baseAction);
			map.put(Constants.CLASS_FORM_VAR, prefixClassName + "Form");
			map.put(Constants.CLASS_BL_VAR, prefixClassName + "BL");
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);

			String entityVarName = entitySimpleName.substring(0, 1)
					.toLowerCase() + entitySimpleName.substring(1);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			InputStream inputStream = Utils.openContentStream(
					"template/UpdateAction", map, this.getClass(), false);
			inputStreams.add(inputStream);

			createFile(updateAction, inputStream, monitor);
		}

		for (InputStream inputStream : inputStreams) {
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
				+ "." + prefixClassName.toLowerCase();

		InputStream inputStream = null;
		try {
			IFile businessLogic = project.getFile(modulePath + IPath.SEPARATOR
					+ prefixClassName + "Form.java");

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.CLASS_FORM_VAR,
					businessLogic.getName().replace(".java", ""));
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);

			{ // Create search criterias
				StringBuilder stringBuilder = new StringBuilder();
				for (RowItem rowItem : criterias) {
					stringBuilder.append("\tprivate").append(" ")
							.append(rowItem.getType()).append(" ")
							.append(rowItem.getName()).append(";").append("\n");
				}
				map.put(Constants.LIST_OF_SEARCH_CRITERIA, stringBuilder
						.toString().replaceFirst("\t", ""));
			}

			{ // Create getter setter for search criterias
				StringBuilder stringBuilder = new StringBuilder();
				for (RowItem rowItem : criterias) {
					String getter = rowItem.getName();
					getter = new StringBuilder("get")
							.append(getter.substring(0, 1).toUpperCase())
							.append(getter.substring(1)).append("()")
							.toString();
					String setter = rowItem.getName();
					setter = new StringBuilder("set")
							.append(setter.substring(0, 1).toUpperCase())
							.append(setter.substring(1)).append("(")
							.append(rowItem.getType()).append(" ")
							.append(rowItem.getName()).append(")").toString();
					stringBuilder.append("\tpublic").append(" ")
							.append(rowItem.getType()).append(" ")
							.append(getter).append(" ").append("{\n")
							.append("\t\t").append("return").append(" ")
							.append(rowItem.getName()).append(";\n")
							.append("\t}\n\n");
					stringBuilder.append("\tpublic void").append(" ")
							.append(setter).append(" ").append("{\n")
							.append("\t\t").append("this.")
							.append(rowItem.getName()).append(" = ")
							.append(rowItem.getName()).append(";\n")
							.append("\t}\n\n");
				}
				map.put(Constants.LIST_OF_GETTER_SETTER_SEARCH_CRITERIA,
						stringBuilder.toString().replaceFirst("\t", ""));
			}

			{ // Create modify fields
				StringBuilder stringBuilder = new StringBuilder();
				for (RowItem rowItem : modifyFields) {
					stringBuilder.append("\tprivate").append(" ")
							.append(rowItem.getType()).append(" ")
							.append(rowItem.getName()).append(";").append("\n");
				}
				map.put(Constants.LIST_OF_MODIFY_FIELD, stringBuilder
						.toString().replaceFirst("\t", ""));
			}

			{ // Create getter setter for modify fields
				StringBuilder stringBuilder = new StringBuilder();
				for (RowItem rowItem : modifyFields) {
					String getter = rowItem.getName();
					getter = new StringBuilder("get")
							.append(getter.substring(0, 1).toUpperCase())
							.append(getter.substring(1)).append("()")
							.toString();
					String setter = rowItem.getName();
					setter = new StringBuilder("set")
							.append(setter.substring(0, 1).toUpperCase())
							.append(setter.substring(1)).append("(")
							.append(rowItem.getType()).append(" ")
							.append(rowItem.getName()).append(")").toString();
					stringBuilder.append("\tpublic").append(" ")
							.append(rowItem.getType()).append(" ")
							.append(getter).append(" ").append("{\n")
							.append("\t\t").append("return").append(" ")
							.append(rowItem.getName()).append(";\n")
							.append("\t}\n\n");
					stringBuilder.append("\tpublic void").append(" ")
							.append(setter).append(" ").append("{\n")
							.append("\t\t").append("this.")
							.append(rowItem.getName()).append(" = ")
							.append(rowItem.getName()).append(";\n")
							.append("\t}\n\n");
				}
				map.put(Constants.LIST_OF_GETTER_SETTER_MODIFY_FIELD,
						stringBuilder.toString().replaceFirst("\t", ""));
			}

			String entityVarName = entitySimpleName.substring(0, 1)
					.toLowerCase() + entitySimpleName.substring(1);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			inputStream = Utils.openContentStream("template/Form", map,
					this.getClass(), false);
			createFile(businessLogic, inputStream, monitor);
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
	}

	private void createJsp(String webApp, IProject project,
			IProgressMonitor monitor) throws CoreException {

		String basePackage = webApp + IPath.SEPARATOR + "jsp" + IPath.SEPARATOR
				+ "modules";

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(basePackage);
		stringBuilder.append(".").append(prefixClassName.toLowerCase());

		String modulePath = stringBuilder.toString().replace('.',
				IPath.SEPARATOR);

		IFolder folder = project.getFolder(modulePath);
		if (folder.exists()) {
			Exception e = new Exception("Cannot create module '" + moduleName
					+ "' because directory " + folder.getFullPath()
					+ " already exists");
			IStatus status = new Status(IStatus.ERROR, this.getClass()
					.getName(), IStatus.OK, e.getMessage(), e);
			throw new CoreException(status);
		}
		Utils.createResource(folder, monitor);

		createJspMain(modulePath, project, monitor);
		createJspDetail(modulePath, project, monitor);
		createJspCreate(modulePath, project, monitor);
		createJspConfirmCreate(modulePath, project, monitor);
		createJspUpdate(modulePath, project, monitor);
		createJspConfirmUpdate(modulePath, project, monitor);
	}

	private void createJspConfirmCreate(String modulePath, IProject project,
			IProgressMonitor monitor) throws CoreException {

		InputStream inputStream = null;
		try {
			IFile jspFile = project.getFile(modulePath
					+ IPath.SEPARATOR
					+ Utils.camelToHuman(prefixClassName).toLowerCase()
							.replace(' ', '_') + "_confirm_create.jsp");
			Map<String, String> map = new HashMap<>();
			map.put(Constants.STRUTS_PACKAGE_VAR, prefixClassName.toLowerCase());
			inputStream = Utils.openContentStream(
					"template/jsp_confirm_create", map, this.getClass(), false);
			createFile(jspFile, inputStream, monitor);
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
	}

	private void createJspConfirmUpdate(String modulePath, IProject project,
			IProgressMonitor monitor) throws CoreException {

		InputStream inputStream = null;
		try {
			IFile jspFile = project.getFile(modulePath
					+ IPath.SEPARATOR
					+ Utils.camelToHuman(prefixClassName).toLowerCase()
							.replace(' ', '_') + "_confirm_update.jsp");
			Map<String, String> map = new HashMap<>();
			map.put(Constants.STRUTS_PACKAGE_VAR, prefixClassName.toLowerCase());
			inputStream = Utils.openContentStream(
					"template/jsp_confirm_update", map, this.getClass(), false);
			createFile(jspFile, inputStream, monitor);
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
	}

	private void createJspCreate(String modulePath, IProject project,
			IProgressMonitor monitor) throws CoreException {

		InputStream inputStream = null;
		try {
			IFile jspFile = project.getFile(modulePath
					+ IPath.SEPARATOR
					+ Utils.camelToHuman(prefixClassName).toLowerCase()
							.replace(' ', '_') + "_create.jsp");
			Map<String, String> map = new HashMap<>();
			map.put(Constants.STRUTS_PACKAGE_VAR, prefixClassName.toLowerCase());
			inputStream = Utils.openContentStream("template/jsp_create", map,
					this.getClass(), false);
			createFile(jspFile, inputStream, monitor);
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
	}

	private void createJspDetail(String modulePath, IProject project,
			IProgressMonitor monitor) throws CoreException {

		InputStream inputStream = null;
		try {
			IFile jspFile = project.getFile(modulePath
					+ IPath.SEPARATOR
					+ Utils.camelToHuman(prefixClassName).toLowerCase()
							.replace(' ', '_') + "_detail.jsp");
			Map<String, String> map = new HashMap<>();
			map.put(Constants.STRUTS_PACKAGE_VAR, prefixClassName.toLowerCase());
			inputStream = Utils.openContentStream("template/jsp_detail", map,
					this.getClass(), false);
			createFile(jspFile, inputStream, monitor);
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
	}

	private void createJspMain(String modulePath, IProject project,
			IProgressMonitor monitor) throws CoreException {

		InputStream inputStream = null;
		try {
			IFile jspFile = project.getFile(modulePath
					+ IPath.SEPARATOR
					+ Utils.camelToHuman(prefixClassName).toLowerCase()
							.replace(' ', '_') + "_main.jsp");
			Map<String, String> map = new HashMap<>();
			map.put(Constants.STRUTS_PACKAGE_VAR, prefixClassName.toLowerCase());
			inputStream = Utils.openContentStream("template/jsp_main", map,
					this.getClass(), false);
			createFile(jspFile, inputStream, monitor);
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
	}

	private void createJspUpdate(String modulePath, IProject project,
			IProgressMonitor monitor) throws CoreException {

		InputStream inputStream = null;
		try {
			IFile jspFile = project.getFile(modulePath
					+ IPath.SEPARATOR
					+ Utils.camelToHuman(prefixClassName).toLowerCase()
							.replace(' ', '_') + "_update.jsp");
			Map<String, String> map = new HashMap<>();
			map.put(Constants.STRUTS_PACKAGE_VAR, prefixClassName.toLowerCase());
			inputStream = Utils.openContentStream("template/jsp_update", map,
					this.getClass(), false);
			createFile(jspFile, inputStream, monitor);
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
	}

	private void createLogic(String modulePath, IProject project,
			IProgressMonitor monitor) throws CoreException {
		String packageName = properties
				.getProperty(Constants.COM_GANESHA_CLIENT_BASEPACKAGE_MODULES)
				+ "." + prefixClassName.toLowerCase();

		InputStream inputStream = null;
		try {
			IFile businessLogic = project.getFile(modulePath + IPath.SEPARATOR
					+ prefixClassName + "BL.java");

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(CLASS_BL_VAR, businessLogic.getName().replace(".java", ""));
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);

			String entityVarName = entitySimpleName.substring(0, 1)
					.toLowerCase() + entitySimpleName.substring(1);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			inputStream = Utils.openContentStream("template/BL", map,
					this.getClass(), false);
			createFile(businessLogic, inputStream, monitor);
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
	}

	private void createModule(String javaSource, IProject project,
			IProgressMonitor monitor) throws CoreException {

		String basePackage = javaSource
				+ IPath.SEPARATOR
				+ (String) properties
						.get(Constants.COM_GANESHA_CLIENT_BASEPACKAGE_MODULES);

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(basePackage);
		stringBuilder.append(".").append(prefixClassName.toLowerCase());

		String modulePath = stringBuilder.toString().replace('.',
				IPath.SEPARATOR);

		IFolder folder = project.getFolder(modulePath);
		if (folder.exists()) {
			Exception e = new Exception("Cannot create module '" + moduleName
					+ "' because directory " + folder.getFullPath()
					+ " already exists");
			IStatus status = new Status(IStatus.ERROR, this.getClass()
					.getName(), IStatus.OK, e.getMessage(), e);
			throw new CoreException(status);
		}
		Utils.createResource(folder, monitor);

		createLogic(modulePath, project, monitor);
		createForm(modulePath, project, monitor);
		createAction(modulePath, project, monitor);
		createStrutsXml(modulePath, project, monitor);
		createNLSProperties(modulePath, project, monitor);
	}

	private void createNLSProperties(String modulePath, IProject project,
			IProgressMonitor monitor) throws CoreException {

		InputStream inputStream = null;
		try {
			IFile packageProperties = project.getFile(modulePath
					+ IPath.SEPARATOR + "package.properties");

			Map<String, String> map = new HashMap<>();
			map.put(Constants.RESOURCE_PAGE_TITLE, moduleName);

			inputStream = Utils.openContentStream("template/NLS", map,
					this.getClass(), false);
			createFile(packageProperties, inputStream, monitor);
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
	}

	private void createStrutsXml(String modulePath, IProject project,
			IProgressMonitor monitor) throws CoreException {

		InputStream inputStream = null;
		try {
			IFile strutsXml = project.getFile(modulePath + IPath.SEPARATOR
					+ prefixClassName.toLowerCase() + "-struts.xml");
			Map<String, String> map = new HashMap<>();
			map.put(Constants.STRUTS_PACKAGE_VAR, prefixClassName.toLowerCase());
			map.put(Constants.GANESHA_MODULES_VAR,
					properties
							.getProperty(Constants.COM_GANESHA_CLIENT_BASEPACKAGE_MODULES));
			map.put(Constants.CLASS_ACTION_MAIN, prefixClassName + "MainAction");
			map.put(Constants.CLASS_ACTION_CREATE, prefixClassName
					+ "CreateAction");
			map.put(Constants.CLASS_ACTION_UPDATE, prefixClassName
					+ "UpdateAction");
			map.put(Constants.CLASS_ACTION_DELETE, prefixClassName
					+ "DeleteAction");

			String prefixJspName = moduleName.replaceAll(" ", "_")
					.toLowerCase();
			map.put(Constants.JSP_MAIN, prefixJspName + "_main.jsp");
			map.put(Constants.JSP_DETAIL, prefixJspName + "_detail.jsp");
			map.put(Constants.JSP_UPDATE, prefixJspName + "_update.jsp");
			map.put(Constants.JSP_UPDATE_CONFIRM, prefixJspName
					+ "_confirm_update.jsp");
			map.put(Constants.JSP_CREATE, prefixJspName + "_create.jsp");
			map.put(Constants.JSP_CREATE_CONFIRM, prefixJspName
					+ "_confirm_create.jsp");

			inputStream = Utils.openContentStream("template/struts", map,
					this.getClass(), false);
			createFile(strutsXml, inputStream, monitor);

			appendRootStrutsXml(project, monitor);

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
			if (file == null) {
				Exception e = new Exception(
						"Project '"
								+ project.getName()
								+ "' is not a valid Ganesha Basic Web Project. Cannot create '"
								+ moduleName + "' in this project.");
				IStatus status = new Status(IStatus.ERROR, this.getClass()
						.getName(), IStatus.OK, e.getLocalizedMessage(), e);
				throw new CoreException(status);
			}

			inputStream = file.getContents();

			if (properties == null) {
				properties = new Properties();
			} else {
				properties.clear();
			}
			properties.load(inputStream);

			createModule(Constants.JAVA_SOURCE, project, monitor);
			createJsp(Constants.WEBAPP, project, monitor);

		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, this.getClass()
					.getName(), IStatus.OK, e.getLocalizedMessage(), e);
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
}