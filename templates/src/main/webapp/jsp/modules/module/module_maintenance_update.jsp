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
	<s:form action="/modules/module/validateUpdate.action" theme="simple">
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
						<s:textfield key="resource.moduleName" name="newName"
							theme="xhtml" size="30px" />
						<s:textfield key="resource.description" name="newDescription"
							theme="xhtml" size="30px" />
						<s:textfield key="resource.firstEntry" name="newFirstEntry"
							theme="xhtml" size="30px" />
						<s:select key="resource.parent" name="newParentId"
							id="newParentId" list="selectListParent" listKey="id"
							listValue="name" theme="xhtml" />
						<s:hidden name="newParentName" id="newParentName" />
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
							<td><b>Access Paths</b></td>
						</tr>
						<tbody>
							<s:iterator value="newURLs" status="rowstatus">
								<tr>
									<td><s:textfield
											name="listAccessPaths[%{#rowstatus.index}]"
											value="%{newURLs[#rowstatus.index]}" size="50px" /></td>
									<td><input type="button" value="Remove"
										onclick="removeRow($(this))" /></td>
								</tr>
							</s:iterator>
						</tbody>
						<tfoot>
							<tr>
								<td align="center" colspan="2"><input type="button"
									value="Add" onclick="addRow($(this).closest('table'));" /></td>
							</tr>
						</tfoot>
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
								onclick="if (!confirmCancel()) {return;} $(this).closest('form').attr('action', '<%=request.getContextPath()%>/modules/module/detail.action'); $(this).closest('form').submit();" /></td>
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
		$('#newParentId').change(
				function() {
					$('#newParentName').val(
							$(this).children("option").filter(":selected")
									.text());
				});
	});

	function removeRow(button) {
		button.closest('tr').remove();
	}

	var rowSize = <s:property value="newURLs.size"/>;
	function addRow(table) {
		var td1 = '<td><input type="text" name="listAccessPaths[' + rowSize + ']" size="50px" /></td>';
		var td2 = '<td><input type="button" value="Remove" onclick="removeRow($(this))" />';
		table.find('tbody:last').append('<tr>' + td1 + td2 + '</tr>');
		++rowSize;
	}
</script>
</html>