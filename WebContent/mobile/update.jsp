<%@ page
	import="java.util.*, java.net.URLEncoder, org.apache.commons.lang.*,java.sql.*,com.catgen.*,com.catgen.factories.*,java.net.URLEncoder, java.text.*"%>
<%
%>

<%@page import="com.catgen.mobile.MobileHelper"%><html>
<head>
<title>OpenEntry m-commerce gateway</title>
</head>
<body>
<% 
	String skey = request.getParameter("skey");
	if(skey != null && skey.length() > 0)
	{
%>
<% String spreadsheetName = MobileHelper.getSpreadsheetName( skey ); %>
<p>Name: <%= spreadsheetName %></p>
<%
	}
%>

<form method="post" action="do_update.jsp">
<input type="hidden" name="skey" value="<%= StringEscapeUtils.escapeHtml(skey) %>"/>
<input type="hidden" name="key" value="<%= StringEscapeUtils.escapeHtml(request.getParameter("key")) %>"/>
<input type="hidden" name="id" value="<%= StringEscapeUtils.escapeHtml(request.getParameter("id")) %>"/>
<input type="hidden" name="cid" value="<%= StringEscapeUtils.escapeHtml(request.getParameter("cid")) %>"/>
<table border="0">
<tr><td>Product Code:</td><td><input type="text" name="product_code" value="<%= StringEscapeUtils.escapeHtml(Utils.getSafeString( request.getParameter("product_code"))) %>"/></td></tr>
<tr><td>Price:</td><td><input type="text" name="price"/></td></tr>
<tr><td>Quantity:</td><td><input type="text" name="quantity"/></td></tr>
<tr><td colspan="2"><input type="submit" value="Update"/></td></tr>
</table>

<% 
	List<Product> products = MobileHelper.getProducts( request.getParameter("key"), request.getParameter("id") );

	if(products.size() > 0)
	{
%>
<p>Select product:</p>
<table border="0">
<%
	for(Product product : products)
	{
%><tr><td><a href="update.jsp?skey=<%= URLEncoder.encode( Utils.getSafeString( skey ), "UTF-8" ) %>&key=<%= URLEncoder.encode( Utils.getSafeString( request.getParameter( "key") ), "UTF-8" ) %>&id=<%= URLEncoder.encode(Utils.getSafeString( request.getParameter( "id") ), "UTF-8" ) %>&cid=<%= URLEncoder.encode(Utils.getSafeString( request.getParameter( "cid") ), "UTF-8" ) %>&v=<%= URLEncoder.encode(Utils.getSafeString( request.getParameter( "v") ), "UTF-8" ) %>&product_code=<%= URLEncoder.encode( Utils.getSafeString( product.Code ), "UTF-8" ) %>"><%= StringEscapeUtils.escapeHtml( product.Code ) %></a></td><td><%= StringEscapeUtils.escapeHtml( product.Name ) %></td></tr><%		
	}
%>
</table><br/>
<%
	}
%>

<p>You can modify the price and/or quantity of your OpenEntry catalog with this form that can also be accessed with an Internet enabled mobile phone.</p>
<p>Enter the exact Product Code and then a new Price and/or Quantity.  If these fields are blank, the program will not change the current value.</p>
</form>
</body>
</html>