<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
						<td><h1>
								<s:text name="resource.page.title" />
							</h1></td>
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
								<li><a
									href="<%=request.getContextPath()%>/modules/module/prepareCreate.action">
										<b><s:text name="resource.create.new" /></b>
								</a></li>
							</ul>
						</td>
					</tr>
					<tr>
						<td><hr /></td>
					</tr>
					<tr>
						<td><s:form action="/modules/module/search.action"
								method="post">
								<s:actionerror />
								<s:fielderror />
								<s:textfield key="resource.moduleName" name="searchName" />
								<s:textfield key="resource.firstEntry" name="searchFirstEntry" />
								<s:textfield key="resource.parent" name="searchParentId" />
								<s:select key="resource.rowsPerPage"
									list="pagination.availableRowsPerPage"
									name="pagination.rowsPerPage" />
								<s:hidden name="pagination.pageNumber" value="1" />
								<s:submit key="resource.search" name="%{resource.search}" />
							</s:form></td>
					</tr>
					<tr>
						<td><hr /></td>
					</tr>
				</table>
			</td>
		</tr>
		<s:if test="pagination.pageNumber != null">
			<tr>
				<td><s:form action="/modules/module/prepareDetail.action"
						theme="simple">
						<s:hidden name="selectedId" />
						<table class="grid">
							<thead>
								<tr align="center">
									<td width="150"><s:text name="resource.moduleName" /></td>
									<td width="300"><s:text name="resource.firstEntry" /></td>
									<td width="150"><s:text name="resource.parent" /></td>
								</tr>
							</thead>
							<tbody class="selectable">
								<s:iterator value="searchResult" status="rowstatus">
									<tr
										onclick="$(this).closest('form').find('input#prepareDetail_selectedId').val('<s:property value="id" />'); $(this).closest('form').submit();"
										class="<s:if test='#rowstatus.odd == true'>rowOdd</s:if><s:else>rowEven</s:else>">
										<td><s:property value="name" /></td>
										<td><s:property value="firstEntry" /></td>
										<td><s:property value="parent.name" /></td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</s:form></td>
			</tr>
			<tr>
				<td><g:pagination formAction="/modules/module/search.action" /></td>
			</tr>

		</s:if>

	</table>
</body>
</html>