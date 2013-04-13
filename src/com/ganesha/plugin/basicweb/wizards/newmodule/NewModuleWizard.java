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
	private String entityVarName;
	private String entityFullName;
	private List<RowItem> criterias;
	private List<RowItem> searchResults;
	private List<RowItem> modifyFields;
	private List<RowItem> detailFields;

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

		entityVarName = new StringBuilder(entitySimpleName.substring(0, 1)
				.toLowerCase()).append(entitySimpleName.substring(1))
				.toString();

		BasicCRUDMainPage mainPage = (BasicCRUDMainPage) getPage(BasicCRUDMainPage.NAME);
		this.criterias = mainPage.getSearchCriterias();
		this.searchResults = mainPage.getSearchResult();

		BasicCRUDModifyPage modifyPage = (BasicCRUDModifyPage) getPage(BasicCRUDModifyPage.NAME);
		this.modifyFields = modifyPage.getFields();

		BasicCRUDDetailPage detailPage = (BasicCRUDDetailPage) getPage(BasicCRUDDetailPage.NAME);
		this.detailFields = detailPage.getFields();

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

		String moduleXmlPath = new StringBuilder(stringBuilder.toString()
				.replace('.', IPath.SEPARATOR)).append("-struts.xml")
				.toString();

		InputStream inputStream = null;
		try {
			IFile strutsXml = project.getFile(new StringBuilder(
					Constants.RESOURCE).append(IPath.SEPARATOR)
					.append("struts.xml").toString());

			Map<String, String> map = new HashMap<>();

			String newIncludeLine = new StringBuilder("<include file=\"")
					.append(moduleXmlPath).append("\"></include>").toString();
			map.put(Constants.STRUTS_NEW_MODULE_LINE,
					new StringBuilder(newIncludeLine).append("\n\t")
							.append(Constants.STRUTS_NEW_MODULE_LINE)
							.toString());

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

		String actionPath = new StringBuilder(modulePath)
				.append(IPath.SEPARATOR).append("action").toString();
		String packageName = new StringBuilder(
				properties
						.getProperty(Constants.COM_GANESHA_CLIENT_BASEPACKAGE_MODULES))
				.append(".").append(prefixClassName.toLowerCase()).toString();

		IFolder folder = project.getFolder(actionPath);
		Utils.createResource(folder, monitor);

		List<InputStream> inputStreams = new ArrayList<>();

		{ // base action
			IFile baseAction = project.getFile(new StringBuilder(actionPath)
					.append(IPath.SEPARATOR).append(prefixClassName)
					.append("Action.java").toString());

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.CLASS_ACTION_BASE,
					baseAction.getName().replace(".java", ""));
			map.put(Constants.CLASS_FORM_VAR,
					new StringBuilder(prefixClassName).append("Form")
							.toString());
			map.put(Constants.CLASS_BL_VAR, new StringBuilder(prefixClassName)
					.append("BL").toString());
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			InputStream inputStream = Utils.openContentStream(
					"template/BaseAction", map, this.getClass(), false);
			inputStreams.add(inputStream);

			createFile(baseAction, inputStream, monitor);
		}

		{ // main action
			IFile mainAction = project.getFile(new StringBuilder(actionPath)
					.append(IPath.SEPARATOR).append(prefixClassName)
					.append("MainAction.java").toString());
			String baseAction = new StringBuilder(prefixClassName).append(
					"Action").toString();

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.CLASS_ACTION_MAIN,
					mainAction.getName().replace(".java", ""));
			map.put(Constants.CLASS_ACTION_BASE, baseAction);
			map.put(Constants.CLASS_FORM_VAR,
					new StringBuilder(prefixClassName).append("Form")
							.toString());
			map.put(Constants.CLASS_BL_VAR, new StringBuilder(prefixClassName)
					.append("BL").toString());
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			{ // Create search criterias
				StringBuilder builderForInputFields = new StringBuilder();
				StringBuilder builderForParameters = new StringBuilder();
				for (RowItem rowItem : criterias) {
					String getter = rowItem.getName();
					getter = new StringBuilder("get")
							.append(getter.substring(0, 1).toUpperCase())
							.append(getter.substring(1)).append("()")
							.toString();
					builderForInputFields.append("\n")
							.append(rowItem.getType()).append(" ")
							.append(rowItem.getName()).append(" = form.")
							.append(getter).append(";");
					builderForParameters.append(", ").append(rowItem.getName());
				}
				map.put(Constants.LIST_OF_SEARCH_CRITERIA,
						builderForInputFields.toString().replaceFirst("\n", ""));
				map.put(Constants.LIST_OF_PARAMETER_SEARCH_CRITERIA,
						builderForParameters.toString().replaceFirst(", ", ""));
			}

			InputStream inputStream = Utils.openContentStream(
					"template/MainAction", map, this.getClass(), false);
			inputStreams.add(inputStream);

			createFile(mainAction, inputStream, monitor);
		}

		{ // create action
			IFile createAction = project.getFile(new StringBuilder(actionPath)
					.append(IPath.SEPARATOR).append(prefixClassName)
					.append("CreateAction.java").toString());
			String baseAction = new StringBuilder(prefixClassName).append(
					"Action").toString();

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.CLASS_ACTION_CREATE, createAction.getName()
					.replace(".java", ""));
			map.put(Constants.CLASS_ACTION_BASE, baseAction);
			map.put(Constants.CLASS_FORM_VAR,
					new StringBuilder(prefixClassName).append("Form")
							.toString());
			map.put(Constants.CLASS_BL_VAR, new StringBuilder(prefixClassName)
					.append("BL").toString());
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			{ // Create modify fields
				StringBuilder builderForInputFields = new StringBuilder();
				StringBuilder builderForParameters = new StringBuilder();
				for (RowItem rowItem : modifyFields) {
					String getter = rowItem.getName();
					getter = new StringBuilder("get")
							.append(getter.substring(0, 1).toUpperCase())
							.append(getter.substring(1)).append("()")
							.toString();
					builderForInputFields.append("\n")
							.append(rowItem.getType()).append(" ")
							.append(rowItem.getName()).append(" = form.")
							.append(getter).append(";");
					builderForParameters.append(", ").append(rowItem.getName());
				}
				map.put(Constants.LIST_OF_MODIFY_FIELD, builderForInputFields
						.toString().replaceFirst("\n", ""));
				map.put(Constants.LIST_OF_PARAMETER_MODIFY_FIELDS,
						builderForParameters.toString().replaceFirst(", ", ""));
			}

			InputStream inputStream = Utils.openContentStream(
					"template/CreateAction", map, this.getClass(), false);
			inputStreams.add(inputStream);

			createFile(createAction, inputStream, monitor);
		}

		{ // delete action
			IFile deleteAction = project.getFile(new StringBuilder(actionPath)
					.append(IPath.SEPARATOR).append(prefixClassName)
					.append("DeleteAction.java").toString());
			String baseAction = new StringBuilder(prefixClassName).append(
					"Action").toString();

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.CLASS_ACTION_DELETE, deleteAction.getName()
					.replace(".java", ""));
			map.put(Constants.CLASS_ACTION_BASE, baseAction);
			map.put(Constants.CLASS_FORM_VAR,
					new StringBuilder(prefixClassName).append("Form")
							.toString());
			map.put(Constants.CLASS_BL_VAR, new StringBuilder(prefixClassName)
					.append("BL").toString());
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			InputStream inputStream = Utils.openContentStream(
					"template/DeleteAction", map, this.getClass(), false);
			inputStreams.add(inputStream);

			createFile(deleteAction, inputStream, monitor);
		}

		{ // update action
			IFile updateAction = project.getFile(new StringBuilder(actionPath)
					.append(IPath.SEPARATOR).append(prefixClassName)
					.append("UpdateAction.java").toString());
			String baseAction = new StringBuilder(prefixClassName).append(
					"Action").toString();

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.CLASS_ACTION_UPDATE, updateAction.getName()
					.replace(".java", ""));
			map.put(Constants.CLASS_ACTION_BASE, baseAction);
			map.put(Constants.CLASS_FORM_VAR,
					new StringBuilder(prefixClassName).append("Form")
							.toString());
			map.put(Constants.CLASS_BL_VAR, new StringBuilder(prefixClassName)
					.append("BL").toString());
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			{ // Create modify fields
				StringBuilder builderForInputFields = new StringBuilder();
				StringBuilder builderForParameters = new StringBuilder();
				for (RowItem rowItem : modifyFields) {
					String getter = rowItem.getName();
					getter = new StringBuilder("get")
							.append(getter.substring(0, 1).toUpperCase())
							.append(getter.substring(1)).append("()")
							.toString();
					builderForInputFields.append("\n")
							.append(rowItem.getType()).append(" ")
							.append(rowItem.getName()).append(" = form.")
							.append(getter).append(";");
					builderForParameters.append(", ").append(rowItem.getName());
				}
				map.put(Constants.LIST_OF_MODIFY_FIELD, builderForInputFields
						.toString().replaceFirst("\n", ""));
				map.put(Constants.LIST_OF_PARAMETER_MODIFY_FIELDS,
						builderForParameters.toString().replaceFirst(", ", ""));
			}

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
		String packageName = new StringBuilder(
				properties
						.getProperty(Constants.COM_GANESHA_CLIENT_BASEPACKAGE_MODULES))
				.append(".").append(prefixClassName).toString().toLowerCase();

		InputStream inputStream = null;
		try {
			IFile businessLogic = project.getFile(new StringBuilder(modulePath)
					.append(IPath.SEPARATOR).append(prefixClassName)
					.append("Form.java").toString());

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(Constants.CLASS_FORM_VAR,
					businessLogic.getName().replace(".java", ""));
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			{ // Create search criterias
				StringBuilder stringBuilder = new StringBuilder();
				for (RowItem rowItem : criterias) {
					stringBuilder.append("\nprivate ")
							.append(rowItem.getType()).append(" ")
							.append(rowItem.getName()).append(";");
				}
				map.put(Constants.LIST_OF_SEARCH_CRITERIA, stringBuilder
						.toString().replaceFirst("\n", ""));
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
					stringBuilder.append("\n\npublic ")
							.append(rowItem.getType()).append(" ")
							.append(getter).append(" {\n").append("\t")
							.append("return ").append(rowItem.getName())
							.append(";\n").append("}");
					stringBuilder.append("\n\npublic void ").append(setter)
							.append(" {\n").append("\t").append("this.")
							.append(rowItem.getName()).append(" = ")
							.append(rowItem.getName()).append(";\n")
							.append("}");
				}
				map.put(Constants.LIST_OF_GETTER_SETTER_SEARCH_CRITERIA,
						stringBuilder.toString().replaceFirst("\n\n", ""));
			}

			{ // Create modify fields
				StringBuilder stringBuilder = new StringBuilder();
				for (RowItem rowItem : modifyFields) {
					stringBuilder.append("\nprivate ")
							.append(rowItem.getType()).append(" ")
							.append(rowItem.getName()).append(";");
				}
				map.put(Constants.LIST_OF_MODIFY_FIELD, stringBuilder
						.toString().replaceFirst("\n", ""));
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
					stringBuilder.append("\n\npublic ")
							.append(rowItem.getType()).append(" ")
							.append(getter).append(" {\n").append("\t")
							.append("return ").append(rowItem.getName())
							.append(";\n").append("}");
					stringBuilder.append("\n\npublic void ").append(setter)
							.append(" {\n").append("\t").append("this.")
							.append(rowItem.getName()).append(" = ")
							.append(rowItem.getName()).append(";\n")
							.append("}");
				}
				map.put(Constants.LIST_OF_GETTER_SETTER_MODIFY_FIELD,
						stringBuilder.toString().replaceFirst("\n\n", ""));
			}

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

		String basePackage = new StringBuilder(webApp).append(IPath.SEPARATOR)
				.append("jsp").append(IPath.SEPARATOR).append("modules")
				.toString();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(basePackage);
		stringBuilder.append(".").append(prefixClassName.toLowerCase());

		String modulePath = stringBuilder.toString().replace('.',
				IPath.SEPARATOR);

		IFolder folder = project.getFolder(modulePath);
		if (folder.exists()) {
			Exception e = new Exception(new StringBuilder(
					"Cannot create module '").append(moduleName)
					.append("' because directory ")
					.append(folder.getFullPath()).append(" already exists")
					.toString());
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
			IFile jspFile = project.getFile(new StringBuilder(modulePath)
					.append(IPath.SEPARATOR)
					.append(Utils.camelToHuman(prefixClassName).toLowerCase()
							.replace(' ', '_')).append("_confirm_create.jsp")
					.toString());
			Map<String, String> map = new HashMap<>();
			map.put(Constants.STRUTS_PACKAGE_VAR, prefixClassName.toLowerCase());
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			{ // Create modify fields
				StringBuilder builderForNewValue = new StringBuilder();
				for (RowItem rowItem : modifyFields) {
					String newValueName = rowItem.getName();
					String originName = newValueName.replaceFirst("new", "");
					originName = new StringBuilder(originName.substring(0, 1)
							.toLowerCase()).append(originName.substring(1))
							.toString();
					builderForNewValue.append("\n<tr>");
					builderForNewValue
							.append("\n\t")
							.append("<td align=\"right\"><s:text name=\"resource.")
							.append(originName).append("\" /></td>");
					builderForNewValue.append("\n\t")
							.append("<td align=\"left\"><s:label name=\"")
							.append(newValueName).append("\" /></td>");
					builderForNewValue.append("\n</tr>");
				}

				map.put(Constants.LIST_OF_MODIFY_FIELD, builderForNewValue
						.toString().replaceFirst("\n", ""));
			}

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
			IFile jspFile = project.getFile(new StringBuilder(modulePath)
					.append(IPath.SEPARATOR)
					.append(Utils.camelToHuman(prefixClassName).toLowerCase()
							.replace(' ', '_')).append("_confirm_update.jsp")
					.toString());
			Map<String, String> map = new HashMap<>();
			map.put(Constants.STRUTS_PACKAGE_VAR, prefixClassName.toLowerCase());
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			{ // Create modify fields
				StringBuilder builderForOldValue = new StringBuilder();
				StringBuilder builderForNewValue = new StringBuilder();
				for (RowItem rowItem : modifyFields) {
					String newValueName = rowItem.getName();
					String originName = newValueName.replaceFirst("new", "");
					originName = new StringBuilder(originName.substring(0, 1)
							.toLowerCase()).append(originName.substring(1))
							.toString();
					builderForOldValue.append("\n<tr>");
					builderForOldValue
							.append("\n\t")
							.append("<td align=\"right\"><s:text name=\"resource.")
							.append(originName).append("\" /></td>");
					builderForOldValue.append("\n\t")
							.append("<td align=\"left\"><s:label name=\"old.")
							.append(originName).append("\" /></td>");
					builderForOldValue.append("\n</tr>");
					builderForNewValue.append("\n<tr>");
					builderForNewValue
							.append("\n\t")
							.append("<td align=\"right\"><s:text name=\"resource.")
							.append(originName).append("\" /></td>");
					builderForNewValue.append("\n\t")
							.append("<td align=\"left\"><s:label name=\"")
							.append(newValueName).append("\" /></td>");
					builderForNewValue.append("\n</tr>");
				}

				map.put(Constants.LIST_OF_MODIFY_FIELD, builderForNewValue
						.toString().replaceFirst("\n", ""));
				map.put(Constants.LIST_OF_OLD_FIELD, builderForOldValue
						.toString().replaceFirst("\n", ""));
			}

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
			IFile jspFile = project.getFile(new StringBuilder(modulePath)
					.append(IPath.SEPARATOR)
					.append(Utils.camelToHuman(prefixClassName).toLowerCase()
							.replace(' ', '_')).append("_create.jsp")
					.toString());
			Map<String, String> map = new HashMap<>();
			map.put(Constants.STRUTS_PACKAGE_VAR, prefixClassName.toLowerCase());
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			{ // Create modify fields
				StringBuilder stringBuilder = new StringBuilder();
				for (RowItem rowItem : modifyFields) {
					String newValueName = rowItem.getName();
					String originName = newValueName.replaceFirst("new", "");
					originName = new StringBuilder(originName.substring(0, 1)
							.toLowerCase()).append(originName.substring(1))
							.toString();
					stringBuilder.append("\n<s:textfield key=\"resource.")
							.append(originName).append("\" name=\"")
							.append(newValueName)
							.append("\" theme=\"xhtml\" size=\"30px\" />");
				}
				map.put(Constants.LIST_OF_MODIFY_FIELD, stringBuilder
						.toString().replaceFirst("\n", ""));
			}

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
			IFile jspFile = project.getFile(new StringBuilder(modulePath)
					.append(IPath.SEPARATOR)
					.append(Utils.camelToHuman(prefixClassName).toLowerCase()
							.replace(' ', '_')).append("_detail.jsp")
					.toString());
			Map<String, String> map = new HashMap<>();
			map.put(Constants.STRUTS_PACKAGE_VAR, prefixClassName.toLowerCase());
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			{ // Create search criterias
				StringBuilder stringBuilder = new StringBuilder();
				for (RowItem rowItem : detailFields) {
					stringBuilder.append("\n<tr>");
					stringBuilder
							.append("\n\t")
							.append("<td align=\"right\"><s:text name=\"resource.")
							.append(rowItem.getName()).append("\" /></td>");
					stringBuilder.append("\n\t")
							.append("<td align=\"left\"><s:label name=\"old.")
							.append(rowItem.getName()).append("\" /></td>");
					stringBuilder.append("\n</tr>");
				}
				map.put(Constants.LIST_OF_DETAIL_FIELDS, stringBuilder
						.toString().replaceFirst("\n", ""));
			}

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
			IFile jspFile = project.getFile(new StringBuilder(modulePath)
					.append(IPath.SEPARATOR)
					.append(Utils.camelToHuman(prefixClassName).toLowerCase()
							.replace(' ', '_')).append("_main.jsp").toString());
			Map<String, String> map = new HashMap<>();
			map.put(Constants.STRUTS_PACKAGE_VAR, prefixClassName.toLowerCase());

			{ // Create search criterias
				StringBuilder stringBuilder = new StringBuilder();
				for (RowItem rowItem : criterias) {
					String searchCriteria = rowItem.getName();
					String originName = searchCriteria.replaceFirst("search",
							"");
					originName = new StringBuilder(originName.substring(0, 1)
							.toLowerCase()).append(originName.substring(1))
							.toString();
					stringBuilder.append("\n<s:textfield key=\"resource.")
							.append(originName).append("\" name=\"")
							.append(searchCriteria).append("\" />");
				}
				map.put(Constants.LIST_OF_SEARCH_CRITERIA, stringBuilder
						.toString().replaceFirst("\n", ""));
			}

			{ // Create search results
				StringBuilder resultsColumnTitle = new StringBuilder();
				StringBuilder resultsColumnBody = new StringBuilder();
				for (RowItem rowItem : searchResults) {
					resultsColumnTitle
							.append("\n<td width=\"150\"><s:text name=\"resource.")
							.append(rowItem.getName()).append("\" /></td>");
					resultsColumnBody.append("\n<td><s:property value=\"")
							.append(rowItem.getName()).append("\" /></td>");
				}
				map.put(Constants.LIST_OF_SEARCH_RESULT_COLUMN_TITLES,
						resultsColumnTitle.toString().replaceFirst("\n", ""));
				map.put(Constants.LIST_OF_SEARCH_RESULTS, resultsColumnBody
						.toString().replaceFirst("\n", ""));
			}

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
			IFile jspFile = project.getFile(new StringBuilder(modulePath)
					.append(IPath.SEPARATOR)
					.append(Utils.camelToHuman(prefixClassName).toLowerCase()
							.replace(' ', '_')).append("_update.jsp")
					.toString());
			Map<String, String> map = new HashMap<>();
			map.put(Constants.STRUTS_PACKAGE_VAR, prefixClassName.toLowerCase());
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			{ // Create modify fields
				StringBuilder stringBuilder = new StringBuilder();
				for (RowItem rowItem : modifyFields) {
					String newValueName = rowItem.getName();
					String originName = newValueName.replaceFirst("new", "");
					originName = new StringBuilder(originName.substring(0, 1)
							.toLowerCase()).append(originName.substring(1))
							.toString();
					stringBuilder.append("\n<s:textfield key=\"resource.")
							.append(originName).append("\" name=\"")
							.append(newValueName)
							.append("\" theme=\"xhtml\" size=\"30px\" />");
				}
				map.put(Constants.LIST_OF_MODIFY_FIELD, stringBuilder
						.toString().replaceFirst("\n", ""));
			}

			inputStream = Utils.openContentStream("template/jsp_detail", map,
					this.getClass(), false);

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
		String packageName = new StringBuilder(
				properties
						.getProperty(Constants.COM_GANESHA_CLIENT_BASEPACKAGE_MODULES))
				.append(".").append(prefixClassName).toString().toLowerCase();

		InputStream inputStream = null;
		try {
			IFile businessLogic = project.getFile(new StringBuilder(modulePath)
					.append(IPath.SEPARATOR).append(prefixClassName)
					.append("BL.java").toString());

			Map<String, String> map = new HashMap<>();
			map.put(Constants.PACKAGE_VAR, packageName);
			map.put(CLASS_BL_VAR, businessLogic.getName().replace(".java", ""));
			map.put(Constants.ENTITY_FULL_VAR, entityFullName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			{ // Create search criterias
				StringBuilder builderForIfConditions = new StringBuilder();
				StringBuilder builderForParameters = new StringBuilder();
				for (RowItem rowItem : criterias) {
					String criteriaName = rowItem.getName();
					String originName = criteriaName.replaceFirst("search", "");
					originName = new StringBuilder(originName.substring(0, 1)
							.toLowerCase()).append(originName.substring(1))
							.toString();
					builderForIfConditions.append("\n\nif (")
							.append(criteriaName).append(" != null && !")
							.append(criteriaName)
							.append(".trim().isEmpty()) {\n\t")
							.append("criteria.add(Restrictions.like(\"")
							.append(originName).append("\", \"%\" + ")
							.append(criteriaName).append(" + \"%\"));\n}");
					builderForParameters.append(", ").append(rowItem.getType())
							.append(" ").append(criteriaName);
				}
				map.put(Constants.LIST_OF_SEARCH_CRITERIA,
						builderForIfConditions.toString().replaceFirst("\n\n",
								""));
				map.put(Constants.LIST_OF_PARAMETER_SEARCH_CRITERIA,
						builderForParameters.toString().replaceFirst(", ", ""));
			}

			{ // Create modify fields
				StringBuilder builderForEntityAssignment = new StringBuilder();
				StringBuilder builderForParameters = new StringBuilder();
				for (RowItem rowItem : modifyFields) {
					String setter = rowItem.getName()
							.replaceFirst("new", "set");
					builderForEntityAssignment.append("\n")
							.append(entityVarName).append(".").append(setter)
							.append("(").append(rowItem.getName()).append(");");
					builderForParameters.append(", ").append(rowItem.getType())
							.append(" ").append(rowItem.getName());
				}
				map.put(Constants.LIST_OF_MODIFY_FIELD,
						builderForEntityAssignment.toString().replaceFirst(
								"\n", ""));
				map.put(Constants.LIST_OF_PARAMETER_MODIFY_FIELDS,
						builderForParameters.toString().replaceFirst(", ", ""));
			}

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

		String basePackage = new StringBuilder(javaSource)
				.append(IPath.SEPARATOR)
				.append((String) properties
						.get(Constants.COM_GANESHA_CLIENT_BASEPACKAGE_MODULES))
				.toString();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(basePackage);
		stringBuilder.append(".").append(prefixClassName.toLowerCase());

		String modulePath = stringBuilder.toString().replace('.',
				IPath.SEPARATOR);

		IFolder folder = project.getFolder(modulePath);
		if (folder.exists()) {
			Exception e = new Exception(new StringBuilder(
					"Cannot create module '").append(moduleName)
					.append("' because directory ")
					.append(folder.getFullPath()).append(" already exists")
					.toString());
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
			IFile packageProperties = project.getFile(new StringBuilder(
					modulePath).append(IPath.SEPARATOR)
					.append("package.properties").toString());

			Map<String, String> map = new HashMap<>();
			map.put(Constants.RESOURCE_PAGE_TITLE, moduleName);
			map.put(Constants.ENTITY_SIMPLE_VAR, entitySimpleName);
			map.put(Constants.ENTITY_VAR_NAME, entityVarName);

			{ // Create properties
				StringBuilder stringBuilder = new StringBuilder();
				for (RowItem rowItem : detailFields) {
					stringBuilder.append("\nresource.")
							.append(rowItem.getName()).append("=")
							.append(Utils.camelToHuman(rowItem.getName()));
				}
				map.put(Constants.LIST_OF_DETAIL_FIELDS, stringBuilder
						.toString().replaceFirst("\n", ""));
			}

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
			IFile strutsXml = project.getFile(new StringBuilder(modulePath)
					.append(IPath.SEPARATOR)
					.append(prefixClassName.toLowerCase())
					.append("-struts.xml").toString());
			Map<String, String> map = new HashMap<>();
			map.put(Constants.STRUTS_PACKAGE_VAR, prefixClassName.toLowerCase());
			map.put(Constants.GANESHA_MODULES_VAR,
					properties
							.getProperty(Constants.COM_GANESHA_CLIENT_BASEPACKAGE_MODULES));
			map.put(Constants.CLASS_ACTION_MAIN, new StringBuilder(
					prefixClassName).append("MainAction").toString());
			map.put(Constants.CLASS_ACTION_CREATE, new StringBuilder(
					prefixClassName).append("CreateAction").toString());
			map.put(Constants.CLASS_ACTION_UPDATE, new StringBuilder(
					prefixClassName).append("UpdateAction").toString());
			map.put(Constants.CLASS_ACTION_DELETE, new StringBuilder(
					prefixClassName).append("DeleteAction").toString());

			String prefixJspName = moduleName.replaceAll(" ", "_")
					.toLowerCase();
			map.put(Constants.JSP_MAIN, new StringBuilder(prefixJspName)
					.append("_main.jsp").toString());
			map.put(Constants.JSP_DETAIL, new StringBuilder(prefixJspName)
					.append("_detail.jsp").toString());
			map.put(Constants.JSP_UPDATE, new StringBuilder(prefixJspName)
					.append("_update.jsp").toString());
			map.put(Constants.JSP_UPDATE_CONFIRM, new StringBuilder(
					prefixJspName).append("_confirm_update.jsp").toString());
			map.put(Constants.JSP_CREATE, new StringBuilder(prefixJspName)
					.append("_create.jsp").toString());
			map.put(Constants.JSP_CREATE_CONFIRM, new StringBuilder(
					prefixJspName).append("_confirm_create.jsp").toString());

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
						new StringBuilder("Project '")
								.append(project.getName())
								.append("' is not a valid Ganesha Basic Web Project. Cannot create '")
								.append(moduleName)
								.append("' in this project.").toString());
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
	}
}