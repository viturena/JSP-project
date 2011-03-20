<%@ page
	import="com.catgen.mobile.*, java.util.*, org.apache.commons.lang.*,java.sql.*,com.catgen.*,com.catgen.factories.*,java.net.URLEncoder, java.text.*"%>
<%
String resultMessage = MobileHelper.getMessageStringFromCode( Utils.StrToIntDef( request.getParameter( "resultcode"), 0 ) );
%>
<html>
<head>
<title>OpenEntry m-commerce gateway</title>
</head>
<body>
<p><%= resultMessage %></p>
</body>
</html>