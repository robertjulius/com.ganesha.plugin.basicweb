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
				<table class="form">
					<tr>
						<td>
							<ul>
								<li>
									<a href="<%=request.getContextPath()%>/modules/usermaintenance/prepareCreate.action"><b><s:text name="resource.create.new" /></b></a>
								</li>
							</ul>
						</td>
					</tr>
					<tr>
						<td><hr /></td>
					</tr>
					<tr>
						<td>
							<s:form action="/modules/usermaintenance/search.action"
								method="post">
								<s:actionerror />
								<s:fielderror />
								<s:textfield key="resource.userId" name="searchUserId" />
								<s:textfield key="resource.userName" name="searchName" />
								<s:textfield key="resource.userGroup" name="searchUserGroupName" />
								<s:select key="resource.rowsPerPage" list="pagination.availableRowsPerPage" name="pagination.rowsPerPage" />
								<s:hidden name="pagination.pageNumber" value="1" />
								<s:submit key="resource.search" name="%{resource.search}" />
							</s:form>
						</td>
					</tr>
					<tr>
						<td><hr /></td>
					</tr>
				</table>
			</td>
		</tr>
		<s:if test="searchResult != null">
			<s:if test="searchResult.empty">
				<tr align="center">
					<td><font color="red"><b><s:text name="resource.search.result.data.empy" /></b></font></td>
				<tr>
			</s:if>
			<s:else>
				<tr>
					<td>
						<s:form action="/modules/usermaintenance/prepareDetail.action" theme="simple">
							<s:hidden name="selectedId" />
							<table class="grid">
								<thead>
									<tr align="center">
										<td></td>
										<td width="150"><s:text name="resource.userId" /></td>
										<td width="200"><s:text name="resource.userName" /></td>
										<td width="200"><s:text name="resource.userGroup" /></td>
									</tr>
								</thead>
								<tbody class="selectable">
									<s:iterator value="searchResult" status="rowstatus">
										<tr onclick="$(this).closest('form').find('input#prepareDetail_selectedId').val('<s:property value="id" />'); $(this).closest('form').submit();" class="<s:if test='#rowstatus.odd == true'>rowOdd</s:if><s:else>rowEven</s:else>">
											<td align="right"><s:property value="(pagination.pageNumber - 1) * pagination.rowsPerPage + #rowstatus.index + 1" /></td>
											<td><s:property value="userId" /></td>
											<td><s:property value="name" /></td>
											<td><s:property value="userGroup.name" /></td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</s:form>
					</td>
				</tr>
				<tr>
					<td><g:pagination formAction="/modules/usermaintenance/search.action" /></td>
				</tr>
			</s:else>
		</s:if>
	</table>
</body>
</html>
