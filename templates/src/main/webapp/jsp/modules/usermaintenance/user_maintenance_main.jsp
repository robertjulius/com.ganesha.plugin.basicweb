<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:head />
</head>
<body>

	<table>
		<tr>
			<td><h1>
					<s:text name="resource.page.title" />
				</h1></td>
		</tr>
	</table>
	<table class="form">
		<tr>
			<td>
				<ul>
					<li><a
						href="<%=request.getContextPath()%>/modules/usermaintenance/prepareCreate.action">
							<b><s:text name="resource.create.new" /></b>
					</a></li>
				</ul>
			</td>
		</tr>
		<tr>
			<td><hr /></td>
		</tr>
		<tr>
			<td><s:form action="/modules/usermaintenance/search.action"
					method="post">
					<s:actionerror />
					<s:fielderror />
					<s:textfield key="resource.userId" name="searchUserId" />
					<s:textfield key="resource.userName" name="searchName" />
					<s:textfield key="resource.userGroup" name="searchUserGroupName" />
					<s:submit key="resource.search" name="%{resource.search}" />
				</s:form></td>
		</tr>
	</table>
</body>
</html>