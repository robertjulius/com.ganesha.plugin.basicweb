<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:head />
<base target="_parent" />
</head>
<body style="background-color: #AA0000">
	<table>
		<tr>
			<td><a href="<%=request.getContextPath() %>/modules/logout/executeLogout.action">Logout</a></td>
		</tr>
	</table>
</body>
</html>