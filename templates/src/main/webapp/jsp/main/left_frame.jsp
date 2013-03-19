<%@page import="com.cjs.basicweb.modules.login.HtmlMenuGenerator"%>
<%@page import="com.cjs.core.User"%>
<%@page import="com.cjs.core.UserSession"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page
	import="com.cjs.basicweb.modules.login.usersession.SimpleUserSession"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<s:head />

<link rel="stylesheet"
	href="/basicweb/js/jquery-treeview/jquery.treeview.css" />
<link rel="stylesheet" href="/basicweb/js/jquery-treeview/screen.css" />

<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.2.6/jquery.min.js"></script>
<script src="/basicweb/js/jquery-treeview/lib/jquery.cookie.js"
	type="text/javascript"></script>
<script src="/basicweb/js/jquery-treeview/jquery.treeview.js"
	type="text/javascript"></script>

<script type="text/javascript">
	$(function() {
		$("#tree").treeview({
			collapsed : true,
			animated : "medium",
			control : "#sidetreecontrol",
			persist : "location"
		});
	})
</script>
<base target="contentFrame" />
</head>
<body>
	<div id="main">
		<div id="sidetree">
			<div class="treeheader">&nbsp;</div>
			<div id="sidetreecontrol">
				<a href="?#">Collapse All</a> | <a href="?#">Expand All</a>
			</div>
			<br />

			<ul id="tree">
				<li><a href="<%=request.getContextPath()%>/main/home.action"><strong>Home</strong></a></li>
				<%
					UserSession userSession = (UserSession) ActionContext.getContext()
							.getSession().get("userSession");
					User user = userSession.getUser();
				%>
				<%=HtmlMenuGenerator.generateHtmlMenu(
					((SimpleUserSession) userSession).getTreeMap(),
					request.getContextPath())%>
			</ul>
		</div>
	</div>
</body>

</html>
