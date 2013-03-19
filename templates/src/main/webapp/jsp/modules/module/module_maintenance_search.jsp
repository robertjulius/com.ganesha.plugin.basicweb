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
	<s:form action="/modules/module/prepareDetail.action" theme="simple">
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
								<td><s:text name="resource.moduleName" /></td>
								<td><s:text name="resource.firstEntry" /></td>
								<td><s:text name="resource.parent" /></td>
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
						<tfoot>
							<tr>
								<td colspan="3"><table class="pagination">
										<s:hidden name="pagination.pageNumber" />
										<tr>
											<td><b>First</b></td>
											<td><b>Previous</b></td>
											<s:iterator var="counter" begin="1"
												end="pagination.totalPage" status="rowstatus">
												<td><s:if
														test='#rowstatus.index+1 == pagination.pageNumber'>
														<b><s:property value="%{#rowstatus.index+1}" /></b>
													</s:if> <s:else>
														<a
															onclick="$(this).closest('form').find('input#prepareDetail_pagination_pageNumber').val('<s:property value="%{#rowstatus.index+1}" />'); $(this).closest('form').attr('action', '<%=request.getContextPath()%>/modules/module/search.action'); $(this).closest('form').submit();"
															href="#"> <s:property value="%{#rowstatus.index+1}" />
														</a>
													</s:else></td>
											</s:iterator>
											<td><b>Next</b></td>
											<td><b>Last</b></td>
										</tr>
									</table></td>
							</tr>
						</tfoot>
					</table>
				</td>
			</tr>
			<tr align="center">
				<td>
					<table>
						<tr>
							<td><input type="button"
								value="<s:text name="resource.back"/>"
								onclick="$(this).closest('form').attr('action', '<%=request.getContextPath()%>/modules/module/main.action'); $(this).closest('form').submit();" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:form>
</body>
</html>