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
<link rel="stylesheet"
	href="/basicweb/css/ganesha-table-popupmenu-0.1.css" type="text/css" />
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
	<s:form action="/modules/usergroupmaintenance/prepareDetail.action" theme="simple">
		<s:if test="hasActionErrors()">
			<table>
				<s:actionerror />
				<s:fielderror />
			</table>
		</s:if>
		<s:hidden name="selectedId" />
		<table class="form">
			<tr>
				<td>
					<table class="grid">
						<thead>
							<tr align="center">
								<td><s:text name="resource.userGroupName" /></td>
								<td><s:text name="resource.description" /></td>
							</tr>
						</thead>
						<tbody class="selectable">
							<s:iterator value="searchResult" status="rowstatus">
								<tr
									onclick="$(this).closest('form').find('input#prepareDetail_selectedId').val('<s:property value="id" />'); $(this).closest('form').submit();"
									class="<s:if test='#rowstatus.odd == true'>rowOdd</s:if><s:else>rowEven</s:else>">
									<td><s:property value="name" /></td>
									<td><s:property value="description" /></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</td>
			</tr>
			<tr align="center">
				<td>
					<table>
						<tr>
							<td><input type="button"
								value="<s:text name="resource.back"/>"
								onclick="$(this).closest('form').attr('action', '<%=request.getContextPath()%>/modules/usergroupmaintenance/main.action'); $(this).closest('form').submit();" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:form>
</body>
</html>