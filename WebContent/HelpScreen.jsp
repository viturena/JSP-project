<%@ page import="java.sql.*,com.catgen.factories.MySqlDB, com.catgen.helper.HelpHelper"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.catgen.helper.HelpHelper"%><html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>OpenEntry Online Help</title>
		<style type="text/css">
			body{
				color: #333;
				font-family: Verdana;
				font-size: 12px;
				background: #EEFFEE;
			}
		</style>
	</head>
	<body><%
		Connection conn = MySqlDB.getDBConnection(getServletContext());
		try{ %>
			<%=HelpHelper.getHelpMessage(conn,Integer.valueOf(request.getParameter("screenId"))) %>
		<%}finally{
			conn.close();
		}%>
	</body>
</html>