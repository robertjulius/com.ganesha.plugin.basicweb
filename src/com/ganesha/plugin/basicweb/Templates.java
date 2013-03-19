package com.ganesha.plugin.basicweb;

import java.util.ArrayList;
import java.util.List;

public class Templates {
	private static final List<String> files = new ArrayList<>();

	static {
		files.add("templates/.classpath");
		files.add("templates/.project");
		files.add("templates/.settings/.jsdtscope");
		files.add("templates/.settings/org.eclipse.jdt.core.prefs");
		files.add("templates/.settings/org.eclipse.m2e.core.prefs");
		files.add("templates/.settings/org.eclipse.m2e.wtp.prefs");
		files.add("templates/.settings/org.eclipse.wst.common.component");
		files.add("templates/.settings/org.eclipse.wst.common.project.facet.core.xml");
		files.add("templates/.settings/org.eclipse.wst.jsdt.core.prefs");
		files.add("templates/.settings/org.eclipse.wst.jsdt.ui.superType.container");
		files.add("templates/.settings/org.eclipse.wst.jsdt.ui.superType.name");
		files.add("templates/pom.xml");
		files.add("templates/src/main/java/com/cjs/basicweb/model/accesspath/AccessPath.java");
		files.add("templates/src/main/java/com/cjs/basicweb/model/activitylog/ActivityLog.java");
		files.add("templates/src/main/java/com/cjs/basicweb/model/FormBean.java");
		files.add("templates/src/main/java/com/cjs/basicweb/model/Item.java");
		files.add("templates/src/main/java/com/cjs/basicweb/model/module/Module.java");
		files.add("templates/src/main/java/com/cjs/basicweb/model/Pagination.java");
		files.add("templates/src/main/java/com/cjs/basicweb/model/Trackable.java");
		files.add("templates/src/main/java/com/cjs/basicweb/model/user/SimpleUser.java");
		files.add("templates/src/main/java/com/cjs/basicweb/model/usergroup/UserGroup.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/BusinessLogic.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/login/action/LoginExecuteAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/login/action/LoginExecuteAction.properties");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/login/form/LoginForm.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/login/HtmlMenuGenerator.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/login/logic/LoginBL.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/login/login-struts.xml");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/login/Privilege.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/login/PrivilegeUtils.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/login/usersession/SimpleSessionManager.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/login/usersession/SimpleUserSession.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/logout/action/LogoutExecuteAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/logout/action/LogoutMainAction.properties");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/logout/logout-struts.xml");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/module/action/ModuleMaintenanceAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/module/action/ModuleMaintenanceCreateAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/module/action/ModuleMaintenanceDeleteAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/module/action/ModuleMaintenanceMainAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/module/action/ModuleMaintenanceUpdateAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/module/ModuleBL.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/module/ModuleForm.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/module/modulemaintenance-struts.xml");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/module/package.properties");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/ModuleSession.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/resetusersession/action/ResetUserSessionMainAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/resetusersession/form/ResetUserSessionForm.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/resetusersession/logic/ResetUserSessionBL.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/resetusersession/package.properties");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/resetusersession/resetusersession-struts.xml");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usergroupmaintenance/action/UserGroupMaintenanceAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usergroupmaintenance/action/UserGroupMaintenanceCreateAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usergroupmaintenance/action/UserGroupMaintenanceDeleteAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usergroupmaintenance/action/UserGroupMaintenanceMainAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usergroupmaintenance/action/UserGroupMaintenanceUpdateAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usergroupmaintenance/HtmlPrivilegeTreeGenerator.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usergroupmaintenance/package.properties");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usergroupmaintenance/usergroupmaintenance-struts.xml");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usergroupmaintenance/UserGroupMaintenanceBL.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usergroupmaintenance/UserGroupMaintenanceForm.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usermaintenance/action/UserMaintenanceAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usermaintenance/action/UserMaintenanceCreateAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usermaintenance/action/UserMaintenanceDeleteAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usermaintenance/action/UserMaintenanceMainAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usermaintenance/action/UserMaintenanceUpdateAction.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usermaintenance/package.properties");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usermaintenance/usermaintenance-struts.xml");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usermaintenance/UserMaintenanceBL.java");
		files.add("templates/src/main/java/com/cjs/basicweb/modules/usermaintenance/UserMaintenanceForm.java");
		files.add("templates/src/main/java/com/cjs/basicweb/utility/AppContextManager.java");
		files.add("templates/src/main/java/com/cjs/basicweb/utility/CommonUtils.java");
		files.add("templates/src/main/java/com/cjs/basicweb/utility/GeneralConstants.java");
		files.add("templates/src/main/java/com/cjs/basicweb/utility/JSPUtils.java");
		files.add("templates/src/main/java/com/cjs/basicweb/utility/PageFail.java");
		files.add("templates/src/main/java/com/cjs/basicweb/utility/PropertiesConstants.java");
		files.add("templates/src/main/java/com/cjs/basicweb/utility/ServletStartUp.java");
		files.add("templates/src/main/java/com/cjs/basicweb/utility/SimpleFilter.java");
		files.add("templates/src/main/java/com/cjs/basicweb/utility/SimpleHttpSessionListener.java");
		files.add("templates/src/main/java/com/cjs/basicweb/utility/SimpleSessionListener.java");
		files.add("templates/src/main/java/com/cjs/core/DefaultHttpSessionListener.java_");
		files.add("templates/src/main/java/com/cjs/core/DefaultSessionManager.java_");
		files.add("templates/src/main/java/com/cjs/core/exception/AppException.java");
		files.add("templates/src/main/java/com/cjs/core/exception/GaneshaException.java");
		files.add("templates/src/main/java/com/cjs/core/exception/UserException.java");
		files.add("templates/src/main/java/com/cjs/core/SessionManager.java");
		files.add("templates/src/main/java/com/cjs/core/User.java");
		files.add("templates/src/main/java/com/cjs/core/UserSession.java");
		files.add("templates/src/main/java/com/cjs/core/utils/MappingUtils.java");
		files.add("templates/src/main/java/com/cjs/struts2/BaseAction.java");
		files.add("templates/src/main/java/com/cjs/struts2/components/Component/Pagination.java");
		files.add("templates/src/main/java/com/cjs/struts2/FormAction.java");
		files.add("templates/src/main/java/com/cjs/struts2/views/jsp/PaginationTag.java");
		files.add("templates/src/main/resources/global.properties");
		files.add("templates/src/main/resources/hibernate.cfg.xml");
		files.add("templates/src/main/resources/log4j.properties");
		files.add("templates/src/main/resources/log4j.xml_");
		files.add("templates/src/main/resources/struts.properties");
		files.add("templates/src/main/resources/struts.xml");
		files.add("templates/src/main/webapp/css/ganesha-common-0.1.css");
		files.add("templates/src/main/webapp/css/ganesha-struts2-0.1.css");
		files.add("templates/src/main/webapp/css/ganesha-table-0.1.css");
		files.add("templates/src/main/webapp/css/ganesha-table-popupmenu-0.1.css");
		files.add("templates/src/main/webapp/images/knob-horizontal.png");
		files.add("templates/src/main/webapp/images/logoFairways.jpg");
		files.add("templates/src/main/webapp/images/logoFairways1.jpg");
		files.add("templates/src/main/webapp/images/logoFairways2.jpg");
		files.add("templates/src/main/webapp/images/logoFairways3.jpg");
		files.add("templates/src/main/webapp/index.jsp");
		files.add("templates/src/main/webapp/js/ganesha-ui-0.1.js");
		files.add("templates/src/main/webapp/js/jquery-treeview/images/file.gif");
		files.add("templates/src/main/webapp/js/jquery-treeview/images/folder-closed.gif");
		files.add("templates/src/main/webapp/js/jquery-treeview/images/folder.gif");
		files.add("templates/src/main/webapp/js/jquery-treeview/images/minus.gif");
		files.add("templates/src/main/webapp/js/jquery-treeview/images/plus.gif");
		files.add("templates/src/main/webapp/js/jquery-treeview/images/treeview-black-line.gif");
		files.add("templates/src/main/webapp/js/jquery-treeview/images/treeview-black.gif");
		files.add("templates/src/main/webapp/js/jquery-treeview/images/treeview-default-line.gif");
		files.add("templates/src/main/webapp/js/jquery-treeview/images/treeview-default.gif");
		files.add("templates/src/main/webapp/js/jquery-treeview/images/treeview-famfamfam-line.gif");
		files.add("templates/src/main/webapp/js/jquery-treeview/images/treeview-famfamfam.gif");
		files.add("templates/src/main/webapp/js/jquery-treeview/images/treeview-gray-line.gif");
		files.add("templates/src/main/webapp/js/jquery-treeview/images/treeview-gray.gif");
		files.add("templates/src/main/webapp/js/jquery-treeview/images/treeview-red-line.gif");
		files.add("templates/src/main/webapp/js/jquery-treeview/images/treeview-red.gif");
		files.add("templates/src/main/webapp/js/jquery-treeview/jquery.treeview.async.js");
		files.add("templates/src/main/webapp/js/jquery-treeview/jquery.treeview.css");
		files.add("templates/src/main/webapp/js/jquery-treeview/jquery.treeview.js");
		files.add("templates/src/main/webapp/js/jquery-treeview/jquery.treeview.min.js");
		files.add("templates/src/main/webapp/js/jquery-treeview/jquery.treeview.pack.js");
		files.add("templates/src/main/webapp/js/jquery-treeview/lib/jquery.cookie.js");
		files.add("templates/src/main/webapp/js/jquery-treeview/screen.css");
		files.add("templates/src/main/webapp/js/jquery.simplemodal.1.4.4.min.js");
		files.add("templates/src/main/webapp/jsp/main/Copy of main.jsp");
		files.add("templates/src/main/webapp/jsp/main/error_not_authorized.jsp");
		files.add("templates/src/main/webapp/jsp/main/error_session_expired.jsp");
		files.add("templates/src/main/webapp/jsp/main/home.jsp");
		files.add("templates/src/main/webapp/jsp/main/left_frame.jsp");
		files.add("templates/src/main/webapp/jsp/main/main.jsp");
		files.add("templates/src/main/webapp/jsp/main/top_frame.jsp");
		files.add("templates/src/main/webapp/jsp/modules/login/login.jsp");
		files.add("templates/src/main/webapp/jsp/modules/logout/logout.jsp");
		files.add("templates/src/main/webapp/jsp/modules/module/module_maintenance_confirm_create.jsp");
		files.add("templates/src/main/webapp/jsp/modules/module/module_maintenance_confirm_update.jsp");
		files.add("templates/src/main/webapp/jsp/modules/module/module_maintenance_create.jsp");
		files.add("templates/src/main/webapp/jsp/modules/module/module_maintenance_detail.jsp");
		files.add("templates/src/main/webapp/jsp/modules/module/module_maintenance_main.jsp");
		files.add("templates/src/main/webapp/jsp/modules/module/module_maintenance_search.jsp");
		files.add("templates/src/main/webapp/jsp/modules/module/module_maintenance_update.jsp");
		files.add("templates/src/main/webapp/jsp/modules/resetusersession/reset_user_session_detail.jsp");
		files.add("templates/src/main/webapp/jsp/modules/resetusersession/reset_user_session_main.jsp");
		files.add("templates/src/main/webapp/jsp/modules/usergroupmaintenance/usergroup_maintenance_confirm_create.jsp");
		files.add("templates/src/main/webapp/jsp/modules/usergroupmaintenance/usergroup_maintenance_confirm_update.jsp");
		files.add("templates/src/main/webapp/jsp/modules/usergroupmaintenance/usergroup_maintenance_create.jsp");
		files.add("templates/src/main/webapp/jsp/modules/usergroupmaintenance/usergroup_maintenance_detail.jsp");
		files.add("templates/src/main/webapp/jsp/modules/usergroupmaintenance/usergroup_maintenance_main.jsp");
		files.add("templates/src/main/webapp/jsp/modules/usergroupmaintenance/usergroup_maintenance_search.jsp");
		files.add("templates/src/main/webapp/jsp/modules/usergroupmaintenance/usergroup_maintenance_update.jsp");
		files.add("templates/src/main/webapp/jsp/modules/usermaintenance/user_maintenance_confirm_create.jsp");
		files.add("templates/src/main/webapp/jsp/modules/usermaintenance/user_maintenance_confirm_update.jsp");
		files.add("templates/src/main/webapp/jsp/modules/usermaintenance/user_maintenance_create.jsp");
		files.add("templates/src/main/webapp/jsp/modules/usermaintenance/user_maintenance_detail.jsp");
		files.add("templates/src/main/webapp/jsp/modules/usermaintenance/user_maintenance_main.jsp");
		files.add("templates/src/main/webapp/jsp/modules/usermaintenance/user_maintenance_search.jsp");
		files.add("templates/src/main/webapp/jsp/modules/usermaintenance/user_maintenance_update.jsp");
		files.add("templates/src/main/webapp/template/css_xhtml/controlfooter.ftl");
		files.add("templates/src/main/webapp/template/css_xhtml/controlheader-core.ftl");
		files.add("templates/src/main/webapp/template/css_xhtml/head.ftl");
		files.add("templates/src/main/webapp/template/css_xhtml/styles.css");
		files.add("templates/src/main/webapp/template/custom/a-close.ftl");
		files.add("templates/src/main/webapp/template/custom/a.ftl");
		files.add("templates/src/main/webapp/template/custom/actionerror.ftl");
		files.add("templates/src/main/webapp/template/custom/actionmessage.ftl");
		files.add("templates/src/main/webapp/template/custom/checkbox.ftl");
		files.add("templates/src/main/webapp/template/custom/checkboxlist.ftl");
		files.add("templates/src/main/webapp/template/custom/combobox.ftl");
		files.add("templates/src/main/webapp/template/custom/common-attributes.ftl");
		files.add("templates/src/main/webapp/template/custom/controlfooter.ftl");
		files.add("templates/src/main/webapp/template/custom/controlheader.ftl");
		files.add("templates/src/main/webapp/template/custom/css.ftl");
		files.add("templates/src/main/webapp/template/custom/debug.ftl");
		files.add("templates/src/main/webapp/template/custom/div-close.ftl");
		files.add("templates/src/main/webapp/template/custom/div.ftl");
		files.add("templates/src/main/webapp/template/custom/doubleselect.ftl");
		files.add("templates/src/main/webapp/template/custom/dynamic-attributes.ftl");
		files.add("templates/src/main/webapp/template/custom/empty.ftl");
		files.add("templates/src/main/webapp/template/custom/fielderror.ftl");
		files.add("templates/src/main/webapp/template/custom/file.ftl");
		files.add("templates/src/main/webapp/template/custom/form-close.ftl");
		files.add("templates/src/main/webapp/template/custom/form-common.ftl");
		files.add("templates/src/main/webapp/template/custom/form.ftl");
		files.add("templates/src/main/webapp/template/custom/head.ftl");
		files.add("templates/src/main/webapp/template/custom/hidden.ftl");
		files.add("templates/src/main/webapp/template/custom/inputtransferselect.ftl");
		files.add("templates/src/main/webapp/template/custom/label.ftl");
		files.add("templates/src/main/webapp/template/custom/optgroup.ftl");
		files.add("templates/src/main/webapp/template/custom/optiontransferselect.ftl");
		files.add("templates/src/main/webapp/template/custom/password.ftl");
		files.add("templates/src/main/webapp/template/custom/radiomap.ftl");
		files.add("templates/src/main/webapp/template/custom/reset.ftl");
		files.add("templates/src/main/webapp/template/custom/scripting-events.ftl");
		files.add("templates/src/main/webapp/template/custom/select.ftl");
		files.add("templates/src/main/webapp/template/custom/styles.css");
		files.add("templates/src/main/webapp/template/custom/submit-close.ftl");
		files.add("templates/src/main/webapp/template/custom/submit.ftl");
		files.add("templates/src/main/webapp/template/custom/table.ftl");
		files.add("templates/src/main/webapp/template/custom/text.ftl");
		files.add("templates/src/main/webapp/template/custom/textarea.ftl");
		files.add("templates/src/main/webapp/template/custom/token.ftl");
		files.add("templates/src/main/webapp/template/custom/updownselect.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/checkbox.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/checkboxlist.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/combobox.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/control-close.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/control.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/controlfooter.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/controlheader.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/debug.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/doubleselect.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/file.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/form-close-validate.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/form-close.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/form-validate.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/form.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/head.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/inputtransferselect.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/label.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/optiontransferselect.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/password-core.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/password.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/radiomap.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/reset.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/select.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/styles.css");
		files.add("templates/src/main/webapp/template/top_xhtml/submit-close.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/submit-core.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/submit.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/text-core.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/text.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/textarea.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/theme.properties");
		files.add("templates/src/main/webapp/template/top_xhtml/tooltip.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/updownselect.ftl");
		files.add("templates/src/main/webapp/template/top_xhtml/validation.js");
		files.add("templates/src/main/webapp/template/xhtml/head.ftl");
		files.add("templates/src/main/webapp/WEB-INF/applicationContext.xml");
		files.add("templates/src/main/webapp/WEB-INF/ganesha-tags.tld");
		files.add("templates/src/main/webapp/WEB-INF/lib/hibernate-annotations.jar");
		files.add("templates/src/main/webapp/WEB-INF/lib/struts2-fullhibernatecore-plugin-2.2.2-GA.jar");
		files.add("templates/src/main/webapp/WEB-INF/web.xml");
	}

	public static int count() {
		return files.size();
	}

	public static String get(int index) {
		return files.get(index);
	}
}
