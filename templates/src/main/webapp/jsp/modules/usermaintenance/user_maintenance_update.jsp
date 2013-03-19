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
	<s:form action="/modules/usermaintenance/validateUpdate.action"
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
						<s:textfield key="resource.userId" name="newUserId" theme="xhtml"
							size="30px" />
						<s:textfield key="resource.userName" name="newName" theme="xhtml"
							size="30px" />
						<s:select key="resource.userGroup" name="newUserGroupId"
							id="newUserGroupId" list="selectListUserGroup" listKey="id"
							listValue="name" theme="xhtml" />
						<s:hidden name="newUserGroupName" id="newUserGroupName" />
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
								onclick="if (!confirmCancel()) {return;} $(this).closest('form').attr('action', '<%=request.getContextPath()%>/modules/usermaintenance/detail.action'); $(this).closest('form').submit();" /></td>
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
<script type="text/javascript">
	$(function() {
		$('#newUserGroupId').change(
				function() {
					$('#newUserGroupName').val(
							$(this).children("option").filter(":selected")
									.text());
				});
	});
</script>
</html>
