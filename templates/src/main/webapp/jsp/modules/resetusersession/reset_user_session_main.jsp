<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="g" uri="/ganesha-tags"%>
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
				<table>
					<tr>
						<td class="pageTitle1"><s:text name="resource.page.title" /></td> 
					</tr>
					<tr>
						<td class="pageTitle2"><s:text name="resource.page.title2.main" /></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<s:form action="/modules/resetusersession/prepareDetail.action" theme="simple">
					<s:actionerror />
					<s:fielderror />
					<s:hidden name="userId" />
					<table class="grid">
						<thead>
							<tr align="center">
								<td width="150"><s:text name="resource.userId" /></td>
								<td width="150"><s:text name="resource.userName" /></td>
								<td width="150"><s:text name="resource.loginTime" /></td>
							</tr>
						</thead>
						<tbody class="selectable">
							<s:iterator value="userSessions" status="rowstatus">
								<tr
									onclick="$(this).closest('form').find('input#prepareDetail_userId').val('<s:property value="user.userId" />'); $(this).closest('form').submit();"
									class="<s:if test='#rowstatus.odd == true'>rowOdd</s:if><s:else>rowEven</s:else>">
									<td><s:property value="user.userId" /></td>
									<td><s:property value="user.name" /></td>
									<td><s:date name="loginTime" format="dd-MMM-yyyy HH:mm:ss" /></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</s:form>
			</td>
		</tr>
	</table>
</body>
</html>
