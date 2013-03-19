<%@page import="java.util.TreeMap"%>
<%@page import="com.cjs.basicweb.modules.login.Privilege"%>
<%@page
	import="com.cjs.basicweb.modules.usergroupmaintenance.HtmlPrivilegeTreeGenerator"%>
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
	<s:form theme="simple">
		<s:if test="hasActionErrors()">
			<table>
				<s:actionerror />
				<s:fielderror />
			</table>
		</s:if>
		<table class="form">
			<tr>
				<td>
					<table class="grid">
						<thead>
							<tr>
								<td colspan="2"><s:text
										name="resource.userGroupInformation" /></td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td align="right"><s:text name="resource.userGroupName" /></td>
								<td align="left"><s:label name="old.name" /></td>
							</tr>
							<tr>
								<td align="right"><s:text name="resource.description" /></td>
								<td align="left"><s:label name="old.description" /></td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table class="grid">
						<thead>
							<tr>
								<td colspan="2"><s:text name="resource.privileges" /></td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									<ul>
										<%
											@SuppressWarnings("unchecked")
																			TreeMap<String, Privilege> oldTreeMap = (TreeMap<String, Privilege>) request.getAttribute("oldTreeMap");

																			String html = HtmlPrivilegeTreeGenerator.generateHtmlTree(
																					oldTreeMap);

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
					<table>
						<tr>
							<td><input type="button"
								value="<s:text name="resource.back"/>"
								onclick="$(this).closest('form').attr('action', '<%=request.getContextPath()%>/modules/usergroupmaintenance/searchResult.action'); $(this).closest('form').submit();" /></td>
							<td><input type="button"
								value="<s:text name="resource.edit"/>"
								onclick="$(this).closest('form').attr('action', '<%=request.getContextPath()%>/modules/usergroupmaintenance/prepareUpdate.action'); $(this).closest('form').submit();" /></td>
							<td><input type="button"
								value="<s:text name="resource.delete"/>"
								onclick="$(this).closest('form').attr('action', '<%=request.getContextPath()%>/modules/usergroupmaintenance/executeDelete.action'); if (confirmAction()) {$(this).closest('form').submit();}" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:form>
</body>
<script type="text/javascript">
	window.onload = function() {
		stripeTable($('table.grid'));
	}
</script>
</html>