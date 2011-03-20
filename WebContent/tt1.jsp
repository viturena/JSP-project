<%@ page
	import="java.sql.*, javax.sql.*, javax.naming.*"%>

<%

// Obtain our environment naming context
Context envCtx = (Context) new InitialContext().lookup("java:comp/env");

out.print(envCtx.toString());

// Look up our data source
DataSource  ds = (DataSource) envCtx.lookup("jdbc/NetmarketDB");

out.print("<br/>");
out.print(envCtx.toString());
//out.flush();

Connection conn = ds.getConnection();

%>

