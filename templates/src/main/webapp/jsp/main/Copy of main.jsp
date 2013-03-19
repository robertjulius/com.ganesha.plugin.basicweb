<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:head />
</head>
<body>
	<div id="topFrame"
		style="clear: both; background-color: yellow; text-align: center;">
		<iframe src="topFrame.jsp" name="topFrame"></iframe>
	</div>

	<div id="leftFrame" style="float: left;">
		<iframe src="leftFrame.jsp" name="leftFrame"></iframe>
	</div>

	<div id="contentFrame" style="float: right; display: inline-block;">
		<iframe src="http://www.google.com" name="contentFrame"></iframe>
	</div>

	<div id="footerFrame"
		style="clear: both; background-color: yellow; text-align: center;">
		<iframe src="footerFrame.jsp" name="footerFrame"></iframe>
	</div>

</body>
</html>