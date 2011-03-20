<%@ page import="com.catgen.*, java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"><%
		String title="", header="", file="";
		DisplayBean displayBean = (DisplayBean)(session.getAttribute("DISPLAY_BEAN"));
		if(displayBean!=null){
			title = displayBean.browserTitle;
			header = displayBean.pageHeader;
			file = displayBean.targetFile;
			
			if(title==null || title.length()==0){
				title="OpenEntry";
			}
			if(header==null || header.length()==0){
				header="";
			}
		}%>
		<title><%=title %></title>
		<script type="text/javascript" src="js/common/jscolor.js"></script>
		
		<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.8.1/build/fonts/fonts-min.css" />
		<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.8.1/build/assets/skins/sam/skin.css">
		<script type="text/javascript" src="http://yui.yahooapis.com/2.8.1/build/yahoo/yahoo-min.js"></script>
		<script type="text/javascript" src="http://yui.yahooapis.com/2.8.1/build/json/json-min.js"></script>
		<script type="text/javascript" src="http://yui.yahooapis.com/2.8.1/build/event/event-min.js"></script>
		<script type="text/javascript" src="http://yui.yahooapis.com/2.8.1/build/connection/connection-min.js"></script>
		<script src="http://yui.yahooapis.com/2.8.1/build/yahoo-dom-event/yahoo-dom-event.js"></script>
		<script src="http://yui.yahooapis.com/2.8.1/build/element/element-min.js"></script>
		<script src="http://yui.yahooapis.com/2.8.1/build/container/container_core-min.js"></script>
		<script src="http://yui.yahooapis.com/2.8.1/build/menu/menu-min.js"></script>
		<script src="http://yui.yahooapis.com/2.8.1/build/button/button-min.js"></script>
		<script src="http://yui.yahooapis.com/2.8.1/build/editor/editor-min.js"></script>
		<style type="text/css">
			body{
				font-family: Verdana;
				color: #666;
				font-size: 12px;
			}
			.redtext{
				font-family: Verdana;
				font-weight: bold;
				color: red;
			}
			input{
				color:#333
			}
			select{
				color:#333
			}
			.shade{
				font-family: Verdana;
				background-color: #EEE;
				font-weight: bold;
			}

			a.hoverClass{
				text-decoration:none;
				color: #777;
				padding:2px;
			}
			a:hover.hoverClass{
				text-decoration:none;
				color:white;
				background-color:orange;
				padding:2px;
			}
			.selectedLink{
				text-decoration:none;
				color:white;
				background-color:orange;
				padding:2px;
			}
			.helpLink{
				text-decoration:none;
				color:white;
				background-color:#444;
				padding:2px 5px;
				font-weight: bold;
			}
			
		</style>
		<script type="text/javascript" language="javascript" src="js/functions/functions.js"></script>
		<script type="text/javascript" language="javascript" src="js/ajax/ajax2.js"></script>
	</head>
	<body bgcolor="#ababab"><br/>
		<table bgcolor="white" align="center" border="0" cellspacing="" cellpadding="0" style="padding:15px;">
			<tr valign="top">
				<td><a href="DataBeanPopulator.jsp?link=<%=Constants.HOME %>"><img src="images/common/logo.gif" title="OpenEntry Network Market Management System" border="0" width="50" height="50"/><br/><img src="images/common/nmms.png" title="OpenEntry Network Market Management System" border="0" width="75"/></a></td>
				<td align="right">
					<jsp:include page="Links.jsp"></jsp:include><br/><br/><font style="font-size:11px;color:#777"><%=header %></font><br/>
					
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<div class="yui-skin-sam">
									<jsp:include page="<%=file %>"></jsp:include>
								</div>
							</td>
						</tr>
						<tr>
							<td><div id="message"></div></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2"><br/><br/><br/><hr width="100%" color="#ccc" size="1"></hr></td>
			</tr>
			<tr>
				<td colspan="2" align="left" style="font-size: 9px;">&copy;2010 OpenEntry.com</td>
			</tr>
			<tr>
				<td colspan="2" align="left" style="font-size: 9px;"><a style="color:blue;" target="_blank" href="TermsOfUse.htm">Terms of Use</a> | <a style="color:blue;" href="PrivacyPolicy.htm" target="_blank">Privacy Policy</a></td>
			</tr>
		</table>
		<input type="hidden" id="xyz"></input>
	</body>
</html>