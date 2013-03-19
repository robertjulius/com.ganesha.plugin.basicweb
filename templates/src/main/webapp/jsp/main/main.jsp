<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:head />
<sj:head/>
<style type="text/css">
iframe#topFrame {
	position: fixed;
	top: 0px;
	left: 0px;
	width: 100%;
	height: 10px;
	border: none;
}

div#dragbar {
	position: fixed;
	top: 50px;
	left: 200px;
	width: 4px;
	height: 100%;
	border-left: 1px solid #AEAEAE;
	border-right: 1px solid #AEAEAE;
	background-color: #EEEEEE;
	cursor: col-resize;
}

img#dotted {
	position: fixed;
	top: 50px;
	cursor: pointer;
	cursor: col-resize;
}

div#draglayer {
	width: 200px;
	background-color: #000;
	opacity: 0.1;
	position: absolute;
	cursor: col-resize;
}

div#ghostbar {
	width: 4px;
	background-color: #AAAAAA;
	border-left: 1px solid #AEAEAE;
	border-right: 1px solid #AEAEAE;
	opacity: 0.5;
	position: absolute;
	cursor: col-resize;
}

iframe#leftFrame {
	position: fixed;
	top: 50px;
	left: 0px;
	width: 100%;
	height: 100%;
	border: none;
}

iframe#contentFrame {
	position: fixed;
	top: 50px;
	left: 204px;
	width: 100%;
	height: 100%;
	border: none;
}
</style>
<script type="text/javascript">
	window.onload = function() {
		fitContent($('iframe#topFrame'));
		fitContent($('iframe#leftFrame'));
		adjustSize();
	};

	window.onresize = function() {
		adjustSize();
	};

	function adjustSize() {
		var windowWidth = parseInt($(window).width());
		var windowHeight = parseInt($(window).height());

		$('iframe#topFrame').width("100%");
		var topFrameTop = parseInt($('iframe#topFrame').css("top"));
		var topFrameLeft = parseInt($('iframe#topFrame').css("left"));
		var topFrameWidth = parseInt($('iframe#topFrame').width());
		var topFrameHeight = parseInt($('iframe#topFrame').height());

		$('div#dragbar').css("top", topFrameHeight);
		$('div#dragbar').height(windowHeight - topFrameHeight);
		var dragbarTop = parseInt($('div#dragbar').css("top"));
		var dragbarLeft = parseInt($('div#dragbar').css("left"));
		var dragbarWidth = parseInt($('div#dragbar').width()) + 2;
		var dragbarHeight = parseInt($('div#dragbar').height());

		var imgHeight = $('img#dotted').height();
		$('img#dotted').css("top", dragbarTop + dragbarHeight / 2 - imgHeight);
		$('img#dotted').css("left", dragbarLeft + 1);

		$('iframe#leftFrame').css("top", dragbarTop);
		$('iframe#leftFrame').width(dragbarLeft);
		$('iframe#leftFrame').height(dragbarHeight);
		var leftFrameTop = parseInt($('iframe#leftFrame').css("top"));
		var leftFrameLeft = parseInt($('iframe#leftFrame').css("left"));
		var leftFrameWidth = parseInt($('iframe#leftFrame').width());
		var leftFrameHeight = parseInt($('iframe#leftFrame').height());

		$('iframe#contentFrame').css("top", dragbarTop);
		$('iframe#contentFrame').css("left", dragbarLeft + dragbarWidth);
		$('iframe#contentFrame').width(
				windowWidth - (dragbarLeft + dragbarWidth));
		$('iframe#contentFrame').height(dragbarHeight);
		var contentFrameTop = parseInt($('iframe#leftFrame').css("top"));
		var contentFrameLeft = parseInt($('iframe#leftFrame').css("left"));
		var contentFrameWidth = parseInt($('iframe#leftFrame').width());
		var contentFrameHeight = parseInt($('iframe#leftFrame').height());

	}

	$(function() {
		var dragging = false;
		$('#dragbar').mousedown(function(e) {
			e.preventDefault();

			dragging = true;
			var dragbar = $('#dragbar');

			var draglayer = $('<div>', {
				id : 'draglayer',
				css : {
					width : parseInt($(window).width()),
					height : parseInt($(window).height()),
					top : 0,
					left : 0
				}
			}).appendTo('body');

			var ghostbar = $('<div>', {
				id : 'ghostbar',
				css : {
					height : dragbar.height(),
					top : dragbar.css("top"),
					left : dragbar.css("left")
				}
			}).appendTo('body');

			var ghostbarWidth = $('#ghostbar').width();
			$(document).mousemove(function(e) {
				ghostbar.css("left", e.pageX - ghostbarWidth / 2);
			});
		});

		$(document).mouseup(function(e) {

			if (dragging) {
				$(document).unbind('mousemove');
				dragging = false;

				var dragbar = $('#dragbar');
				var draglayer = $('#draglayer');
				var ghostbar = $('#ghostbar');

				var dragbarLeft = parseInt(dragbar.css("left"));
				var ghostbarLeft = parseInt(ghostbar.css("left"));

				if (dragbarLeft != ghostbarLeft) {
					dragbar.css("left", ghostbar.css("left"));
				}

				draglayer.remove();
				ghostbar.remove();
				adjustSize();
			}
		});

	});
</script>
<title></title>
</head>
<body>
	<iframe id="topFrame" src="<%=request.getContextPath() %>/main/topFrame.action" name="topFrame"></iframe>
	<iframe id="leftFrame" src="<%=request.getContextPath() %>/main/leftFrame.action" name="leftFrame"></iframe>
	<iframe id="contentFrame" src="<%=request.getContextPath() %>/main/home.action" name="contentFrame"></iframe>
	<div id="dragbar">
		<img id="dotted" src="<%=request.getContextPath() %>/images/knob-horizontal.png" />
	</div>
</body>
</html>
