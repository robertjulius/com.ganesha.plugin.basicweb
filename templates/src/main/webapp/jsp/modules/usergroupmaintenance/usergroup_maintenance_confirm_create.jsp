<%@page import="java.util.TreeMap"%>
<%@page import="com.ganesha.basicweb.modules.login.Privilege"%>
<%@page import="com.ganesha.basicweb.modules.usergroupmaintenance.HtmlPrivilegeTreeGenerator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
			<td class="pageTitle1"><s:text name="resource.page.title" /></td> 
		</tr>
		<tr>
			<td class="pageTitle2"><s:text name="resource.page.title2.confirmCreate" /></td>
		</tr>
	</table>
	<s:form action="/modules/usergroupmaintenance/executeCreate.action" theme="simple">
		<s:if test="hasActionErrors()">
			<table>
				<s:actionerror />
				<s:fielderror />
			</table>
		</s:if>
		<table class="form">
			<tr>
				<td>
					<table class="grid" id="grid2">
						<thead>
							<tr>
								<td colspan="2"><s:text name="resource.newValue" /></td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td align="right" width="100px"><b><s:text name="resource.userGroupName" /></b></td>
								<td align="left" width="300px"><s:property value="newName" /></td>
							</tr>
							<tr>
								<td align="right" width="100px"><b><s:text name="resource.description" /></b></td>
								<td align="left" width="300px"><s:property value="newDescription" /></td>
							</tr>
							<tr>
								<td colspan="2">
									<ul>
										<%
											@SuppressWarnings("unchecked")
											TreeMap<String, Privilege> newTreeMap = (TreeMap<String, Privilege>) request.getAttribute("newTreeMap");
											out.write(HtmlPrivilegeTreeGenerator.generateHtmlTree(newTreeMap));
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
							<td><input type="button" value="<s:text name="resource.back"/>"
								onclick="$(this).closest('form').attr('action', '<%=request.getContextPath()%>/modules/usergroupmaintenance/formCreate.action'); $(this).closest('form').submit();" /></td>
							<td><input type="button" value="<s:text name="resource.submit"/>"
								onclick="$(this).closest('form').submit();" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:form>
</body>
<script type="text/javascript">
	window.onload = function() {
		stripeTable($('table#grid1'));
		stripeTable($('table#grid2'));
	}
</script>
</html>
