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
	<s:form action="/modules/module/executeCreate.action" theme="simple">
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
								<td colspan="2"><s:text name="resource.moduleInformation" /></td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td align="right"><s:text name="resource.moduleName" /></td>
								<td align="left"><s:label name="newName" /></td>
							</tr>
							<tr>
								<td align="right"><s:text name="resource.description" /></td>
								<td align="left"><s:label name="newDescription" /></td>
							</tr>
							<tr>
								<td align="right"><s:text name="resource.firstEntry" /></td>
								<td align="left"><s:label name="newFirstEntry" /></td>
							</tr>
							<tr>
								<td align="right"><s:text name="resource.parent" /></td>
								<td align="left"><s:label name="newParentName" /></td>
							</tr>
							<tr>
								<td align="left" colspan="2">
									<table>
										<tr>
											<td><b><s:text name="resource.accessPaths" /></b></td>
										</tr>
										<s:iterator value="newURLs" status="rowstatus">
											<tr>
												<td>&#149; <s:property /></td>
											</tr>
										</s:iterator>
									</table>
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
								onclick="$(this).closest('form').attr('action', '<%=request.getContextPath()%>/modules/module/formCreate.action'); $(this).closest('form').submit();" /></td>
							<td><input type="button"
								value="<s:text name="resource.submit"/>"
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
		stripeTable($('table#grid2'));
	}
</script>
</html>