<%@page import="java.util.List"%>
<%@page import="java.util.TreeMap"%>
<%@page import="com.cjs.basicweb.modules.login.Privilege"%>
<%@page
	import="com.cjs.basicweb.modules.usergroupmaintenance.HtmlPrivilegeTreeGenerator"%>
<%@page import="com.cjs.basicweb.utility.GeneralConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:head />
<sj:head />

<style type="text/css">
ul {
	list-style-type: none;
	padding-left: 25px;
	padding-right: 25px;
}
</style>

</head>
<body>
	<table>
		<tr>
			<td>
				<h1>
					<s:text name="resource.page.title" />
				</h1>
			</td>
		</tr>
	</table>
	<s:form action="/modules/usergroupmaintenance/validateUpdate.action"
		theme="simple">
		<s:if test="hasActionErrors()">
			<table>
				<s:actionerror />
				<s:fielderror />
			</table>
		</s:if>
		<table class="form">
			<tr>
				<td>
					<table>
						<s:textfield key="resource.userGroupName" name="newName"
							theme="xhtml" />
						<s:textfield key="resource.description" name="newDescription"
							theme="xhtml" />
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%">
						<tr>
							<td><hr /></td>
						</tr>
					</table>
					<table border="1px" bordercolor="#CCCCFF">
						<tr>
							<td><b><s:text name="resource.privileges" /></b></td>
						</tr>
						<tbody>
							<tr>
								<td>
									<ul>
										<%
											@SuppressWarnings("unchecked")
												List<String> newModuleIds = (List<String>) request
														.getAttribute("newModuleIds");

												@SuppressWarnings("unchecked")
												TreeMap<String, Privilege> treeMap = (TreeMap<String, Privilege>) request
														.getAttribute("treeMap");

												String html = HtmlPrivilegeTreeGenerator.generateHtmlCheckBox(
														treeMap, newModuleIds);

												out.write(html);
										%>
									</ul>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%">
						<tr>
							<td><hr /></td>
						</tr>
					</table>
					<table>
						<tr>
							<td><input type="button"
								value="<s:text name="resource.cancel"/>"
								onclick="if (!confirmCancel()) {return;} $(this).closest('form').attr('action', '<%=request.getContextPath()%>/modules/usergroupmaintenance/detail.action'); $(this).closest('form').submit();" /></td>
							<td><input type="button"
								value="<s:text name="resource.submit"/>"
								onclick="if (confirmAction()) {$(this).closest('form').submit();}" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:form>
</body>
</html>