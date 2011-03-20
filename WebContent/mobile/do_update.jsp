
<%@page import="com.catgen.mobile.MobileHelper"%><%@ page
	import="java.util.*, org.apache.commons.lang.*,java.sql.*,com.catgen.*,com.catgen.factories.*,java.net.URLEncoder, java.text.*"%>
<%
	String productCode = request.getParameter("product_code");
	double price = Utils.StrToDoubleDef( request.getParameter("price"), 0 );
	int quantity = Utils.StrToIntDef( request.getParameter("quantity"), -1 );

	//int resultCode = MobileHelper.UpdateProduct( "tur13FIXxy2fWu_aHBf3qaw", "od6", productCode, price, quantity );
	// http://127.0.0.1:8080/mobile/update.jsp?key=tur13FIXxy2fWu_aHBf3qaw&id=od6
	int resultCode = MobileHelper.UpdateProduct( request.getParameter("key"), request.getParameter("id"), productCode, price, quantity );

	response.sendRedirect("update_result.jsp?resultcode=" + resultCode );

%>