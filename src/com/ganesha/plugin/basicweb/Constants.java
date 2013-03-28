package com.ganesha.plugin.basicweb;import org.eclipse.core.runtime.IPath;public class Constants {	public static final String TEMPLATES_PATH = "templates/";	public static final String STRUTS_XML = TEMPLATES_PATH			+ "src/main/resources/struts.xml";	public static final String GNS_VAR = "$_gns_var_";	public static final String TITLE_VAR = GNS_VAR + "title";	public static final String PROJECT_NAME_VAR = GNS_VAR + "project_name";	public static final String GANESHA_CLIENT_VAR = GNS_VAR + "ganesha_client";	public static final String GANESHA_BASEPACKAGE_VAR = GNS_VAR			+ "ganesha_basepackage";	public static final String GANESHA_MODULES_VAR = GNS_VAR			+ "ganesha_modules";	public static final String GANESHA_MODEL_VAR = GNS_VAR + "ganesha_model";	public static final String PACKAGE_VAR = GNS_VAR + "package";	// public static final String CLASS_VAR = GNS_VAR + "class";	// ===================== Action Class ===================== //	public static final String BASE_ACTION_VAR = GNS_VAR + "base_action_class";	public static final String CREATE_ACTION_VAR = GNS_VAR			+ "create_action_class";	public static final String DELETE_ACTION_VAR = GNS_VAR			+ "delete_action_class";	public static final String MAIN_ACTION_VAR = GNS_VAR + "main_action_class";	public static final String UPDATE_ACTION_VAR = GNS_VAR			+ "update_action_class";	// ======================= Form & Business Logic ========== //	public static final String FORM_VAR = GNS_VAR + "form_class";	public static final String BL_VAR = GNS_VAR + "bl_class";	// ======================= Entity ========================= //	public static final String ENTITY_SIMPLE_VAR = GNS_VAR			+ "entity_simple_class";	public static final String ENTITY_FULL_VAR = GNS_VAR + "entity_full_class";	public static final String ENTITY_VAR_NAME = GNS_VAR + "entity_var_name";	// ======================= DB Setting ==================== //	public static final String DB_URL_VAR = GNS_VAR + "db_url";	public static final String DB_USERNAME_VAR = GNS_VAR + "db_username";	public static final String DB_PASSWORD_VAR = GNS_VAR + "db_password";	public static final String WIZARD_DESCRIPTION = "Create a new Basic Web project";	public static final String WIZARD_TITLE = "New Basic Web Project";	public static final String JAVA_SOURCE = "src" + IPath.SEPARATOR + "main"			+ IPath.SEPARATOR + "java";	public static final String COM_GANESHA = "com.ganesha";	public static final String COM_GANESHA_CLIENT = "com.ganesha.client";	public static final String COM_GANESHA_CLIENT_BASEPACKAGE = "com.ganesha.client.basepackage";	public static final String COM_GANESHA_CLIENT_BASEPACKAGE_MODULES = "com.ganesha.client.basepackage.modules";	public static final String COM_GANESHA_CLIENT_BASEPACKAGE_MODEL = "com.ganesha.client.basepackage.model";}